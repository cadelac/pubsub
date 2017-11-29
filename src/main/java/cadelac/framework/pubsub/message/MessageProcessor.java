package cadelac.framework.pubsub.message;

@FunctionalInterface
public interface MessageProcessor {
	public void process(PacketMsg msg_) throws Exception;
}