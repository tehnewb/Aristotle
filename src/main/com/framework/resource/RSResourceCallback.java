package com.framework.resource;

import java.util.function.Consumer;

public record RSResourceCallback<T> (RSResource<T> resource, Consumer<T> callback) {

}
