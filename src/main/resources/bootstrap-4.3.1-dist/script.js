/*function showIncome() {
    showModal("showIncomeMessages");
}

function showOutcome() {
    showModal("showOutcomeMessages");
}

function showModal(url){
    $.ajax({
        method: 'GET',
        contentType: 'application/json;charset=UTF-8',
        url: url,
        data: null,
        success: function (response) {
            getMessages(response);
            $('#exampleModal').modal();
        }
    });
}

function getMessages(messages){
    var myTBody = document.getElementById("myTBody");
    myTBody.innerHTML = "";

    for (i = 0; i < messages.length; i++){
        var tr = document.createElement("tr");
        myTBody.appendChild(tr);

        var tdContent = document.createElement("td");
        tdContent.innerHTML = messages[i].content;
        tr.appendChild(tdContent);

        var tdGoal = document.createElement("td");
        tdGoal.innerHTML = messages[i].goal;
        tr.appendChild(tdGoal);
    }
}

function Get(requestUrl){
    let httpreq = new XMLHttpRequest();
    httpreq.open("GET", requestUrl, false);
    httpreq.send(null);

    return httpreq;
}

function post(requestUrl, body){
    let httpreq = new XMLHttpRequest();
    httpreq.open("POST", requestUrl, false);
    httpreq.setRequestHeader("Content-type", "application/json;charset=UTF-8")
    httpreq.send(body);

    return httpreq;
}}*/