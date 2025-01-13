document.addEventListener("DOMContentLoaded", function (){

    var addFlight = document.getElementById("addFlight");

    addFlight.addEventListener("submit", function (event){
            event.preventDefault();
            const form = event.target;
            const formData = new FormData(form);

            const flight = {} ;
            formData.forEach(function (value,key){
                flight[key] = value;
            })
        console.log(flight);
        const Message = document.getElementById("message");
        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if (!/^\d+$/.test(document.getElementById("Available_seats").value)) {
            Message.textContent = "Ghế chỉ được nhập ký tự số";
            Message.classList.add("text-danger");
            return;
        }
        fetch(`http://localhost:8080/api/admin/flight/add`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(flight)
        })
            .then(response => {
                if(!response.ok){
                    throw new Error("Không có phản hồi!")
                }
                return response.text();
            })
            .then(data => {
                if(data === "Thêm chuyến bay thành công!"){
                    alert(data)
                    window.location.href = "/admin/flight"
                } else{
                    alert("Tên Chuyến bay đã tồn tại! Hãy thử lại.")
                }
            })
            .catch(error => {
                console.error("Error: ", error)
                alert("Lỗi. Hãy thử lại!");
            })
    })
})