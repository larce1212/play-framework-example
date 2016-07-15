row_id = location.pathname.split('/')[2]
$ ->
  $.get "/requestRowsByParent/" + row_id, (rows) ->
    $.each rows, (index, row) ->
      id = $("<td>").text row.id
      requestId = $("<td>").text row.requestId
      productName = $("<td>").text row.productName
      quantity = $("<td>").text row.quantity
      price = $("<td>").text row.price
      status = $("<td>").text row.status
      links = $("<td>").html '<a href="/requestRow_cancel/' + row.id + '" class="btn btn-danger btn-sm">Cancelar</a>' + '<a href="/requestRow_fill/' + row.id + '" class="btn btn-primary btn-sm">Entregar</a>' + '<a href="/requestRow_update/' + row.id + '" class="btn btn-primary btn-sm">Editar</a>' + '<a href="/requestRow_remove/' + row.id + '" class="btn btn-danger btn-sm">Eliminar</a>' + '<a href="/requestRow_show/' + row.id + '" class="btn btn-info btn-sm">Mostrar</a>'
      $("#rows").append $("<tr>").append(id).append(requestId).append(productName).append(quantity).append(price).append(status).append(links)
