package cadelac.framework.pubsub.prog.meter.handler;

import java.io.IOException;

import cadelac.framework.pubsub.BusChannel;
import cadelac.framework.pubsub.generator.Accept;
import cadelac.framework.pubsub.generator.ChannelHandlerGenerator;
import cadelac.framework.pubsub.generator.ChannelInfo;
import cadelac.framework.pubsub.generator.Input;
import cadelac.framework.pubsub.message.system.LifecycleMsg;
import cadelac.framework.pubsub.message.system.Pong;
import cadelac.framework.pubsub.prog.meter.process.ProcessLifecycleMsg;
import cadelac.framework.pubsub.prog.meter.process.ProcessPong;



@ChannelInfo(value=BusChannel.SYSTEM_CHANNEL_STRING, classname="SystemChannelHandler")

@Accept({
	@Input(type=LifecycleMsg.class, handler=ProcessLifecycleMsg.class)
	
	, @Input(type=Pong.class, handler=ProcessPong.class)
	
})


public class SystemChannelHandlerDescriptor 
		extends ChannelHandlerGenerator {

	public static void main(final String[] args_) 
			throws IOException {
		SystemChannelHandlerDescriptor descriptor = 
				new SystemChannelHandlerDescriptor();
		descriptor.generate();
	}
}
