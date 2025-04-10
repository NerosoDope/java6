const chitiet = document.querySelector("chitiet")
if (chitiet) {
	chitiet.addEventListener("click", function() {
		document.querySelector(".product-content-right-bottom-content-gtsp").style.display = "none"
	})
}
const butTon = document.querySelector(".product-content-right-bottom-top")
if (butTon) {
	butTon.addEventListener("click", function() {
		document.querySelector(".product-content-right-bottom-content-big").classList.toggle("activeB")
	})
}
const bigImg = document.querySelector(".product-content-left-big-img img")
const smallImg = document.querySelectorAll(".product-content-left-small-img img")
smallImg.forEach(function(imgItem, X) {
	imgItem.addEventListener("click", function() {
		bigImg.src = imgItem.src;
	})
})

// Tìm kiếm sản phẩm
function searchProduct() {
	// Lấy giá trị từ ô tìm kiếm
	var searchQuery = document.getElementById('searchInput').value;

	// Kiểm tra nếu có giá trị nhập vào
	if (searchQuery.trim() !== '') {
		// Chuyển hướng đến trang tìm kiếm với tham số tìm kiếm
		window.location.href = "/search?query=" + encodeURIComponent(searchQuery);
	} else {
		alert("Vui lòng nhập từ khóa tìm kiếm!");
	}
}

// Lọc sản phẩm
function filterProducts() {
	var filterValue = document.getElementById('filter').value;
	var categoryId = /*[[${categoryId}]]*/ 1; // lấy id danh mục
	var page = 1; // Bạn có thể thay đổi cách lấy trang hiện tại nếu cần
	var url = '/products?categoryId=' + categoryId + '&page=' + page + '&filter=' + filterValue;
	window.location.href = url;
}
// Ảnh slide
const imgPosition = document.querySelectorAll(".aspect-ratio-169 img")
const imgContainer = document.querySelector('.aspect-ratio-169')
const dotItem = document.querySelectorAll(".dot")
let imgNumber = imgPosition.length
let index = 0
imgPosition.forEach(function(image, index) {
	image.style.left = index * 100 + "%"
	dotItem[index].addEventListener("click", function() {
		slider(index)
	})
})

function imgSlide() {
	index++;
	if (index >= imgNumber) { index = 0; }
	slider(index)
}

function slider(index) {
	imgContainer.style.left = "-" + index * 100 + "%"
	const dotActive = document.querySelector('.active')
	dotActive.classList.remove("active")
	dotItem[index].classList.add("active")
}
setInterval(imgSlide, 5000)

// Menu slidebar
const itemsliderbar = document.querySelectorAll(".category-left-li")
itemsliderbar.forEach(function(menu, index) {
	menu.addEventListener("click", function() {
		menu.classList.toggle("block")
	})
})
