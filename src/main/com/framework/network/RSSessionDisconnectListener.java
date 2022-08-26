package com.framework.network;

import com.framework.RSFramework;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RSSessionDisconnectListener implements GenericFutureListener<Future<Void>> {

	private final RSNetworkSession session;

	@Override
	public void operationComplete(Future<Void> future) throws Exception {
		RSFramework.post(new RSSessionDisconnectEvent(session));
	}

}
