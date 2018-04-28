package cadelac.framework.pubsub.prog.meter.handler;

import org.json.JSONObject;

import cadelac.framework.pubsub.generator.InstantiatedChannelHandler;
import cadelac.framework.pubsub.message.MsgDecoder;
import cadelac.framework.pubsub.message.PacketMsg;

import cadelac.framework.pubsub.message.system.LifecycleMsg;
import cadelac.framework.pubsub.prog.meter.process.ProcessLifecycleMsg;
import cadelac.framework.pubsub.message.system.Pong;
import cadelac.framework.pubsub.prog.meter.process.ProcessPong;


import de.jackwhite20.japs.client.sub.impl.handler.annotation.Channel;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Key;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Value;


/* AUTOMATICALLY GENERATED CODE BY FRAMEWORK -- DO NOT EDIT !!! */

@Channel(SystemChannelHandler.CHANNEL)
public class SystemChannelHandler extends InstantiatedChannelHandler {

  public static final String EVENT_KEY = "Event";
  public static final String CHANNEL = "systemChannel";

  @Key(EVENT_KEY)
  @Value(LifecycleMsg.EVENT)
  public void onLifecycleMsg(final JSONObject jsonObject)
          throws Exception {
    jsonObject.put(EVENT_KEY, LifecycleMsg.EVENT);
    final PacketMsg packet =
        MsgDecoder.directDecodePacket(
                jsonObject
                , LifecycleMsg.class);
    packet.audit(CHANNEL);
    ProcessLifecycleMsg.process(packet);
  }


  @Key(EVENT_KEY)
  @Value(Pong.EVENT)
  public void onPong(final JSONObject jsonObject)
          throws Exception {
    jsonObject.put(EVENT_KEY, Pong.EVENT);
    final PacketMsg packet =
        MsgDecoder.directDecodePacket(
                jsonObject
                , Pong.class);
    packet.audit(CHANNEL);
    ProcessPong.process(packet);
  }


}

