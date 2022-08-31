package versions.ver637.map;

import com.framework.io.RSStream;
import com.framework.resource.RSResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import versions.ver637.cache.CacheResource;

@RequiredArgsConstructor
@Slf4j
public class GameObjectResource implements RSResource<GameObjectData[]> {

	private static GameObjectData[] LOCALES;

	@Override
	public void finish(GameObjectData[] data) {
		LOCALES = data;
		log.info("Loaded locales.");
	}

	@Override
	public GameObjectData[] load() throws Exception {
		log.info("Loading locales...");

		GameObjectData[] locales = new GameObjectData[60000];
		for (int objectID = 0; objectID < locales.length; objectID++) {

			byte[] archive = CacheResource.getLibrary().data(CacheResource.LOCALES, objectID >>> 8, objectID & 0xff);
			GameObjectData data = new GameObjectData();
			data.setID(objectID);

			if (archive == null)
				continue;

			RSStream stream = new RSStream(archive);
			for (;;) {
				int opcode = stream.readUnsignedByte();
				if (opcode == 0)
					break;
				if (opcode != 1 && opcode != 5) {
					if (opcode != 2) {
						if (opcode != 14) {
							if (opcode != 15) {
								if (opcode == 17) {
									data.setSolid(false);
									data.setActionType(0);
								} else if (opcode != 18) {
									if (opcode == 19)
										data.setSecondInt(stream.readUnsignedByte());
									else if (opcode == 21)
										data.setAByte3912((byte) 1);
									else if (opcode != 22) {
										if (opcode != 23) {
											if (opcode != 24) {
												if (opcode == 27)
													data.setActionType(1);
												else if (opcode == 28)
													data.setAnInt3892(stream.readUnsignedByte() << 2);
												else if (opcode != 29) {
													if (opcode != 39) {
														if (opcode < 30 || opcode >= 35) {
															if (opcode == 40) {
																int i_53_ = (stream.readUnsignedByte());
																data.setOriginalColors(new short[i_53_]);
																data.setModifiedColors(new short[i_53_]);
																for (int i_54_ = 0; i_53_ > i_54_; i_54_++) {
																	data.getOriginalColors()[i_54_] = (short) (stream.readUnsignedShort());
																	data.getModifiedColors()[i_54_] = (short) (stream.readUnsignedShort());
																}
															} else if (opcode != 41) {
																if (opcode != 42) {
																	if (opcode != 62) {
																		if (opcode != 64) {
																			if (opcode == 65)
																				stream.readUnsignedShort();
																			else if (opcode != 66) {
																				if (opcode != 67) {
																					if (opcode == 69)
																						data.setAccessFlag(stream.readUnsignedByte());
																					else if (opcode != 70) {
																						if (opcode == 71)
																							stream.readShort();
																						else if (opcode != 72) {
																							if (opcode == 73)
																								data.setSecondBool(true);
																							else if (opcode == 74)
																								data.setBlockRangeFlag(true);
																							else if (opcode != 75) {
																								if (opcode != 77 && opcode != 92) {
																									if (opcode == 78) {
																										data.setAnInt3860(stream.readUnsignedShort());
																										data.setAnInt3904(stream.readUnsignedByte());
																									} else if (opcode != 79) {
																										if (opcode == 81) {
																											data.setAByte3912((byte) 2);
																											stream.readUnsignedByte();
																										} else if (opcode != 82) {
																											if (opcode == 88)
																												data.setABoolean3853(false);
																											else if (opcode != 89) {
																												if (opcode == 90)
																													data.setABoolean3870(true);
																												else if (opcode != 91) {
																													if (opcode != 93) {
																														if (opcode == 94)
																															data.setAByte3912((byte) 4);
																														else if (opcode != 95) {
																															if (opcode != 96) {
																																if (opcode == 97)
																																	data.setABoolean3866(true);
																																else if (opcode == 98)
																																	data.setABoolean3923(true);
																																else if (opcode == 99) {
																																	data.setAnInt3857(stream.readUnsignedByte());
																																	data.setAnInt3835(stream.readUnsignedShort());
																																} else if (opcode == 100) {
																																	data.setAnInt3844(stream.readUnsignedByte());
																																	data.setAnInt3913(stream.readUnsignedShort());
																																} else if (opcode != 101) {
																																	if (opcode == 102)
																																		data.setAnInt3838(stream.readUnsignedShort());
																																	else if (opcode == 103)
																																		data.setThirdInt(0);
																																	else if (opcode != 104) {
																																		if (opcode == 105)
																																			data.setABoolean3906(true);
																																		else if (opcode == 106) {
																																			int i_55_ = stream.readUnsignedByte();
																																			int[] anIntArray3869 = new int[i_55_];
																																			int[] anIntArray3833 = new int[i_55_];
																																			int anInt3881 = 0;
																																			for (int i_56_ = 0; i_56_ < i_55_; i_56_++) {
																																				anIntArray3833[i_56_] = stream.readUnsignedShort();
																																				int i_57_ = stream.readUnsignedByte();
																																				anIntArray3869[i_56_] = i_57_;
																																				anInt3881 += i_57_;
																																			}
																																			data.setAnIntArray3869(anIntArray3869);
																																			data.setAnIntArray3833(anIntArray3833);
																																			data.setAnInt3881(anInt3881);
																																		} else if (opcode == 107)
																																			data.setAnInt3851(stream.readUnsignedShort());
																																		else if (opcode >= 150 && opcode < 155) {
																																			data.getOptions()[opcode - 150] = stream.readRSString();
																																		} else if (opcode != 160) {
																																			if (opcode == 162) {
																																				data.setAByte3912((byte) 3);
																																				stream.readInt();
																																			} else if (opcode == 163) {
																																				stream.readByte();
																																				stream.readByte();
																																				stream.readByte();
																																				stream.readByte();
																																			} else if (opcode != 164) {
																																				if (opcode != 165) {
																																					if (opcode != 166) {
																																						if (opcode == 167)
																																							data.setAnInt3921(stream.readUnsignedShort());
																																						else if (opcode != 168) {
																																							if (opcode == 169) {
																																								data.setABoolean3845(true);
																																							} else if (opcode == 170) {
																																								stream.readUnsignedSmart();
																																							} else if (opcode == 171) {
																																								stream.readUnsignedSmart();
																																							} else if (opcode == 173) {
																																								stream.readUnsignedShort();
																																								stream.readUnsignedShort();
																																							} else if (opcode == 177) {
																																								//something = true
																																							} else if (opcode == 178) {
																																								stream.readUnsignedByte();
																																							} else if (opcode == 249) {
																																								int i_58_ = stream.readUnsignedByte();
																																								for (int i_60_ = 0; i_60_ < i_58_; i_60_++) {
																																									boolean bool = stream.readUnsignedByte() == 1;
																																									stream.readMedium();
																																									if (!bool)
																																										stream.readInt();
																																									else
																																										stream.readRSString();
																																								}
																																							}
																																						} else
																																							data.setABoolean3894(true);
																																					} else
																																						stream.readShort();
																																				} else
																																					stream.readShort();
																																			} else
																																				stream.readShort();
																																		} else {
																																			int i_62_ = stream.readUnsignedByte();
																																			int[] anIntArray3908 = new int[i_62_];
																																			for (int i_63_ = 0; i_62_ > i_63_; i_63_++)
																																				anIntArray3908[i_63_] = stream.readUnsignedShort();
																																			data.setAnIntArray3908(anIntArray3908);
																																		}
																																	} else
																																		data.setAnInt3865(stream.readUnsignedByte());
																																} else
																																	data.setAnInt3850(stream.readUnsignedByte());
																															} else
																																data.setABoolean3924(true);
																														} else {
																															data.setAByte3912((byte) 5);
																															stream.readShort();
																														}
																													} else {
																														data.setAByte3912((byte) 3);
																														stream.readUnsignedShort();
																													}
																												} else
																													data.setABoolean3873(true);
																											} else
																												data.setABoolean3895(false);
																										} else
																											data.setABoolean3891(true);
																									} else {
																										data.setAnInt3900(stream.readUnsignedShort());
																										data.setAnInt3905(stream.readUnsignedShort());
																										data.setAnInt3904(stream.readUnsignedByte());
																										int i_64_ = stream.readUnsignedByte();
																										int[] anIntArray3859 = new int[i_64_];
																										for (int i_65_ = 0; i_65_ < i_64_; i_65_++)
																											anIntArray3859[i_65_] = stream.readUnsignedShort();
																										data.setAnIntArray3859(anIntArray3859);
																									}
																								} else {
																									int configFileId = stream.readUnsignedShort();
																									if (configFileId == 65535)
																										configFileId = -1;
																									int configId = stream.readUnsignedShort();
																									if (configId == 65535)
																										configId = -1;

																									data.setConfigFileID(configFileId);
																									data.setConfigID(configId);

																									int i_66_ = -1;
																									if (opcode == 92) {
																										i_66_ = stream.readUnsignedShort();
																										if (i_66_ == 65535)
																											i_66_ = -1;
																									}
																									int i_67_ = stream.readUnsignedByte();
																									int[] childrenIds = new int[i_67_ + 2];
																									for (int i_68_ = 0; i_67_ >= i_68_; i_68_++) {
																										childrenIds[i_68_] = stream.readUnsignedShort();
																										if (childrenIds[i_68_] == 65535)
																											childrenIds[i_68_] = -1;
																									}
																									childrenIds[i_67_ + 1] = i_66_;
																									data.setChildrenIds(childrenIds);
																								}
																							} else
																								data.setAnInt3855(stream.readUnsignedByte());
																						} else
																							stream.readShort();
																					} else
																						stream.readShort();
																				} else
																					stream.readUnsignedShort();
																			} else
																				stream.readUnsignedShort();
																		} else
																			data.setABoolean3872(false);
																	} else
																		data.setABoolean3839(true);
																} else {
																	int i_69_ = stream.readUnsignedByte();
																	byte[] aByteArray3858 = new byte[i_69_];
																	for (int i_70_ = 0; i_70_ < i_69_; i_70_++)
																		aByteArray3858[i_70_] = stream.readByte();
																	data.setAByteArray3858(aByteArray3858);
																}
															} else {
																int i_71_ = stream.readUnsignedByte();
																short[] aShortArray3920 = new short[i_71_];
																short[] aShortArray3919 = new short[i_71_];
																for (int i_72_ = 0; i_71_ > i_72_; i_72_++) {
																	aShortArray3920[i_72_] = (short) stream.readUnsignedShort();
																	aShortArray3919[i_72_] = (short) stream.readUnsignedShort();
																}

																data.setAShortArray3920(aShortArray3920);
																data.setAShortArray3919(aShortArray3919);
															}
														} else
															data.getOptions()[opcode - 30] = stream.readRSString();
													} else
														stream.readByte();
												} else
													stream.readByte();
											} else {
												int anInt3876 = stream.readUnsignedShort();
												if (anInt3876 == 65535)
													anInt3876 = -1;
												data.setAnInt3876(anInt3876);
											}
										} else
											data.setThirdInt(1);
									} else
										data.setABoolean3867(true);
								} else
									data.setSolid(false);
							} else
								data.setSizeX(stream.readUnsignedByte());
						} else
							data.setSizeY(stream.readUnsignedByte());
					} else
						data.setName(stream.readRSString());
				} else {
					boolean aBoolean1162 = false;
					if (opcode == 5 && aBoolean1162)
						skipUseless(stream);
					int i_73_ = stream.readUnsignedByte();
					int[][] anIntArrayArray3916 = new int[i_73_][];
					byte[] aByteArray3899 = new byte[i_73_];
					for (int i_74_ = 0; i_74_ < i_73_; i_74_++) {
						aByteArray3899[i_74_] = stream.readByte();
						int i_75_ = stream.readUnsignedByte();
						anIntArrayArray3916[i_74_] = new int[i_75_];
						for (int i_76_ = 0; i_75_ > i_76_; i_76_++)
							anIntArrayArray3916[i_74_][i_76_] = stream.readUnsignedShort();
						data.setAnIntArrayArray3916(anIntArrayArray3916);
						data.setAByteArray3899(aByteArray3899);
					}
					if (opcode == 5 && !aBoolean1162)
						skipUseless(stream);
				}
			}

			method3287(data);
			if (data.isBlockRangeFlag()) {
				data.setSolid(false);
				data.setActionType(0);
			}
			if (data.getName().toLowerCase().contains("booth")) {
				data.setBlockRangeFlag(false);
				data.setSolid(true);
				data.setActionType(2);
			}
			locales[objectID] = data;
		}
		return locales;
	}

	final void method3287(GameObjectData data) {
		if (data.getSecondInt() == -1) {
			data.setSecondInt(0);
			if (data.getAByteArray3899() != null && data.getAByteArray3899().length == 1 && data.getAByteArray3899()[0] == 10)
				data.setSecondInt(1);
			for (int i_13_ = 0; i_13_ < 5; i_13_++) {
				if (data.getOptions()[i_13_] != null) {
					data.setSecondInt(1);
					break;
				}
			}
		}
		if (data.getAnInt3855() == -1)
			data.setAnInt3855(data.getActionType() != 0 ? 1 : 0);
	}

	private void skipUseless(RSStream stream) {
		int length = stream.readUnsignedByte();
		for (int index = 0; index < length; index++) {
			stream.skip(1);
			stream.skip(stream.readUnsignedByte() * 2);
		}
	}

	public static GameObjectData getData(int objectID) {
		if (objectID < 0 || objectID >= 60000)
			throw new IndexOutOfBoundsException("Object ID must be within 0 and 59999 inclusive");
		return LOCALES[objectID];
	}

}
