package versions.ver637.pane;

import java.util.ArrayList;

import com.displee.cache.index.Index;
import com.displee.cache.index.archive.Archive;
import com.framework.io.RSStream;
import com.framework.resource.RSResource;

import lombok.extern.slf4j.Slf4j;
import versions.ver637.cache.CacheResource;

@Slf4j
public class InterfaceResource implements RSResource<InterfaceData[]> {

	private static InterfaceData[] INTERFACES;

	@Override
	public InterfaceData[] load() throws Exception {
		log.info("Loading interfaces...");

		InterfaceData[] interfaces = new InterfaceData[1094];
		for (int interfaceID = 0; interfaceID < interfaces.length; interfaceID++) {
			InterfaceData data = new InterfaceData();
			Index index = CacheResource.getLibrary().index(3);
			Archive archive = index.archive(interfaceID);
			if (archive == null)
				continue;

			data.components = new InterfaceComponentData[archive.files().length];
			data.interfaceID = interfaceID;
			for (int componentIndex = 0; componentIndex < data.components.length; componentIndex++) {
				com.displee.cache.index.archive.file.File file = archive.file(componentIndex);
				if (file == null)
					continue;
				byte[] componentData = file.getData();
				if (componentData != null) {
					InterfaceComponentData component = new InterfaceComponentData();
					component.componentHash = (interfaceID << 16);
					component.componentID = componentIndex;
					RSStream stream = new RSStream(componentData);

					int aByte = stream.readUnsignedByte();
					if (aByte == 255)
						aByte = -1;
					component.type = stream.readUnsignedByte();
					if ((component.type & 0x80 ^ 0xffffffff) != -1) {
						component.type &= 0x7f;
						component.aString4765 = stream.readRSString();
					}
					component.anInt4814 = stream.readUnsignedShort();
					component.anInt4850 = stream.readShort();
					component.anInt4816 = stream.readShort();
					component.anInt4693 = stream.readUnsignedShort();
					component.anInt4722 = stream.readUnsignedShort();
					component.aByte4750 = (byte) stream.readByte();
					component.aByte4741 = (byte) stream.readByte();
					component.aByte4720 = (byte) stream.readByte();
					component.aByte4851 = (byte) stream.readByte();
					component.parentId = stream.readUnsignedShort();
					if (component.parentId != 65535) {
						component.parentId = component.parentId + (component.componentHash & ~0xffff);
					} else {
						component.parentId = -1;
					}
					int i_17_ = stream.readUnsignedByte();
					component.hidden = (0x1 & i_17_ ^ 0xffffffff) != -1;
					if (aByte >= 0) {
						component.aBoolean4858 = (i_17_ & 0x2 ^ 0xffffffff) != -1;
					}
					if (component.type == 0) {
						component.anInt4735 = stream.readUnsignedShort();
						component.anInt4691 = stream.readUnsignedShort();
						if ((aByte ^ 0xffffffff) > -1) {
							component.aBoolean4858 = stream.readUnsignedByte() == 1;
						}
					}
					if (component.type == 5) {
						component.anInt4820 = stream.readInt();
						component.anInt4728 = stream.readUnsignedShort();
						int i_18_ = stream.readUnsignedByte();
						component.aBoolean4861 = (i_18_ & 0x1 ^ 0xffffffff) != -1;
						component.aBoolean4738 = (i_18_ & 0x2) != 0;
						component.anInt4757 = stream.readUnsignedByte();
						component.anInt4744 = stream.readUnsignedByte();
						component.anInt4796 = stream.readInt();
						component.aBoolean4732 = stream.readUnsignedByte() == 1;
						component.aBoolean4743 = stream.readUnsignedByte() == 1;
						component.anInt4754 = stream.readInt();
						if ((aByte ^ 0xffffffff) <= -4) {
							component.aBoolean4782 = (stream.readUnsignedByte() ^ 0xffffffff) == -2;
						}
					}
					if (component.type == 6) {
						component.modelType = 1;
						if ((component.componentHash >> 16) > 1144)
							component.anInt4864 = stream.readExtendedSmart();
						else {
							component.anInt4864 = stream.readUnsignedShort();
							if ((component.anInt4864 ^ 0xffffffff) == -65536) {
								component.anInt4864 = -1;
							}
						}
						int i_19_ = stream.readUnsignedByte();
						component.aBoolean4707 = (0x4 & i_19_) == 4;
						boolean bool = (0x1 & i_19_) == 1;
						component.aBoolean4865 = (i_19_ & 0x2 ^ 0xffffffff) == -3;
						component.aBoolean4727 = (0x8 & i_19_ ^ 0xffffffff) == -9;
						if (bool) {
							component.anInt4709 = stream.readShort();
							component.anInt4797 = stream.readShort();
							component.anInt4815 = stream.readUnsignedShort();
							component.anInt4821 = stream.readUnsignedShort();
							component.anInt4682 = stream.readUnsignedShort();
							component.anInt4787 = stream.readUnsignedShort();
						} else if (component.aBoolean4865) {
							component.anInt4709 = stream.readShort();
							component.anInt4797 = stream.readShort();
							component.anInt4842 = stream.readShort();
							component.anInt4815 = stream.readUnsignedShort();
							component.anInt4821 = stream.readUnsignedShort();
							component.anInt4682 = stream.readUnsignedShort();
							component.anInt4787 = stream.readShort();
						}
						if ((component.componentHash >> 16) > 1144)
							component.anInt4773 = stream.readExtendedSmart();
						else {
							component.anInt4773 = stream.readUnsignedShort();
							if ((component.anInt4773 ^ 0xffffffff) == -65536) {
								component.anInt4773 = -1;
							}
						}
						if (component.aByte4750 != 0) {
							component.anInt4800 = stream.readUnsignedShort();
						}
						if ((component.aByte4741 ^ 0xffffffff) != -1) {
							component.anInt4849 = stream.readUnsignedShort();
						}
					}
					if (component.type == 4) {
						if ((component.componentHash >> 16) > 1144)
							component.anInt4759 = stream.readExtendedSmart();
						else {
							component.anInt4759 = stream.readUnsignedShort();
							if ((component.anInt4759 ^ 0xffffffff) == -65536) {
								component.anInt4759 = -1;
							}
						}
						if ((aByte ^ 0xffffffff) <= -3) {
							component.aBoolean4832 = (stream.readUnsignedByte() ^ 0xffffffff) == -2;
						}
						component.text = stream.readRSString();
						component.anInt4697 = stream.readUnsignedByte();
						component.anInt4835 = stream.readUnsignedByte();
						component.anInt4825 = stream.readUnsignedByte();
						component.aBoolean4710 = (stream.readUnsignedByte() ^ 0xffffffff) == -2;
						component.anInt4754 = stream.readInt();
						component.anInt4757 = stream.readUnsignedByte();
						if ((aByte ^ 0xffffffff) <= -1) {
							component.anInt4767 = stream.readUnsignedByte();
						}
					}
					if ((component.type ^ 0xffffffff) == -4) {
						component.anInt4754 = stream.readInt();
						component.aBoolean4769 = (stream.readUnsignedByte() ^ 0xffffffff) == -2;
						component.anInt4757 = stream.readUnsignedByte();
					}
					if ((component.type ^ 0xffffffff) == -10) {
						component.anInt4752 = stream.readUnsignedByte();
						component.anInt4754 = stream.readInt();
						component.aBoolean4721 = stream.readUnsignedByte() == 1;
					}
					int i_20_ = stream.readMedium();
					int i_21_ = stream.readUnsignedByte();
					if ((i_21_ ^ 0xffffffff) != -1) {
						component.aByteArray4806 = new byte[11];
						component.aByteArray4733 = new byte[11];
						component.anIntArray4705 = new int[11];
						for (/**/; i_21_ != 0; i_21_ = stream.readUnsignedByte()) {
							int i_22_ = (i_21_ >> 4) - 1;
							if (i_22_ < 0)
								continue;
							i_21_ = stream.readUnsignedByte() | i_21_ << 8;
							i_21_ &= 0xfff;
							if (i_21_ == 4095) {
								i_21_ = -1;
							}
							byte b_23_ = (byte) stream.readByte();
							if ((b_23_ ^ 0xffffffff) != -1) {
								component.aBoolean4802 = true;
							}
							byte b_24_ = (byte) stream.readByte();
							component.anIntArray4705[i_22_] = i_21_;
							component.aByteArray4806[i_22_] = b_23_;
							component.aByteArray4733[i_22_] = b_24_;
						}
					}
					component.aString4779 = stream.readRSString();
					int i_25_ = stream.readUnsignedByte();
					int i_26_ = 0xf & i_25_;
					if (i_26_ > 0) {
						component.optionNames = new String[i_26_];
						for (int i_27_ = 0; (i_27_ ^ 0xffffffff) > (i_26_ ^ 0xffffffff); i_27_++)
							component.optionNames[i_27_] = stream.readRSString();
					}
					int i_28_ = i_25_ >> 4;
					if ((i_28_ ^ 0xffffffff) < -1) {
						int i_29_ = stream.readUnsignedByte();
						component.anIntArray4863 = new int[i_29_ - -1];
						for (int i_30_ = 0; (i_30_ ^ 0xffffffff) > (component.anIntArray4863.length ^ 0xffffffff); i_30_++)
							component.anIntArray4863[i_30_] = -1;
						component.anIntArray4863[i_29_] = stream.readUnsignedShort();
					}
					if ((i_28_ ^ 0xffffffff) < -2) {
						int i_31_ = stream.readUnsignedByte();
						component.anIntArray4863[i_31_] = stream.readUnsignedShort();
					}
					component.aString4784 = stream.readRSString();
					if (component.aString4784.equals("")) {
						component.aString4784 = null;
					}
					component.anInt4708 = stream.readUnsignedByte();
					component.anInt4795 = stream.readUnsignedByte();
					component.anInt4860 = stream.readUnsignedByte();
					component.aString4786 = stream.readRSString();
					int i_32_ = -1;
					if ((method925(i_20_) ^ 0xffffffff) != -1) {
						i_32_ = stream.readUnsignedShort();
						if (i_32_ == 65535) {
							i_32_ = -1;
						}
						component.anInt4698 = stream.readUnsignedShort();
						if ((component.anInt4698 ^ 0xffffffff) == -65536) {
							component.anInt4698 = -1;
						}
						component.anInt4839 = stream.readUnsignedShort();
						if (component.anInt4839 == 65535) {
							component.anInt4839 = -1;
						}
					}
					if (aByte >= 0) {
						component.anInt4761 = stream.readUnsignedShort();
						if (component.anInt4761 == 65535) {
							component.anInt4761 = -1;
						}
					}

					if (aByte >= 0) {
						int i_33_ = stream.readUnsignedByte();
						for (int i_34_ = 0; i_33_ > i_34_; i_34_++) {
							int i_35_ = stream.readMedium();
							int i_36_ = stream.readInt();
							component.struct.put((long) i_35_, i_36_);
						}
						int i_37_ = stream.readUnsignedByte();
						for (int i_38_ = 0; i_38_ < i_37_; i_38_++) {
							int i_39_ = stream.readMedium();
							String string = stream.readRSString();
							component.struct.put((long) i_39_, string);
						}
					}
					component.scripts0 = readScript(component, stream);
					component.scripts1 = readScript(component, stream);
					component.scripts2 = readScript(component, stream);
					component.scripts3 = readScript(component, stream);
					component.scripts4 = readScript(component, stream);
					component.scripts5 = readScript(component, stream);
					component.scripts6 = readScript(component, stream);
					component.scripts7 = readScript(component, stream);
					component.scripts8 = readScript(component, stream);
					component.scripts = readScript(component, stream);
					if ((aByte ^ 0xffffffff) <= -1)
						component.scripts9 = readScript(component, stream);
					component.scripts10 = readScript(component, stream);
					component.scripts11 = readScript(component, stream);
					component.scripts12 = readScript(component, stream);
					component.scripts13 = readScript(component, stream);
					component.scripts14 = readScript(component, stream);
					component.scripts15 = readScript(component, stream);
					component.scripts16 = readScript(component, stream);
					component.scripts17 = readScript(component, stream);
					component.scripts18 = readScript(component, stream);
					component.scripts19 = readScript(component, stream);
					component.connectedVarps = aMethod2(stream);
					component.connectedContainers = aMethod2(stream);
					component.anIntArray4789 = aMethod2(stream);
					component.connectedVarcs = aMethod2(stream);
					component.anIntArray4805 = aMethod2(stream);

					data.components[componentIndex] = component;
				}
			}
			interfaces[data.interfaceID] = data;
		}
		return interfaces;
	}

