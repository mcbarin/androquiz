//import firebase functions modules
const functions = require('firebase-functions');
//import admin module
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.pushNotification2 = functions.database.ref('/gamestate/{pushId}').onWrite( event => {


    console.log('Push notification event triggered');

    //  Grab the current value of what was written to the Realtime Database.
    var valueObject = event.data.val();

    if(valueObject.photoUrl != null) {
      valueObject.photoUrl= "Sent you a photo!";
    }

  // Create a notification
    const payload = {
        notification: {
            title:"GameOS",
            body: "You have a new Challenge from a Friend",
            sound: "default"
        },
    };

  //Create an options object that contains the time to live for the notification and the priority
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24
    };

    return admin.messaging().sendToTopic("pushNotifications2", payload, options);
});


// Listens for new messages added to messages/:pushId
exports.pushNotification = functions.database.ref('/users/{pushId}/requests/{userId}').onWrite( event => {


    console.log('Push notification event triggered');

    //  Grab the current value of what was written to the Realtime Database.
    var valueObject = event.data.val();

    if(valueObject.photoUrl != null) {
      valueObject.photoUrl= "Sent you a photo!";
    }

  // Create a notification
    const payload = {
        notification: {
            title:"GameOS",
            body: "You have a new Friend Request",
            sound: "default"
        },
    };

  //Create an options object that contains the time to live for the notification and the priority
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24
    };

    return admin.messaging().sendToTopic("pushNotifications", payload, options);
});
