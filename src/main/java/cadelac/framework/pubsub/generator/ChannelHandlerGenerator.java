package cadelac.framework.pubsub.generator;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;

import cadelac.framework.blade.core.Utilities;
import cadelac.framework.blade.core.code.generator.LowLevelGenerator;
import cadelac.framework.pubsub.message.MsgDecoder;
import cadelac.framework.pubsub.message.PacketMsg;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Channel;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Key;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Value;




public class ChannelHandlerGenerator extends LowLevelGenerator {
	
	
	
	public ChannelHandlerGenerator() {
		_channelInfoAnnotation = getChannelInfoAnnotation();
		_acceptAnnotation = getAcceptAnnotation();
	}

	public void generate() throws IOException {
		
		final String userDir = System.getProperty("user.dir");
		File sourceFile = new File(userDir, getClassname() + ".java");
		
		
		final String sourceCode = generateSourceCode();
		Utilities.writeFile(sourceFile, sourceCode);
		System.out.println(sourceCode);
	}

	public String generateSourceCode() {
		return generateSourceCode(this.getClass());
	}
	
	@Override
	public String generateSourceCode(Class<?> protoClass_) {

		addPackage(this.getClass());
		
		addLinebreak(1);

		addImport(JSONObject.class);
		
		addLinebreak(1);

		addImport(InstantiatedChannelHandler.class);
		addImport(MsgDecoder.class);
		addImport(PacketMsg.class);


		addLinebreak(1);
		
		addImportBlock();
		
		addLinebreak(1);
		

		
		addLinebreak(1);

		addImport(Channel.class);
		addImport(Key.class);
		addImport(Value.class);
		
		addLinebreak(2);

		
		addCode(code -> {
			code.append(String.format(
					"/* AUTOMATICALLY GENERATED CODE BY FRAMEWORK -- DO NOT EDIT !!! */\n\n"
					+ "@Channel(%s.CHANNEL)\n" //1
					+ "public class %s extends %s {\n\n" //2 //3
					+ "  public static final String EVENT_KEY = \"Event\";\n"
					+ "  public static final String CHANNEL = \"%s\";\n\n" //4

					, getClassname() //1
					, getClassname() //2
					, InstantiatedChannelHandler.class.getSimpleName() //3
					, _channelInfoAnnotation.value() //4
					)); 
		});

		for (Input input : _acceptAnnotation.value()) {			
			addCode(code -> {
				code.append(String.format(
						"  @Key(EVENT_KEY)\n"
								+ "  @Value(%s.EVENT)\n" //1
								+ "  public void on%s(final JSONObject jsonObject)\n" //2
								+ "          throws Exception {\n"
								+ "    jsonObject.put(EVENT_KEY, %s.EVENT);\n" //3
								+ "    final PacketMsg packet =\n"
								+ "        MsgDecoder.directDecodePacket(\n"
								+ "                jsonObject\n"
								+ "                , %s.class);\n" //5
								+ "    packet.audit(%s, CHANNEL);\n" // 6
								+ "    %s.process(packet);\n" //4
								+ "  }\n\n\n"
								, input.type().getSimpleName()    //1
								, input.type().getSimpleName()    //2
								, input.type().getSimpleName()    //3
								, input.type().getSimpleName()    //5
								, (input.audit()) ? "true" : "false"   // 6
								, input.handler().getSimpleName() //4
						));			
			});
		}
		
		addCode(code -> {
			code.append("}\n");
		});
		
		addLinebreak(1);
		
		return getCode();
	}

	

	
	

	protected ChannelHandlerGenerator addImportBlock() {
		for (Input input : _acceptAnnotation.value()) {
			addImport(input.type());
			addImport(input.handler());
		}
			
		return this;
	}
	
	
	protected String getClassname() {
		final String classname = _channelInfoAnnotation.classname();
		return (classname!=null && !classname.isEmpty()) 
				? classname 
				: _channelInfoAnnotation.value();
	}
	
	
	
	
	
	private ChannelInfo getChannelInfoAnnotation() {
		final Annotation annotation = 
				this.getClass().getAnnotation(ChannelInfo.class);
		return (annotation != null) ? (ChannelInfo) annotation : null;
	}
	
	private Accept getAcceptAnnotation() {
		final Annotation annotation = 
				this.getClass().getAnnotation(Accept.class);
		return (annotation != null) ? (Accept) annotation : null;
	}

	


	
	protected final ChannelInfo _channelInfoAnnotation;
	protected final Accept _acceptAnnotation;
}
