const checkboxes = document.querySelectorAll('input[type="checkbox"]');
const checkoutTable = document.getElementById('checkoutTable');
const totalPriceElement = document.getElementById('totalPrice');
const checkoutForm = document.getElementById('checkoutForm');
document.addEventListener('DOMContentLoaded', function () {


    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            const id = this.dataset.id;
            const name = this.dataset.name;
            const stock = this.dataset.stock;
            const pricestring = this.dataset.price;
            let numberString = pricestring.replace(/\./g, '');
            let price = parseInt(numberString, 10);

            if (this.checked) {
                const row = checkoutTable.insertRow();
                row.innerHTML = `<td>${name}</td>
                                                  <td>${price.toLocaleString()} VND</td>
                                                  <td>${stock}</td>
                                                  <td style="width: 55px;">
                                                  <input style="width: 54px;" type="number" min="1" value="1" onchange="updateTotal()">
                                                  </td>`;
                row.setAttribute('data-price', price);
                row.setAttribute('data-id', id);

                const hiddenInputId = document.createElement('input');
                hiddenInputId.type = 'hidden';
                hiddenInputId.name = 'productIds[]';
                hiddenInputId.value = id;
                hiddenInputId.classList.add('product-id-' + id);

                const hiddenInputQuantity = document.createElement('input');
                hiddenInputQuantity.type = 'hidden';
                hiddenInputQuantity.name = 'quantities[]';
                hiddenInputQuantity.value = '1';
                hiddenInputQuantity.classList.add('product-quantity-' + id);

                checkoutForm.appendChild(hiddenInputId);
                checkoutForm.appendChild(hiddenInputQuantity);
            } else {
                for (let i = 1; i < checkoutTable.rows.length; i++) {
                    if (checkoutTable.rows[i].getAttribute('data-id') === id) {
                        checkoutTable.deleteRow(i);
                        break;
                    }
                }

                const hiddenInputId = document.querySelector('.product-id-' + id);
                const hiddenInputQuantity = document.querySelector('.product-quantity-' + id);

                if (hiddenInputId) hiddenInputId.remove();
                if (hiddenInputQuantity) hiddenInputQuantity.remove();
            }

            updateTotal();
        });
    });
    // function updateTotal() {
    //     let totalPrice = 0;
    //     console.log("ok")
    //     const quantities = {};
    //     for (let i = 1; i < checkoutTable.rows.length; i++) {
    //         const price = parseInt(checkoutTable.rows[i].getAttribute('data-price'));
    //         const quantity = parseInt(checkoutTable.rows[i].querySelector('input[type="number"]').value);
    //         console.log('so luong: ' + quantity);
    //         const id = checkoutTable.rows[i].getAttribute('data-id');
    //         totalPrice += price * quantity;
    //         quantities[id] = quantity;
    //     }
    //
    //     totalPriceElement.innerHTML = `${totalPrice.toLocaleString()} VND `;
    //
    //     for (let id in quantities) {
    //         const hiddenInputQuantity = document.querySelector('.product-quantity-' + id);
    //         if (hiddenInputQuantity) hiddenInputQuantity.value = quantities[id];
    //     }
    // }

});
function updateTotal() {
    let totalPrice = 0;
    console.log("ok")
    const quantities = {};
    for (let i = 1; i < checkoutTable.rows.length; i++) {
        const price = parseInt(checkoutTable.rows[i].getAttribute('data-price'));
        const quantity = parseInt(checkoutTable.rows[i].querySelector('input[type="number"]').value);
        console.log('so luong: ' + quantity);
        const id = checkoutTable.rows[i].getAttribute('data-id');
        totalPrice += price * quantity;
        quantities[id] = quantity;
    }

    totalPriceElement.innerHTML = `${totalPrice.toLocaleString()} VND `;

    for (let id in quantities) {
        const hiddenInputQuantity = document.querySelector('.product-quantity-' + id);
        if (hiddenInputQuantity) hiddenInputQuantity.value = quantities[id];
    }
}