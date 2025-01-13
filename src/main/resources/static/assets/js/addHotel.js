document.addEventListener("DOMContentLoaded", function (){

    var addHotel = document.getElementById("addHotel");

    addHotel.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        const Message = document.getElementById("message");
        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if (!/^\d+$/.test(document.getElementById("availableRooms").value)) {
            Message.textContent = "Số phòng trống chỉ được nhập ký tự số";
            Message.classList.add("text-danger");
            return;
        }
        fetch(`http://localhost:8080/api/admin/hotel/add`, {
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
                console.log(data)
                if(data === "Khách sạn đã tồn tại!"){
                    alert("Khách sạn đã tồn tại! Hãy thử lại.");
                } else{
                    alert(data);
                    window.location.href = "/admin/hotel"
                }
            })
            .catch(error => {
                console.error("Error: ", error)
                alert("Lỗi. Hãy thử lại!");
            })
    })
})