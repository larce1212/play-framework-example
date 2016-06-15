row_id = location.pathname.split('/')[2]

$ ->
  $.get "/productRequestsByStorekeeper/" + row_id, (rows) ->
    $.each rows, (index, row) ->
      date = $("<td>").text row.date
      veterinario = $("<td>").text row.veterinarioName
      storekeeper = $("<td>").text row.storekeeperName
      status = $("<td>").text row.status
      detail = $("<td>").text row.detail
      finalizado1 = ''
      eliminar1 = ''
      editar1 = ''

      if row.status == "borrador" or row.status == "enviado"
        finalizado1 = '<a href="/productRequest_finish/' + row.id + '" class="btn btn-primary">Finalizar</a>'
        editar1 = '<a href="/productRequest_update/' + row.id + '" class="btn btn-primary">Editar</a>'
        eliminar1 = '<a href="/productRequest_remove/' + row.id + '" class="btn btn-danger">Eliminar</a>'


      links = $("<td>").html finalizado1 + editar1 + eliminar1 + '<a href="/productRequest_show/' + row.id + '" class="btn btn-info">Mostrar</a>'
      $("#rows").append $("<tr>").append(date).append(veterinario).append(storekeeper).append(status).append(detail).append(links)
