document.addEventListener("DOMContentLoaded", function (){
    var addRoom = document.getElementById("addRoom");
    var hotelId = localStorage.getItem("hotelId");

    addRoom.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        console.log(hotelId)
        const Message = document.getElementById("message");
        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if (!/^\d+$/.test(document.getElementById("quantity").value)) {
            Message.textContent = "Số phòng trống chỉ được nhập ký tự số";
            Message.classList.add("text-danger");
            return;
        }
        fetch(`http://localhost:8080/api/admin/hotel/room/add/${hotelId}`, {
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
                if(data === "Phòng này đã tồn tại!"){
                    alert("Phong này đã tồn tại! Hãy thử lại.")

                } else{
                    alert(data);
                    window.location.href = "/admin/hotel/room"
                }
            })
            .catch(error => {
                console.error("Error: ", error)
                alert("Lỗi. Hãy thử lại!");
            })
    })
})