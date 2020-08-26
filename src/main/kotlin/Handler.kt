val handler = Handler { request, headers ->
    println("Handler called with $request")
    LambdaResponse(request.toString())
}
