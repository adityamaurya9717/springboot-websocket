
var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}
 let websocket=null;
function connect() {
   // var socket = new SockJS('/websocket');
  websocket = new WebSocket("ws://localhost:8080/websocket");
   console.log(websocket);
   websocket.onopen = function(e) {
     //alert("[open] Connection established");
     //alert("Sending to server");
     websocket.send("My name is John");
   };

   websocket.addEventListener("error", (event) => {
        console.log("error from server ", event);
        });
        websocket.addEventListener("message", (event) => {
        console.log("Message from server ", event.data);
        });

//    stompClient = Stomp.over(socket);
//    stompClient.connect({}, function (frame) {
//        setConnected(true);
//        console.log('Connected: ' + frame);
//        stompClient.subscribe('/topic/greetings', function (greeting) {
//                //stompClient.subscribe('/mychat/aditya', function (greeting) {
//             console.log("subscribe to ->", greeting.body);
//            showGreeting(JSON.parse(greeting.body).content);
//        });
//    });
}
function addEventListener(){

  }

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/chat", {}, JSON.stringify({'name': $("#name").val()}));
       // stompClient.send("/app/chat", {}, JSON.stringify({'name': $("#name").val()}));

}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});


