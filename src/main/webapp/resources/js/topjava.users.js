// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
                        "asc"
                    ]
                ]
            })
        }
    );
    $(":checkbox").change(function () {
        let checked = this.checked;
        let checkBox = $(this);
        $.ajax({
            url: context.ajaxUrl + "?id=" + checkBox.closest("tr").attr("id") + "&checked=" + checked,
            type: "PUT"
        }).done(function () {
            if (checked) {
                checkBox.closest("tr").removeClass("notEnabled").addClass("enabled");
            } else {
                checkBox.closest("tr").removeClass("enabled").addClass("notEnabled");
            }
            successNoty(checked ? "Enabled" : "Disabled");
        }).fail(function (jqXHR) {
            checkBox.prop('checked', !checked);
            failNoty(jqXHR);
        });

    });
});

function updateTable() {
    $.get(context.ajaxUrl, function (data) {
        redrawTable(data);
    });
}