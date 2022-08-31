package com.framework.resource;

import java.util.function.Consumer;

final record RSResourceCallback<T> (RSResource<T> resource, Consumer<T> callback) {

}
