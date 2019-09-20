function addNewUser() {
    let body = {};
    body.newLogin = document.getElementById("newLogin").value;
    body.newPassword = document.getElementById("newPassword").value;

    let response = post("/addUser",JSON.stringify(body));
    document.getElementById("info").innerHTML = response.responseText;
}