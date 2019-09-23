function addNewUser() {
    let body = {};
    // body.newLogin = document.getElementById("newLogin").value;
    // body.newPassword = document.getElementById("newPassword").value;
    body.newLogin = $('#newLogin').val();
    body.newPassword = $('#newPassword').val();

    let response = post("/addUser",JSON.stringify(body));
    // document.getElementById("info").innerHTML = response.responseText;
    $('#info').text(response.responseText);
}