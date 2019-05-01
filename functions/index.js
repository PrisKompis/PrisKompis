const functions = require('firebase-functions');
const admin = require('firebase-admin');
const nodemailer = require('nodemailer');
admin.initializeApp(functions.config().firebase);
const express = require('express');
const cors = require('cors')({origin: true});
const app = express();
const fetch = require('node-fetch');

// TODO: Remember to set token using >> firebase functions:config:set stripe.token="SECRET_STRIPE_TOKEN_HERE"
const stripe = require('stripe')(functions.config().stripe.token);

// Function to charge the card

exports.charge = functions.https.onRequest((req, res) => {
    const body = req.body;
    const token = body.token.id;
    const amount = body.charge.amount;
    const currency = body.charge.currency;
    const description = body.charge.description;
    const receipt_email = body.charge.receipt_email;
    console.log(token, "::", amount, "::", currency, "::", receipt_email);

    // Charge card  - JSON Object
    stripe.charges.create({
        amount,
        currency,
        description: description,
        receipt_email,
        source: token,
    }).then(charge => {
        send(res, 200, {
            message: 'Success',
            charge,
        });
        return null;
    }).catch(err => {
        console.log(err);
        send(res, 500, {
            error: err.message,
        });
    });
})

// send response
function send(res, code, body) {
    res.send({
        statusCode: code,
        headers: {'Access-Control-Allow-Origin': '*'},
        body: JSON.stringify(body),
    });
}

app.use(cors);
app.post('/', (req, res) => {
    // Catch any unexpected errors to prevent crashing
    try {
        charge(req, res);
    } catch(e) {
        console.log(e);
        send(res, 500, {
            error: `The server received an unexpected error. Please try again and contact the site admin if the error persists.`,
        });
    }
});

/**
* Here we're using Gmail to send
*/


// TODO: Remember to set email Id and password using >> firebase functions:config:set gmail.email="Your EmailId here - eg.priskompissda@gmail.com"
// TODO:                                             >> firebase functions:config:set gmail.password="Your password here"

const Email = functions.config().gmail.email;
const Password = functions.config().gmail.password;
const mailTransport = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: Email,
        pass: Password
    }
});

exports.sendMail = functions.https.onRequest((req, res) => {
    cors(req, res, () => {
        const body = req.body;
        const title = body.title;
        const receipt_url = body.receipt_url;
        const emailTo = body.emailTo;
        console.log(emailTo, "::", emailTo);
        console.log(title, "::", receipt_url);

        fetch(receipt_url)
            .then((response) => response.text())
            .then(body => {
                const htmlMessage = body;

                const mailOptions = {
                    from: 'Pris Kompis <priskompissda@gmail.com>', // Something like: Jane Doe <janedoe@gmail.com>
                    to: emailTo,
                    subject: title, // email subject
                    html: htmlMessage
                };

                // send email and return response
                return mailTransport.sendMail(mailOptions, (err, info) => {
                    if(err){
                        return res.send(err.toString());
                    }
                    return res.send('Email Sent!');
                });
            })
            .catch((err) => {
                console.error("Unable to fetch html", err);
            });
    });
});