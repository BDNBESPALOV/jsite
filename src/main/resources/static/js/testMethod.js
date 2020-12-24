///*
// * Добавляет продукт, определенный его кодом,
// * в корзину покупателя
// * itemCode - код продукта для добавления.
// */
//function addToCart(itemCode) {
//
//  // Возвращает содержимое  XMLHttpRequest
//  var req = newXMLHttpRequest();
//
//  // Оператор для получения сообщения обратного вызова
//  // из объекта запроса
//  var handlerFunction = getReadyStateHandler(req, updateCart);
//  req.onreadystatechange = handlerFunction;
//
//  // Открываем HTTP-соединение с помощью POST-метода к сервлету корзины покупателя.
//  // Третий параметр определяет, что запрос  асинхронный.
//  req.open("POST", "cart.do", true);
//
//  // Определяет, что в содержимом запроса есть данные
//  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//
//  // Посылаем закодированные данные, говорящие о том, что я хочу добавить
//  // определенный продукт в корзину.
//  req.send("action=add&item="+itemCode);
//}