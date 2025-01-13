document.addEventListener("DOMContentLoaded", function (){
    var addTable = document.getElementById("addTable");
    var restaurantId = localStorage.getItem("restaurantId");

    addTable.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        console.log(restaurantId)
        const Message = document.getElementById("message");
        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if (!/^\d+$/.test(document.getElementById("quantity").value)) {
            Message.textContent = "Số bàn trống chỉ được nhập ký tự số";
            Message.classList.add("text-danger");
            return;
        }
        fetch(`http://localhost:8080/api/admin/restaurant/table/add/${restaurantId}`, {
            method: "POST",
            body: formData
        })
            .then(response => {
                if(!response.ok){
                    throw new Error("Không có phản hồi!")
                }
                return response.text();
            })
            .then(data => {
                if(data === "Bàn này đã tồn tại!"){
                    alert("Bàn này đã tồn tại! Hãy thử lại.")

                } else{
                    alert(data);
                    window.location.href = "/admin/restaurant/table"
                }
            })
            .catch(error => {
                console.error("Error: ", error)
                alert("Lỗi. Hãy thử lại!");
            })
    })
})