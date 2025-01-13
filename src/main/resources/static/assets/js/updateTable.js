document.addEventListener("DOMContentLoaded", function (){

    var tableData = JSON.parse(localStorage.getItem("table"));
    console.log(tableData);
    let formattedPrice = tableData.price ? tableData.price.toLocaleString('vi-VN'): 'N/A';

    if(tableData){
        document.getElementById("tableRestaurantName").value = tableData.tableRestaurantName ;
        document.getElementById("tableRestaurantType").value = tableData.tableRestaurantType ;
        document.getElementById("area").value = tableData.area ;
        document.getElementById("price").value = formattedPrice ;
        document.getElementById("amenities").value = tableData.amenities ;
        document.getElementById("currentImage").textContent = tableData.imageTable || "Không có ảnh";
        document.getElementById("quantity").value = tableData.quantity ;


    }
    localStorage.removeItem("table");

    var updateTable = document.getElementById("updateTable");
    updateTable.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        console.log(formData);

        const Message = document.getElementById("message");
        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if (!/^\d+$/.test(document.getElementById("quantity").value)) {
            Message.textContent = "Số bàn trống chỉ được nhập ký tự số";
            Message.classList.add("text-danger");
            return;
        }
        fetch(`http://localhost:8080/api/admin/restaurant/table/update/${tableData.id}`, {
            method: 'PUT',
            body: formData
        }).then(response =>{
            if(!response.ok){
                throw new Error("ErrorUpdate");
            }
            return response.json();
        })
            .then(data => {
                if(data){
                    alert("Cập nhật thành công!")
                    window.location.href = "/admin/restaurant/table";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Bàn này đã tồn tại. Hãy thử lại!");
            })
    })
})

