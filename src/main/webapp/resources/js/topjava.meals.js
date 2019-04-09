// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    );
    $("#filter").click(function () {
        getFiltered();
    });
    $("#clear").click(function () {
        clearForm();
    });
});


function getFiltered() {
    $.ajax({
        type: "GET",
        url: context.ajaxUrl + "filter",
        data: $("#filterParams").serialize(),
        success: function (data) {
            redrawTable(data);
        }
    });
}

function clearForm() {
    $("#filterParams").find(":input").val("");
    updateTable();
}

function updateTable() {
    getFiltered();
}