	@Override
	public void finish(InterfaceData[] data) {
		INTERFACES = data;

		log.info("Interfaces loaded");
	}

	public static InterfaceData getData(int interfaceID) {
		return INTERFACES[interfaceID];
	}

	private static ArrayList<Object> readScript(InterfaceComponentData data, RSStream buffer) {
		int i = buffer.readUnsignedByte();
		if ((i ^ 0xffffffff) == -1) {
			return null;
		}
		ArrayList<Object> objects = new ArrayList<>(i);
		for (int i_3_ = 0; i > i_3_; i_3_++) {
			int i_4_ = buffer.readUnsignedByte();
			if (i_4_ == 0) {
				objects.add(i_3_, buffer.readInt());
			} else if ((i_4_ ^ 0xffffffff) == -2) {
				objects.add(i_3_, buffer.readRSString());
			}
		}
		data.hasScripts = true;
		return objects;
	}

	private static ArrayList<Integer> aMethod2(RSStream buffer) {
		int i = buffer.readUnsignedByte();
		if (i == 0) {
			return null;
		}
		ArrayList<Integer> is = new ArrayList<>(i);
		for (int i_60_ = 0; i_60_ < i; i_60_++)
			is.add(i_60_, buffer.readInt());
		return is;
	}

	private static final int method925(int i) {
		return (i & 0x3fda8) >> 11;
	}
}
