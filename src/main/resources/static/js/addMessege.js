function sendMessageWithAjax() {
    let body = {};
    body.myContent = document.getElementById("myContent").value;
    body.myRecipLogin = document.getElementById("myRecipientLogin").value;

    $.ajax({
        method: 'POST',
        contentType: 'application/json;charset=UTF-8',
        url: 'sendMessage',
        data: JSON.stringify(body),
        success: function (response) {
            document.getElementById("info").innerHTML = response.responseText;
        },
        error: function (response) {
            document.getElementById("info").innerHTML = response.responseText;
        }
    });
}

function sendMessage(){
    let body = {};
    body.myContent = document.getElementById("myContent").value;
    body.myRecipLogin = document.getElementById("myRecipientLogin").value;
    //{"myContent":"1","myRecipLogin":"2"}

    let response = post("/sendMessage", JSON.stringify(body));
    document.getElementById("info").innerHTML = response.responseText;
}
