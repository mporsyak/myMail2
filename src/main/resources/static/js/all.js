function openModal() {
    $('#exampleModal').modal();
}

function loadUsersTable(){
    $.ajax({
        method: 'GET',
        contentType: 'application/json;charset=UTF-8',
        url: "allUsers",
        data: null,
        success: function (response) {
            buildUserTable(response);
        }
    });
}

function buildUserTable(users){
    var tBody = document.getElementById("allUsersTable");
    tBody.innerHTML = "";

    for (i = 0; i < users.length; i++){
        var tr = document.createElement("tr");
        tBody.appendChild(tr);

        var tdLogin = document.createElement("td");
        tdLogin.innerHTML = "<p onclick='openModal();'>" + users[i] + "</p>";
        tr.appendChild(tdLogin);
    }
}