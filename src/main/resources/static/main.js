$(document).ready(function(){

   getAll();

    $(document).ajaxStart(function () {
        $("#loading").show();
        $("#loaded").hide();
    }).ajaxStop(function () {
        $("#loading").hide();
        $("#loaded").show();
    });

    $("#phoneNumber").mask("(999) 999 9999");

});

function getAll() {
    clearAll();
    $("#mainTable").show();
    myAjax('getAll','GET','', function(data){
        for(var i = 0; i < data.value.length && data.isSuccess; i++) {
            $("#customFields").append(
                `<tr>
                            <td>` + data.value[i].firstName + `</td>
                            <td>` + data.value[i].lastName + `</td>
                            <td>` + data.value[i].phoneNumber + `</td>
                            <td>
                            <button type="button" class="btn btn-primary" onclick="editUser('`+data.value[i].id+`')">Edit</button>
                            <button type="button" class="btn btn-danger" onclick="deleteUser('`+data.value[i].id+`')">Delete</button>
                            </td>
                         </tr>`
            );
        }
        if(data.value.length == 0) $("#mainTable").hide();
    });
}

function editUser(_id) {
    myAjax('get','GET', {id: _id},function (data) {
        if (data.isSuccess) {
            document.getElementById('firstName').value = data.value.firstName;
            document.getElementById('lastName').value = data.value.lastName;
            document.getElementById('phoneNumber').value = data.value.phoneNumber;
            $("#createOrEdit").modal("toggle");
            $('button[name="modalActionButton"]').attr("onclick", "createOrEdit('edit','"+data.value.id+"')");
        }else {
            alert(data.value);
        }
    });
}

function clearAll() {
    closeModal();
    $("#customFields").empty();
}

function deleteUser(id) {
    if(confirm("Are you sure to delete this record ?")){
        myAjax( 'delete','DELETE',{id: id},function (data) {
            if(data.isSuccess) {
                getAll();
            }else {
                alert("Something went wrong.");
            }
        });
    }
}

function closeModal() {
    $("#createOrEditForm")[0].reset();
    $('button[name="modalActionButton"]').removeAttr("onclick");
    $("#errorMessages").empty();
    $("#createOrEdit").modal('hide');
}

function newUser() {
    closeModal();
    $("#createOrEdit").modal("toggle");
    $('button[name="modalActionButton"]').attr("onclick", "createOrEdit('new')");
}

function createOrEdit(_type,_id) {
    var sendData = $("#createOrEditForm").serializeArray();
    sendData.push({name: "id", value:_id});
    myAjax('createOrEdit','POST', sendData, function (data) {
        if(data.isSuccess) {
            getAll();
            closeModal();
        }else {
            $("#errorMessages").empty();
            $("#errorMessages").append(data.value);
        }
    });
}

function myAjax(url, type, data, callback) {
    $.ajax({
        url: url,
        type: type,
        data: data,
        success: callback,
        error:function () {
            alert("Something went wrong. Please tr again.")
        }
    });
}
