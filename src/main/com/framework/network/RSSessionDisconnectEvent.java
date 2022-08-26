package com.framework.network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RSSessionDisconnectEvent {

	private final RSNetworkSession session;

}
