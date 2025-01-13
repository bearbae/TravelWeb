
document.addEventListener("DOMContentLoaded", function () {
    fetchImageOfTour();
});

async function fetchImageOfTour() {
    var tourIdForImage = JSON.parse(localStorage.getItem("tourIdForImage"));

    if (!tourIdForImage) {
        console.error("Không tìm thấy tourIdForImage trong localStorage");
        return "Loi khong co id";
    }
    try {
        const response = await fetch(`http://localhost:8080/api/admin/image/idTour/${tourIdForImage}`);

        if (!response.ok) {
            console.error("Không thể lấy thông tin ảnh!");
            return;
        }
        const imageData = await response.json();
        displayImage(imageData);
    } catch (error) {
        console.error("Error: ", error);
    }

}
function displayImage(image) {
    var imageData = document.getElementById("imageData");
    let imageName1 = encodeURIComponent(image.img1);
    let imageName2 = encodeURIComponent(image.img2);
    let imageName3 = encodeURIComponent(image.img3);
    let imageName4 = encodeURIComponent(image.img4);
    let imageName5 = encodeURIComponent(image.img5);
    let timestamp = new Date().getTime();
    imageData.innerHTML = `
                    <tr>
                        <td>${image.id}</td>
                        <td><img src="/uploadsTour/${imageName1}?t=${timestamp}" alt="${imageName1}"/></td>
                        <td><img src="/uploadsTour/${imageName2}?t=${timestamp}" alt="${imageName1}"/></td>
                        <td><img src="/uploadsTour/${imageName3}?t=${timestamp}" alt="${imageName1}"/></td>
                        <td><img src="/uploadsTour/${imageName4}?t=${timestamp}" alt="${imageName1}"/></td>
                        <td><img src="/uploadsTour/${imageName5}?t=${timestamp}" alt="${imageName1}"/></td>
                        <td>
                            <button class="btn btn-success" onclick="editImage(${image.id})">Sửa</button>
                            <button class="btn btn-danger" onclick="deleteImage(${image.id})">Xóa</button>
                        </td>
                    </tr>
    `;
}

// Cập nhật ảnh
function editImage(imageId){
    fetch(`http://localhost:8080/api/admin/image/${imageId}`)
        .then(response => {
            if(!response.ok){
                return "Không thể lấy thông tin anh!";
            }
            return response.json();
        })
        .then(data => {
            localStorage.setItem("imageOfTourData", JSON.stringify(data));
            window.location.href = "/admin/tour/image/update";
        })
        .catch(error => console.error("Error: ",error));
}

function deleteImage(imageId){
    let confirmation = confirm("Bạn có chắc chắn muốn xóa không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/admin/image/delete/${imageId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("DeleteError");
                }
                return response.text();
            })
            .then(data => {
                if (data === "Xóa thành công!") {
                    window.location.reload();
                } else {
                    alert("Xóa thất bại. Hãy thử lại!")
                }
            })
    }
}
