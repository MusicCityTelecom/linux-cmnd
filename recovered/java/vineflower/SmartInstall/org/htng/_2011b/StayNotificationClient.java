package org.htng._2011b;

import javax.xml.namespace.QName;

public final class StayNotificationClient {
   private static final QName SERVICE_NAME = new QName("http://htng.org/2011B", "HTNG_GuestAndRoomStatusService");

   private StayNotificationClient() {
   }

   public static void main(String[] param0) throws Exception {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.OutOfMemoryError: Java heap space
      //   at org.jetbrains.java.decompiler.util.collections.FastSparseSetFactory$FastSparseSet.getCopy(FastSparseSetFactory.java:95)
      //   at org.jetbrains.java.decompiler.util.collections.SFormsFastMapDirect.getCopy(SFormsFastMapDirect.java:67)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.updateLiveMap(SSAUConstructorSparseEx.java:269)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.varReadSingleVersion(SSAUConstructorSparseEx.java:110)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.varRead(SFormsConstructor.java:168)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.VarExprent.processSforms(VarExprent.java:574)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.Exprent.processSforms(Exprent.java:317)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.processSforms(InvocationExprent.java:1894)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.ssaStatements(SFormsConstructor.java:127)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.splitVariables(SSAUConstructorSparseEx.java:45)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:67)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:43)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:238)
      //
      // Bytecode:
      // 0000: getstatic org/htng/_2011b/HTNGGuestAndRoomStatusService.WSDL_LOCATION Ljava/net/URL;
      // 0003: astore 1
      // 0004: aload 0
      // 0005: arraylength
      // 0006: ifle 004a
      // 0009: aload 0
      // 000a: bipush 0
      // 000b: aaload
      // 000c: ifnull 004a
      // 000f: ldc ""
      // 0011: aload 0
      // 0012: bipush 0
      // 0013: aaload
      // 0014: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 0017: ifne 004a
      // 001a: new java/io/File
      // 001d: dup
      // 001e: aload 0
      // 001f: bipush 0
      // 0020: aaload
      // 0021: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 0024: astore 2
      // 0025: aload 2
      // 0026: invokevirtual java/io/File.exists ()Z
      // 0029: ifeq 0037
      // 002c: aload 2
      // 002d: invokevirtual java/io/File.toURI ()Ljava/net/URI;
      // 0030: invokevirtual java/net/URI.toURL ()Ljava/net/URL;
      // 0033: astore 1
      // 0034: goto 0042
      // 0037: new java/net/URL
      // 003a: dup
      // 003b: aload 0
      // 003c: bipush 0
      // 003d: aaload
      // 003e: invokespecial java/net/URL.<init> (Ljava/lang/String;)V
      // 0041: astore 1
      // 0042: goto 004a
      // 0045: astore 3
      // 0046: aload 3
      // 0047: invokevirtual java/net/MalformedURLException.printStackTrace ()V
      // 004a: new org/htng/_2011b/HTNGGuestAndRoomStatusService
      // 004d: dup
      // 004e: aload 1
      // 004f: getstatic org/htng/_2011b/StayNotificationClient.SERVICE_NAME Ljavax/xml/namespace/QName;
      // 0052: invokespecial org/htng/_2011b/HTNGGuestAndRoomStatusService.<init> (Ljava/net/URL;Ljavax/xml/namespace/QName;)V
      // 0055: astore 2
      // 0056: aload 2
      // 0057: invokevirtual org/htng/_2011b/HTNGGuestAndRoomStatusService.getStayNotification ()Lorg/htng/_2011b/StayNotification;
      // 005a: astore 3
      // 005b: getstatic java/lang/System.out Ljava/io/PrintStream;
      // 005e: ldc "Invoking checkedIn..."
      // 0060: invokevirtual java/io/PrintStream.println (Ljava/lang/String;)V
      // 0063: new org/htng/_2011b/HTNGHotelCheckInNotifRQ
      // 0066: dup
      // 0067: invokespecial org/htng/_2011b/HTNGHotelCheckInNotifRQ.<init> ()V
      // 006a: astore 4
      // 006c: new org/opentravel/ota/_2003/_05/POSType
      // 006f: dup
      // 0070: invokespecial org/opentravel/ota/_2003/_05/POSType.<init> ()V
      // 0073: astore 5
      // 0075: new java/util/ArrayList
      // 0078: dup
      // 0079: invokespecial java/util/ArrayList.<init> ()V
      // 007c: astore 6
      // 007e: new org/opentravel/ota/_2003/_05/SourceType
      // 0081: dup
      // 0082: invokespecial org/opentravel/ota/_2003/_05/SourceType.<init> ()V
      // 0085: astore 7
      // 0087: new org/opentravel/ota/_2003/_05/SourceType$RequestorID
      // 008a: dup
      // 008b: invokespecial org/opentravel/ota/_2003/_05/SourceType$RequestorID.<init> ()V
      // 008e: astore 8
      // 0090: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 0093: dup
      // 0094: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 0097: astore 9
      // 0099: aload 9
      // 009b: ldc "Value-1672389424"
      // 009d: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 00a0: aload 9
      // 00a2: ldc "Division1206315956"
      // 00a4: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 00a7: aload 9
      // 00a9: ldc "Department-109289828"
      // 00ab: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 00ae: aload 9
      // 00b0: ldc "CompanyShortName1371585275"
      // 00b2: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 00b5: aload 9
      // 00b7: ldc "TravelSector1566416014"
      // 00b9: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 00bc: aload 9
      // 00be: ldc "Code-1956506811"
      // 00c0: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 00c3: aload 9
      // 00c5: ldc "CodeContext91415094"
      // 00c7: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 00ca: aload 8
      // 00cc: aload 9
      // 00ce: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 00d1: aload 8
      // 00d3: ldc "URL-410628672"
      // 00d5: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setURL (Ljava/lang/String;)V
      // 00d8: aload 8
      // 00da: ldc "Type2118549906"
      // 00dc: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setType (Ljava/lang/String;)V
      // 00df: aload 8
      // 00e1: ldc "Instance115025505"
      // 00e3: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setInstance (Ljava/lang/String;)V
      // 00e6: aload 8
      // 00e8: ldc "IDContext988391687"
      // 00ea: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setIDContext (Ljava/lang/String;)V
      // 00ed: aload 8
      // 00ef: ldc "ID124928880"
      // 00f1: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setID (Ljava/lang/String;)V
      // 00f4: aload 8
      // 00f6: ldc "MessagePassword-1226208394"
      // 00f8: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setMessagePassword (Ljava/lang/String;)V
      // 00fb: aload 7
      // 00fd: aload 8
      // 00ff: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setRequestorID (Lorg/opentravel/ota/_2003/_05/SourceType$RequestorID;)V
      // 0102: new org/opentravel/ota/_2003/_05/SourceType$Position
      // 0105: dup
      // 0106: invokespecial org/opentravel/ota/_2003/_05/SourceType$Position.<init> ()V
      // 0109: astore 10
      // 010b: aload 10
      // 010d: ldc "Latitude-2070344196"
      // 010f: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setLatitude (Ljava/lang/String;)V
      // 0112: aload 10
      // 0114: ldc "Longitude-1128692500"
      // 0116: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setLongitude (Ljava/lang/String;)V
      // 0119: aload 10
      // 011b: ldc "Altitude1043408685"
      // 011d: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setAltitude (Ljava/lang/String;)V
      // 0120: aload 10
      // 0122: ldc "AltitudeUnitOfMeasureCode1356266931"
      // 0124: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setAltitudeUnitOfMeasureCode (Ljava/lang/String;)V
      // 0127: aload 10
      // 0129: ldc "PositionAccuracy-548819818"
      // 012b: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setPositionAccuracy (Ljava/lang/String;)V
      // 012e: aload 7
      // 0130: aload 10
      // 0132: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setPosition (Lorg/opentravel/ota/_2003/_05/SourceType$Position;)V
      // 0135: new org/opentravel/ota/_2003/_05/SourceType$BookingChannel
      // 0138: dup
      // 0139: invokespecial org/opentravel/ota/_2003/_05/SourceType$BookingChannel.<init> ()V
      // 013c: astore 11
      // 013e: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 0141: dup
      // 0142: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 0145: astore 12
      // 0147: aload 12
      // 0149: ldc "Value-663988523"
      // 014b: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 014e: aload 12
      // 0150: ldc "Division1925410287"
      // 0152: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 0155: aload 12
      // 0157: ldc "Department-900925869"
      // 0159: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 015c: aload 12
      // 015e: ldc "CompanyShortName54542563"
      // 0160: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 0163: aload 12
      // 0165: ldc "TravelSector-1046714730"
      // 0167: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 016a: aload 12
      // 016c: ldc "Code644764623"
      // 016e: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 0171: aload 12
      // 0173: ldc "CodeContext1765850090"
      // 0175: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 0178: aload 11
      // 017a: aload 12
      // 017c: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 017f: aload 11
      // 0181: ldc "Type1516555949"
      // 0183: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setType (Ljava/lang/String;)V
      // 0186: aload 11
      // 0188: bipush 0
      // 0189: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 018c: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setPrimary (Ljava/lang/Boolean;)V
      // 018f: aload 7
      // 0191: aload 11
      // 0193: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setBookingChannel (Lorg/opentravel/ota/_2003/_05/SourceType$BookingChannel;)V
      // 0196: aload 7
      // 0198: ldc "AgentSine-1182348926"
      // 019a: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAgentSine (Ljava/lang/String;)V
      // 019d: aload 7
      // 019f: ldc "PseudoCityCode1155167942"
      // 01a1: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setPseudoCityCode (Ljava/lang/String;)V
      // 01a4: aload 7
      // 01a6: ldc "ISOCountry-768167431"
      // 01a8: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setISOCountry (Ljava/lang/String;)V
      // 01ab: aload 7
      // 01ad: ldc "ISOCurrency1517872425"
      // 01af: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setISOCurrency (Ljava/lang/String;)V
      // 01b2: aload 7
      // 01b4: ldc "AgentDutyCode2042574370"
      // 01b6: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAgentDutyCode (Ljava/lang/String;)V
      // 01b9: aload 7
      // 01bb: ldc "AirlineVendorID-782841499"
      // 01bd: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAirlineVendorID (Ljava/lang/String;)V
      // 01c0: aload 7
      // 01c2: ldc "AirportCode1019396201"
      // 01c4: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAirportCode (Ljava/lang/String;)V
      // 01c7: aload 7
      // 01c9: ldc "FirstDepartPoint1692890642"
      // 01cb: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setFirstDepartPoint (Ljava/lang/String;)V
      // 01ce: aload 7
      // 01d0: ldc "ERSPUserID-639037132"
      // 01d2: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setERSPUserID (Ljava/lang/String;)V
      // 01d5: aload 7
      // 01d7: ldc "TerminalID25492519"
      // 01d9: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setTerminalID (Ljava/lang/String;)V
      // 01dc: aload 6
      // 01de: aload 7
      // 01e0: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 01e5: pop
      // 01e6: aload 5
      // 01e8: invokevirtual org/opentravel/ota/_2003/_05/POSType.getSource ()Ljava/util/List;
      // 01eb: aload 6
      // 01ed: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 01f2: pop
      // 01f3: aload 4
      // 01f5: aload 5
      // 01f7: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setPOS (Lorg/opentravel/ota/_2003/_05/POSType;)V
      // 01fa: new org/opentravel/ota/_2003/_05/UniqueIDType
      // 01fd: dup
      // 01fe: invokespecial org/opentravel/ota/_2003/_05/UniqueIDType.<init> ()V
      // 0201: astore 13
      // 0203: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 0206: dup
      // 0207: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 020a: astore 14
      // 020c: aload 14
      // 020e: ldc "Value1351589088"
      // 0210: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 0213: aload 14
      // 0215: ldc "Division2068352364"
      // 0217: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 021a: aload 14
      // 021c: ldc "Department1386715366"
      // 021e: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 0221: aload 14
      // 0223: ldc "CompanyShortName-2125483964"
      // 0225: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 0228: aload 14
      // 022a: ldc "TravelSector-158785173"
      // 022c: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 022f: aload 14
      // 0231: ldc "Code-1750074703"
      // 0233: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 0236: aload 14
      // 0238: ldc "CodeContext-1885263720"
      // 023a: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 023d: aload 13
      // 023f: aload 14
      // 0241: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 0244: aload 13
      // 0246: ldc "URL-2127999489"
      // 0248: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setURL (Ljava/lang/String;)V
      // 024b: aload 13
      // 024d: ldc "Type35521374"
      // 024f: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setType (Ljava/lang/String;)V
      // 0252: aload 13
      // 0254: ldc "Instance-354377349"
      // 0256: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setInstance (Ljava/lang/String;)V
      // 0259: aload 13
      // 025b: ldc "IDContext-107940963"
      // 025d: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setIDContext (Ljava/lang/String;)V
      // 0260: aload 13
      // 0262: ldc "ID-1844141541"
      // 0264: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setID (Ljava/lang/String;)V
      // 0267: aload 4
      // 0269: aload 13
      // 026b: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setUniqueID (Lorg/opentravel/ota/_2003/_05/UniqueIDType;)V
      // 026e: new org/htng/_2011b/HTNGRequestBaseType$PropertyInfo
      // 0271: dup
      // 0272: invokespecial org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.<init> ()V
      // 0275: astore 15
      // 0277: aload 15
      // 0279: ldc "ChainCode1565595069"
      // 027b: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setChainCode (Ljava/lang/String;)V
      // 027e: aload 15
      // 0280: ldc "BrandCode226203153"
      // 0282: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setBrandCode (Ljava/lang/String;)V
      // 0285: aload 15
      // 0287: ldc "HotelCode-1418516414"
      // 0289: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCode (Ljava/lang/String;)V
      // 028c: aload 15
      // 028e: ldc "HotelCityCode1888837262"
      // 0290: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCityCode (Ljava/lang/String;)V
      // 0293: aload 15
      // 0295: ldc "HotelName1118948830"
      // 0297: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelName (Ljava/lang/String;)V
      // 029a: aload 15
      // 029c: ldc "HotelCodeContext1965813572"
      // 029e: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCodeContext (Ljava/lang/String;)V
      // 02a1: aload 15
      // 02a3: ldc "ChainName-64897266"
      // 02a5: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setChainName (Ljava/lang/String;)V
      // 02a8: aload 15
      // 02aa: ldc "BrandName-1990495628"
      // 02ac: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setBrandName (Ljava/lang/String;)V
      // 02af: aload 15
      // 02b1: ldc "AreaID1480977996"
      // 02b3: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setAreaID (Ljava/lang/String;)V
      // 02b6: aload 4
      // 02b8: aload 15
      // 02ba: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setPropertyInfo (Lorg/htng/_2011b/HTNGRequestBaseType$PropertyInfo;)V
      // 02bd: aload 4
      // 02bf: ldc "EchoToken467493244"
      // 02c1: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setEchoToken (Ljava/lang/String;)V
      // 02c4: aload 4
      // 02c6: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 02c9: ldc "2020-03-24T09:54:33.635+08:00"
      // 02cb: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 02ce: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setTimeStamp (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 02d1: aload 4
      // 02d3: ldc "Target240679499"
      // 02d5: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setTarget (Ljava/lang/String;)V
      // 02d8: aload 4
      // 02da: ldc "TargetName-399020730"
      // 02dc: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setTargetName (Ljava/lang/String;)V
      // 02df: aload 4
      // 02e1: new java/math/BigDecimal
      // 02e4: dup
      // 02e5: ldc "-8455372043333339379.2556595178619363659"
      // 02e7: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 02ea: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setVersion (Ljava/math/BigDecimal;)V
      // 02ed: aload 4
      // 02ef: ldc "TransactionIdentifier-180330138"
      // 02f1: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setTransactionIdentifier (Ljava/lang/String;)V
      // 02f4: aload 4
      // 02f6: new java/math/BigInteger
      // 02f9: dup
      // 02fa: ldc "-85375650654700813271972865477001386819"
      // 02fc: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 02ff: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setSequenceNmbr (Ljava/math/BigInteger;)V
      // 0302: aload 4
      // 0304: ldc "TransactionStatusCode137781740"
      // 0306: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setTransactionStatusCode (Ljava/lang/String;)V
      // 0309: aload 4
      // 030b: bipush 0
      // 030c: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 030f: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setRetransmissionIndicator (Ljava/lang/Boolean;)V
      // 0312: aload 4
      // 0314: ldc "CorrelationID-1426279024"
      // 0316: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setCorrelationID (Ljava/lang/String;)V
      // 0319: aload 4
      // 031b: ldc "PrimaryLangID-1277144256"
      // 031d: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setPrimaryLangID (Ljava/lang/String;)V
      // 0320: aload 4
      // 0322: ldc "AltLangID-2007409157"
      // 0324: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setAltLangID (Ljava/lang/String;)V
      // 0327: new org/htng/_2011b/HTNGCollectionOfUniqueIDs
      // 032a: dup
      // 032b: invokespecial org/htng/_2011b/HTNGCollectionOfUniqueIDs.<init> ()V
      // 032e: astore 16
      // 0330: new org/opentravel/ota/_2003/_05/UniqueIDType
      // 0333: dup
      // 0334: invokespecial org/opentravel/ota/_2003/_05/UniqueIDType.<init> ()V
      // 0337: astore 17
      // 0339: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 033c: dup
      // 033d: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 0340: astore 18
      // 0342: aload 18
      // 0344: ldc "Value126972020"
      // 0346: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 0349: aload 18
      // 034b: ldc "Division1916371967"
      // 034d: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 0350: aload 18
      // 0352: ldc "Department-1225422939"
      // 0354: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 0357: aload 18
      // 0359: ldc "CompanyShortName-1271412658"
      // 035b: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 035e: aload 18
      // 0360: ldc "TravelSector905461507"
      // 0362: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 0365: aload 18
      // 0367: ldc "Code1011558312"
      // 0369: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 036c: aload 18
      // 036e: ldc "CodeContext-1310249358"
      // 0370: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 0373: aload 17
      // 0375: aload 18
      // 0377: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 037a: aload 17
      // 037c: ldc "URL1501076871"
      // 037e: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setURL (Ljava/lang/String;)V
      // 0381: aload 17
      // 0383: ldc "Type561668315"
      // 0385: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setType (Ljava/lang/String;)V
      // 0388: aload 17
      // 038a: ldc "Instance703931242"
      // 038c: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setInstance (Ljava/lang/String;)V
      // 038f: aload 17
      // 0391: ldc "IDContext1742400670"
      // 0393: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setIDContext (Ljava/lang/String;)V
      // 0396: aload 17
      // 0398: ldc "ID1776076913"
      // 039a: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setID (Ljava/lang/String;)V
      // 039d: aload 16
      // 039f: aload 17
      // 03a1: invokevirtual org/htng/_2011b/HTNGCollectionOfUniqueIDs.setUniqueID (Lorg/opentravel/ota/_2003/_05/UniqueIDType;)V
      // 03a4: aload 4
      // 03a6: aload 16
      // 03a8: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setAffectedGuests (Lorg/htng/_2011b/HTNGCollectionOfUniqueIDs;)V
      // 03ab: new org/htng/_2011b/HTNGRoomElementType
      // 03ae: dup
      // 03af: invokespecial org/htng/_2011b/HTNGRoomElementType.<init> ()V
      // 03b2: astore 19
      // 03b4: new org/opentravel/ota/_2003/_05/RoomTypeType
      // 03b7: dup
      // 03b8: invokespecial org/opentravel/ota/_2003/_05/RoomTypeType.<init> ()V
      // 03bb: astore 20
      // 03bd: new org/opentravel/ota/_2003/_05/ParagraphType
      // 03c0: dup
      // 03c1: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 03c4: astore 21
      // 03c6: new java/util/ArrayList
      // 03c9: dup
      // 03ca: invokespecial java/util/ArrayList.<init> ()V
      // 03cd: astore 22
      // 03cf: aload 21
      // 03d1: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 03d4: aload 22
      // 03d6: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 03db: pop
      // 03dc: aload 21
      // 03de: ldc "Name-920146955"
      // 03e0: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 03e3: aload 21
      // 03e5: new java/math/BigInteger
      // 03e8: dup
      // 03e9: ldc "-59889123756518409042637747037238139603"
      // 03eb: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 03ee: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 03f1: aload 21
      // 03f3: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 03f6: ldc "2020-03-24T09:54:33.635+08:00"
      // 03f8: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 03fb: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 03fe: aload 21
      // 0400: ldc "CreatorID-407706415"
      // 0402: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 0405: aload 21
      // 0407: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 040a: ldc "2020-03-24T09:54:33.636+08:00"
      // 040c: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 040f: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0412: aload 21
      // 0414: ldc "LastModifierID692819144"
      // 0416: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 0419: aload 21
      // 041b: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 041e: ldc "2020-03-24T09:54:33.636+08:00"
      // 0420: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 0423: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0426: aload 21
      // 0428: ldc "Language-193696535"
      // 042a: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 042d: aload 20
      // 042f: aload 21
      // 0431: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomDescription (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 0434: new org/opentravel/ota/_2003/_05/AdditionalDetailsType
      // 0437: dup
      // 0438: invokespecial org/opentravel/ota/_2003/_05/AdditionalDetailsType.<init> ()V
      // 043b: astore 23
      // 043d: new java/util/ArrayList
      // 0440: dup
      // 0441: invokespecial java/util/ArrayList.<init> ()V
      // 0444: astore 24
      // 0446: aload 23
      // 0448: invokevirtual org/opentravel/ota/_2003/_05/AdditionalDetailsType.getAdditionalDetail ()Ljava/util/List;
      // 044b: aload 24
      // 044d: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0452: pop
      // 0453: aload 20
      // 0455: aload 23
      // 0457: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setAdditionalDetails (Lorg/opentravel/ota/_2003/_05/AdditionalDetailsType;)V
      // 045a: new org/opentravel/ota/_2003/_05/RoomTypeType$Amenities
      // 045d: dup
      // 045e: invokespecial org/opentravel/ota/_2003/_05/RoomTypeType$Amenities.<init> ()V
      // 0461: astore 25
      // 0463: new java/util/ArrayList
      // 0466: dup
      // 0467: invokespecial java/util/ArrayList.<init> ()V
      // 046a: astore 26
      // 046c: aload 25
      // 046e: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType$Amenities.getAmenity ()Ljava/util/List;
      // 0471: aload 26
      // 0473: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0478: pop
      // 0479: aload 20
      // 047b: aload 25
      // 047d: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setAmenities (Lorg/opentravel/ota/_2003/_05/RoomTypeType$Amenities;)V
      // 0480: new java/util/ArrayList
      // 0483: dup
      // 0484: invokespecial java/util/ArrayList.<init> ()V
      // 0487: astore 27
      // 0489: aload 20
      // 048b: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getOccupancy ()Ljava/util/List;
      // 048e: aload 27
      // 0490: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0495: pop
      // 0496: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 0499: dup
      // 049a: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 049d: astore 28
      // 049f: new java/util/ArrayList
      // 04a2: dup
      // 04a3: invokespecial java/util/ArrayList.<init> ()V
      // 04a6: astore 29
      // 04a8: aload 28
      // 04aa: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 04ad: aload 29
      // 04af: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 04b4: pop
      // 04b5: aload 20
      // 04b7: aload 28
      // 04b9: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 04bc: aload 20
      // 04be: new java/math/BigInteger
      // 04c1: dup
      // 04c2: ldc "-83150808690360532076410612165878085960"
      // 04c4: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 04c7: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setNumberOfUnits (Ljava/math/BigInteger;)V
      // 04ca: aload 20
      // 04cc: bipush 1
      // 04cd: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 04d0: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsRoom (Ljava/lang/Boolean;)V
      // 04d3: aload 20
      // 04d5: bipush 0
      // 04d6: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 04d9: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsConverted (Ljava/lang/Boolean;)V
      // 04dc: aload 20
      // 04de: bipush 0
      // 04df: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 04e2: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsAlternate (Ljava/lang/Boolean;)V
      // 04e5: aload 20
      // 04e7: ldc "ReqdGuaranteeType-663297249"
      // 04e9: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setReqdGuaranteeType (Ljava/lang/String;)V
      // 04ec: aload 20
      // 04ee: ldc "RoomType-1198531578"
      // 04f0: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomType (Ljava/lang/String;)V
      // 04f3: aload 20
      // 04f5: ldc "RoomTypeCode-814774497"
      // 04f7: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomTypeCode (Ljava/lang/String;)V
      // 04fa: aload 20
      // 04fc: ldc "RoomCategory988496998"
      // 04fe: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomCategory (Ljava/lang/String;)V
      // 0501: aload 20
      // 0503: ldc "RoomID-1586748212"
      // 0505: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomID (Ljava/lang/String;)V
      // 0508: aload 20
      // 050a: ldc 199912142
      // 050c: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 050f: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setFloor (Ljava/lang/Integer;)V
      // 0512: aload 20
      // 0514: ldc "InvBlockCode-1956325984"
      // 0516: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setInvBlockCode (Ljava/lang/String;)V
      // 0519: aload 20
      // 051b: ldc "RoomLocationCode-136502313"
      // 051d: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomLocationCode (Ljava/lang/String;)V
      // 0520: aload 20
      // 0522: ldc_w "RoomViewCode-1175486003"
      // 0525: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomViewCode (Ljava/lang/String;)V
      // 0528: new java/util/ArrayList
      // 052b: dup
      // 052c: invokespecial java/util/ArrayList.<init> ()V
      // 052f: astore 30
      // 0531: aload 20
      // 0533: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getBedTypeCode ()Ljava/util/List;
      // 0536: aload 30
      // 0538: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 053d: pop
      // 053e: aload 20
      // 0540: bipush 0
      // 0541: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0544: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setNonSmoking (Ljava/lang/Boolean;)V
      // 0547: aload 20
      // 0549: ldc_w "Configuration-1732865790"
      // 054c: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setConfiguration (Ljava/lang/String;)V
      // 054f: aload 20
      // 0551: ldc_w "SizeMeasurement417073211"
      // 0554: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setSizeMeasurement (Ljava/lang/String;)V
      // 0557: aload 20
      // 0559: ldc_w 1025184798
      // 055c: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 055f: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setQuantity (Ljava/lang/Integer;)V
      // 0562: aload 20
      // 0564: bipush 1
      // 0565: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0568: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setComposite (Ljava/lang/Boolean;)V
      // 056b: aload 20
      // 056d: ldc_w "RoomClassificationCode-1522218479"
      // 0570: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomClassificationCode (Ljava/lang/String;)V
      // 0573: aload 20
      // 0575: ldc_w "RoomArchitectureCode-579977428"
      // 0578: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomArchitectureCode (Ljava/lang/String;)V
      // 057b: aload 20
      // 057d: ldc_w "RoomGender-625926269"
      // 0580: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomGender (Ljava/lang/String;)V
      // 0583: aload 20
      // 0585: bipush 1
      // 0586: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0589: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setSharedRoomInd (Ljava/lang/Boolean;)V
      // 058c: aload 20
      // 058e: ldc_w "PromotionCode1149396988"
      // 0591: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setPromotionCode (Ljava/lang/String;)V
      // 0594: new java/util/ArrayList
      // 0597: dup
      // 0598: invokespecial java/util/ArrayList.<init> ()V
      // 059b: astore 31
      // 059d: aload 20
      // 059f: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getPromotionVendorCode ()Ljava/util/List;
      // 05a2: aload 31
      // 05a4: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 05a9: pop
      // 05aa: aload 19
      // 05ac: aload 20
      // 05ae: invokevirtual org/htng/_2011b/HTNGRoomElementType.setRoomType (Lorg/opentravel/ota/_2003/_05/RoomTypeType;)V
      // 05b1: new org/htng/_2011b/HTNGTelephoneExtensionType
      // 05b4: dup
      // 05b5: invokespecial org/htng/_2011b/HTNGTelephoneExtensionType.<init> ()V
      // 05b8: astore 32
      // 05ba: new java/util/ArrayList
      // 05bd: dup
      // 05be: invokespecial java/util/ArrayList.<init> ()V
      // 05c1: astore 33
      // 05c3: aload 32
      // 05c5: invokevirtual org/htng/_2011b/HTNGTelephoneExtensionType.getTelephoneExtention ()Ljava/util/List;
      // 05c8: aload 33
      // 05ca: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 05cf: pop
      // 05d0: aload 19
      // 05d2: aload 32
      // 05d4: invokevirtual org/htng/_2011b/HTNGRoomElementType.setTelephoneExtensions (Lorg/htng/_2011b/HTNGTelephoneExtensionType;)V
      // 05d7: getstatic org/htng/_2011b/HTNGHousekeepingStatusType.PICKUP Lorg/htng/_2011b/HTNGHousekeepingStatusType;
      // 05da: astore 34
      // 05dc: aload 19
      // 05de: aload 34
      // 05e0: invokevirtual org/htng/_2011b/HTNGRoomElementType.setHKStatus (Lorg/htng/_2011b/HTNGHousekeepingStatusType;)V
      // 05e3: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 05e6: dup
      // 05e7: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 05ea: astore 35
      // 05ec: new java/util/ArrayList
      // 05ef: dup
      // 05f0: invokespecial java/util/ArrayList.<init> ()V
      // 05f3: astore 36
      // 05f5: aload 35
      // 05f7: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 05fa: aload 36
      // 05fc: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0601: pop
      // 0602: aload 19
      // 0604: aload 35
      // 0606: invokevirtual org/htng/_2011b/HTNGRoomElementType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 0609: aload 19
      // 060b: ldc_w "RoomID667279512"
      // 060e: invokevirtual org/htng/_2011b/HTNGRoomElementType.setRoomID (Ljava/lang/String;)V
      // 0611: new org/htng/_2011b/HTNGComponentRoomsType
      // 0614: dup
      // 0615: invokespecial org/htng/_2011b/HTNGComponentRoomsType.<init> ()V
      // 0618: astore 37
      // 061a: new java/util/ArrayList
      // 061d: dup
      // 061e: invokespecial java/util/ArrayList.<init> ()V
      // 0621: astore 38
      // 0623: aload 37
      // 0625: invokevirtual org/htng/_2011b/HTNGComponentRoomsType.getComponentRoom ()Ljava/util/List;
      // 0628: aload 38
      // 062a: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 062f: pop
      // 0630: aload 19
      // 0632: aload 37
      // 0634: invokevirtual org/htng/_2011b/HTNGRoomElementType.setComponentRooms (Lorg/htng/_2011b/HTNGComponentRoomsType;)V
      // 0637: aload 4
      // 0639: aload 19
      // 063b: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setRoom (Lorg/htng/_2011b/HTNGRoomElementType;)V
      // 063e: new org/opentravel/ota/_2003/_05/HotelReservationsType
      // 0641: dup
      // 0642: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType.<init> ()V
      // 0645: astore 39
      // 0647: new java/util/ArrayList
      // 064a: dup
      // 064b: invokespecial java/util/ArrayList.<init> ()V
      // 064e: astore 40
      // 0650: new org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation
      // 0653: dup
      // 0654: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.<init> ()V
      // 0657: astore 41
      // 0659: new org/opentravel/ota/_2003/_05/POSType
      // 065c: dup
      // 065d: invokespecial org/opentravel/ota/_2003/_05/POSType.<init> ()V
      // 0660: astore 42
      // 0662: new java/util/ArrayList
      // 0665: dup
      // 0666: invokespecial java/util/ArrayList.<init> ()V
      // 0669: astore 43
      // 066b: aload 42
      // 066d: invokevirtual org/opentravel/ota/_2003/_05/POSType.getSource ()Ljava/util/List;
      // 0670: aload 43
      // 0672: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0677: pop
      // 0678: aload 41
      // 067a: aload 42
      // 067c: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setPOS (Lorg/opentravel/ota/_2003/_05/POSType;)V
      // 067f: new java/util/ArrayList
      // 0682: dup
      // 0683: invokespecial java/util/ArrayList.<init> ()V
      // 0686: astore 44
      // 0688: aload 41
      // 068a: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.getUniqueID ()Ljava/util/List;
      // 068d: aload 44
      // 068f: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0694: pop
      // 0695: new org/opentravel/ota/_2003/_05/RoomStaysType
      // 0698: dup
      // 0699: invokespecial org/opentravel/ota/_2003/_05/RoomStaysType.<init> ()V
      // 069c: astore 45
      // 069e: new java/util/ArrayList
      // 06a1: dup
      // 06a2: invokespecial java/util/ArrayList.<init> ()V
      // 06a5: astore 46
      // 06a7: aload 45
      // 06a9: invokevirtual org/opentravel/ota/_2003/_05/RoomStaysType.getRoomStay ()Ljava/util/List;
      // 06ac: aload 46
      // 06ae: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 06b3: pop
      // 06b4: aload 41
      // 06b6: aload 45
      // 06b8: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRoomStays (Lorg/opentravel/ota/_2003/_05/RoomStaysType;)V
      // 06bb: new org/opentravel/ota/_2003/_05/ServicesType
      // 06be: dup
      // 06bf: invokespecial org/opentravel/ota/_2003/_05/ServicesType.<init> ()V
      // 06c2: astore 47
      // 06c4: new java/util/ArrayList
      // 06c7: dup
      // 06c8: invokespecial java/util/ArrayList.<init> ()V
      // 06cb: astore 48
      // 06cd: aload 47
      // 06cf: invokevirtual org/opentravel/ota/_2003/_05/ServicesType.getService ()Ljava/util/List;
      // 06d2: aload 48
      // 06d4: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 06d9: pop
      // 06da: aload 41
      // 06dc: aload 47
      // 06de: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setServices (Lorg/opentravel/ota/_2003/_05/ServicesType;)V
      // 06e1: new java/util/ArrayList
      // 06e4: dup
      // 06e5: invokespecial java/util/ArrayList.<init> ()V
      // 06e8: astore 49
      // 06ea: aload 41
      // 06ec: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.getBillingInstructionCode ()Ljava/util/List;
      // 06ef: aload 49
      // 06f1: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 06f6: pop
      // 06f7: new org/opentravel/ota/_2003/_05/ResGuestsType
      // 06fa: dup
      // 06fb: invokespecial org/opentravel/ota/_2003/_05/ResGuestsType.<init> ()V
      // 06fe: astore 50
      // 0700: new java/util/ArrayList
      // 0703: dup
      // 0704: invokespecial java/util/ArrayList.<init> ()V
      // 0707: astore 51
      // 0709: aload 50
      // 070b: invokevirtual org/opentravel/ota/_2003/_05/ResGuestsType.getResGuest ()Ljava/util/List;
      // 070e: aload 51
      // 0710: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0715: pop
      // 0716: aload 41
      // 0718: aload 50
      // 071a: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setResGuests (Lorg/opentravel/ota/_2003/_05/ResGuestsType;)V
      // 071d: new org/opentravel/ota/_2003/_05/ResGlobalInfoType
      // 0720: dup
      // 0721: invokespecial org/opentravel/ota/_2003/_05/ResGlobalInfoType.<init> ()V
      // 0724: astore 52
      // 0726: new org/opentravel/ota/_2003/_05/GuestCountType
      // 0729: dup
      // 072a: invokespecial org/opentravel/ota/_2003/_05/GuestCountType.<init> ()V
      // 072d: astore 53
      // 072f: new java/util/ArrayList
      // 0732: dup
      // 0733: invokespecial java/util/ArrayList.<init> ()V
      // 0736: astore 54
      // 0738: aload 53
      // 073a: invokevirtual org/opentravel/ota/_2003/_05/GuestCountType.getGuestCount ()Ljava/util/List;
      // 073d: aload 54
      // 073f: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0744: pop
      // 0745: aload 53
      // 0747: bipush 0
      // 0748: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 074b: invokevirtual org/opentravel/ota/_2003/_05/GuestCountType.setIsPerRoom (Ljava/lang/Boolean;)V
      // 074e: aload 52
      // 0750: aload 53
      // 0752: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setGuestCounts (Lorg/opentravel/ota/_2003/_05/GuestCountType;)V
      // 0755: new org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan
      // 0758: dup
      // 0759: invokespecial org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.<init> ()V
      // 075c: astore 55
      // 075e: new org/opentravel/ota/_2003/_05/TimeInstantType
      // 0761: dup
      // 0762: invokespecial org/opentravel/ota/_2003/_05/TimeInstantType.<init> ()V
      // 0765: astore 56
      // 0767: aload 56
      // 0769: ldc_w "Value1588715408"
      // 076c: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setValue (Ljava/lang/String;)V
      // 076f: aload 56
      // 0771: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0774: ldc_w "-P134847723Y11M4DT3H42M25.499S"
      // 0777: invokevirtual javax/xml/datatype/DatatypeFactory.newDuration (Ljava/lang/String;)Ljavax/xml/datatype/Duration;
      // 077a: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setWindowBefore (Ljavax/xml/datatype/Duration;)V
      // 077d: aload 56
      // 077f: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0782: ldc_w "P15417545Y2M7DT9H36M21.929S"
      // 0785: invokevirtual javax/xml/datatype/DatatypeFactory.newDuration (Ljava/lang/String;)Ljavax/xml/datatype/Duration;
      // 0788: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setWindowAfter (Ljavax/xml/datatype/Duration;)V
      // 078b: aload 56
      // 078d: bipush 0
      // 078e: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0791: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setCrossDateAllowedIndicator (Ljava/lang/Boolean;)V
      // 0794: aload 55
      // 0796: aload 56
      // 0798: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setDateWindowRange (Lorg/opentravel/ota/_2003/_05/TimeInstantType;)V
      // 079b: new org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow
      // 079e: dup
      // 079f: invokespecial org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.<init> ()V
      // 07a2: astore 57
      // 07a4: aload 57
      // 07a6: ldc_w "EarliestDate596069815"
      // 07a9: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.setEarliestDate (Ljava/lang/String;)V
      // 07ac: aload 57
      // 07ae: ldc_w "LatestDate138923859"
      // 07b1: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.setLatestDate (Ljava/lang/String;)V
      // 07b4: getstatic org/opentravel/ota/_2003/_05/DayOfWeekType.WED Lorg/opentravel/ota/_2003/_05/DayOfWeekType;
      // 07b7: astore 58
      // 07b9: aload 57
      // 07bb: aload 58
      // 07bd: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.setDOW (Lorg/opentravel/ota/_2003/_05/DayOfWeekType;)V
      // 07c0: aload 55
      // 07c2: aload 57
      // 07c4: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setStartDateWindow (Lorg/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow;)V
      // 07c7: new org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow
      // 07ca: dup
      // 07cb: invokespecial org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.<init> ()V
      // 07ce: astore 59
      // 07d0: aload 59
      // 07d2: ldc_w "EarliestDate-888630437"
      // 07d5: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.setEarliestDate (Ljava/lang/String;)V
      // 07d8: aload 59
      // 07da: ldc_w "LatestDate2131912811"
      // 07dd: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.setLatestDate (Ljava/lang/String;)V
      // 07e0: getstatic org/opentravel/ota/_2003/_05/DayOfWeekType.SAT Lorg/opentravel/ota/_2003/_05/DayOfWeekType;
      // 07e3: astore 60
      // 07e5: aload 59
      // 07e7: aload 60
      // 07e9: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.setDOW (Lorg/opentravel/ota/_2003/_05/DayOfWeekType;)V
      // 07ec: aload 55
      // 07ee: aload 59
      // 07f0: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setEndDateWindow (Lorg/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow;)V
      // 07f3: aload 55
      // 07f5: ldc_w "Start2004749977"
      // 07f8: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setStart (Ljava/lang/String;)V
      // 07fb: aload 55
      // 07fd: ldc_w "Duration361455976"
      // 0800: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setDuration (Ljava/lang/String;)V
      // 0803: aload 55
      // 0805: ldc_w "End482718988"
      // 0808: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setEnd (Ljava/lang/String;)V
      // 080b: aload 55
      // 080d: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0810: ldc_w "P172102712Y11M16DT16H43M57.576S"
      // 0813: invokevirtual javax/xml/datatype/DatatypeFactory.newDuration (Ljava/lang/String;)Ljavax/xml/datatype/Duration;
      // 0816: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setIncrement (Ljavax/xml/datatype/Duration;)V
      // 0819: aload 52
      // 081b: aload 55
      // 081d: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setTimeSpan (Lorg/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan;)V
      // 0820: new org/opentravel/ota/_2003/_05/ResGuestRPHsType
      // 0823: dup
      // 0824: invokespecial org/opentravel/ota/_2003/_05/ResGuestRPHsType.<init> ()V
      // 0827: astore 61
      // 0829: aload 61
      // 082b: ldc_w "Value1936950524"
      // 082e: invokevirtual org/opentravel/ota/_2003/_05/ResGuestRPHsType.setValue (Ljava/lang/String;)V
      // 0831: aload 52
      // 0833: aload 61
      // 0835: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setResGuestRPHs (Lorg/opentravel/ota/_2003/_05/ResGuestRPHsType;)V
      // 0838: new org/opentravel/ota/_2003/_05/MembershipType
      // 083b: dup
      // 083c: invokespecial org/opentravel/ota/_2003/_05/MembershipType.<init> ()V
      // 083f: astore 62
      // 0841: new java/util/ArrayList
      // 0844: dup
      // 0845: invokespecial java/util/ArrayList.<init> ()V
      // 0848: astore 63
      // 084a: aload 62
      // 084c: invokevirtual org/opentravel/ota/_2003/_05/MembershipType.getMembership ()Ljava/util/List;
      // 084f: aload 63
      // 0851: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0856: pop
      // 0857: aload 52
      // 0859: aload 62
      // 085b: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setMemberships (Lorg/opentravel/ota/_2003/_05/MembershipType;)V
      // 085e: new org/opentravel/ota/_2003/_05/CommentType
      // 0861: dup
      // 0862: invokespecial org/opentravel/ota/_2003/_05/CommentType.<init> ()V
      // 0865: astore 64
      // 0867: new java/util/ArrayList
      // 086a: dup
      // 086b: invokespecial java/util/ArrayList.<init> ()V
      // 086e: astore 65
      // 0870: aload 64
      // 0872: invokevirtual org/opentravel/ota/_2003/_05/CommentType.getComment ()Ljava/util/List;
      // 0875: aload 65
      // 0877: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 087c: pop
      // 087d: aload 52
      // 087f: aload 64
      // 0881: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setComments (Lorg/opentravel/ota/_2003/_05/CommentType;)V
      // 0884: new org/opentravel/ota/_2003/_05/SpecialRequestType
      // 0887: dup
      // 0888: invokespecial org/opentravel/ota/_2003/_05/SpecialRequestType.<init> ()V
      // 088b: astore 66
      // 088d: new java/util/ArrayList
      // 0890: dup
      // 0891: invokespecial java/util/ArrayList.<init> ()V
      // 0894: astore 67
      // 0896: aload 66
      // 0898: invokevirtual org/opentravel/ota/_2003/_05/SpecialRequestType.getSpecialRequest ()Ljava/util/List;
      // 089b: aload 67
      // 089d: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 08a2: pop
      // 08a3: aload 52
      // 08a5: aload 66
      // 08a7: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setSpecialRequests (Lorg/opentravel/ota/_2003/_05/SpecialRequestType;)V
      // 08aa: new org/opentravel/ota/_2003/_05/GuaranteeType
      // 08ad: dup
      // 08ae: invokespecial org/opentravel/ota/_2003/_05/GuaranteeType.<init> ()V
      // 08b1: astore 68
      // 08b3: new org/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted
      // 08b6: dup
      // 08b7: invokespecial org/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted.<init> ()V
      // 08ba: astore 69
      // 08bc: new java/util/ArrayList
      // 08bf: dup
      // 08c0: invokespecial java/util/ArrayList.<init> ()V
      // 08c3: astore 70
      // 08c5: aload 69
      // 08c7: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted.getGuaranteeAccepted ()Ljava/util/List;
      // 08ca: aload 70
      // 08cc: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 08d1: pop
      // 08d2: aload 68
      // 08d4: aload 69
      // 08d6: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setGuaranteesAccepted (Lorg/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted;)V
      // 08d9: new org/opentravel/ota/_2003/_05/GuaranteeType$Deadline
      // 08dc: dup
      // 08dd: invokespecial org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.<init> ()V
      // 08e0: astore 71
      // 08e2: aload 71
      // 08e4: ldc_w "AbsoluteDeadline1483516512"
      // 08e7: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setAbsoluteDeadline (Ljava/lang/String;)V
      // 08ea: getstatic org/opentravel/ota/_2003/_05/TimeUnitType.YEAR Lorg/opentravel/ota/_2003/_05/TimeUnitType;
      // 08ed: astore 72
      // 08ef: aload 71
      // 08f1: aload 72
      // 08f3: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setOffsetTimeUnit (Lorg/opentravel/ota/_2003/_05/TimeUnitType;)V
      // 08f6: aload 71
      // 08f8: ldc_w 1146180817
      // 08fb: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 08fe: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setOffsetUnitMultiplier (Ljava/lang/Integer;)V
      // 0901: aload 71
      // 0903: ldc_w "OffsetDropTime-2108745568"
      // 0906: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setOffsetDropTime (Ljava/lang/String;)V
      // 0909: aload 68
      // 090b: aload 71
      // 090d: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setDeadline (Lorg/opentravel/ota/_2003/_05/GuaranteeType$Deadline;)V
      // 0910: new org/opentravel/ota/_2003/_05/CommentType
      // 0913: dup
      // 0914: invokespecial org/opentravel/ota/_2003/_05/CommentType.<init> ()V
      // 0917: astore 73
      // 0919: new java/util/ArrayList
      // 091c: dup
      // 091d: invokespecial java/util/ArrayList.<init> ()V
      // 0920: astore 74
      // 0922: aload 73
      // 0924: invokevirtual org/opentravel/ota/_2003/_05/CommentType.getComment ()Ljava/util/List;
      // 0927: aload 74
      // 0929: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 092e: pop
      // 092f: aload 68
      // 0931: aload 73
      // 0933: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setComments (Lorg/opentravel/ota/_2003/_05/CommentType;)V
      // 0936: new java/util/ArrayList
      // 0939: dup
      // 093a: invokespecial java/util/ArrayList.<init> ()V
      // 093d: astore 75
      // 093f: aload 68
      // 0941: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.getGuaranteeDescription ()Ljava/util/List;
      // 0944: aload 75
      // 0946: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 094b: pop
      // 094c: aload 68
      // 094e: ldc_w "RetributionType-2101332801"
      // 0951: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setRetributionType (Ljava/lang/String;)V
      // 0954: aload 68
      // 0956: ldc_w "GuaranteeCode-1004448766"
      // 0959: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setGuaranteeCode (Ljava/lang/String;)V
      // 095c: aload 68
      // 095e: ldc_w "GuaranteeType-1106342999"
      // 0961: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setGuaranteeType (Ljava/lang/String;)V
      // 0964: aload 68
      // 0966: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0969: ldc_w "2020-03-24T09:54:33.642+08:00"
      // 096c: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 096f: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setHoldTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0972: aload 52
      // 0974: aload 68
      // 0976: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setGuarantee (Lorg/opentravel/ota/_2003/_05/GuaranteeType;)V
      // 0979: new org/opentravel/ota/_2003/_05/RequiredPaymentsType
      // 097c: dup
      // 097d: invokespecial org/opentravel/ota/_2003/_05/RequiredPaymentsType.<init> ()V
      // 0980: astore 76
      // 0982: new java/util/ArrayList
      // 0985: dup
      // 0986: invokespecial java/util/ArrayList.<init> ()V
      // 0989: astore 77
      // 098b: aload 76
      // 098d: invokevirtual org/opentravel/ota/_2003/_05/RequiredPaymentsType.getGuaranteePayment ()Ljava/util/List;
      // 0990: aload 77
      // 0992: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0997: pop
      // 0998: aload 52
      // 099a: aload 76
      // 099c: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setDepositPayments (Lorg/opentravel/ota/_2003/_05/RequiredPaymentsType;)V
      // 099f: new org/opentravel/ota/_2003/_05/CancelPenaltiesType
      // 09a2: dup
      // 09a3: invokespecial org/opentravel/ota/_2003/_05/CancelPenaltiesType.<init> ()V
      // 09a6: astore 78
      // 09a8: new java/util/ArrayList
      // 09ab: dup
      // 09ac: invokespecial java/util/ArrayList.<init> ()V
      // 09af: astore 79
      // 09b1: aload 78
      // 09b3: invokevirtual org/opentravel/ota/_2003/_05/CancelPenaltiesType.getCancelPenalty ()Ljava/util/List;
      // 09b6: aload 79
      // 09b8: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 09bd: pop
      // 09be: aload 78
      // 09c0: bipush 1
      // 09c1: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 09c4: invokevirtual org/opentravel/ota/_2003/_05/CancelPenaltiesType.setCancelPolicyIndicator (Ljava/lang/Boolean;)V
      // 09c7: aload 52
      // 09c9: aload 78
      // 09cb: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setCancelPenalties (Lorg/opentravel/ota/_2003/_05/CancelPenaltiesType;)V
      // 09ce: new org/opentravel/ota/_2003/_05/FeesType
      // 09d1: dup
      // 09d2: invokespecial org/opentravel/ota/_2003/_05/FeesType.<init> ()V
      // 09d5: astore 80
      // 09d7: new java/util/ArrayList
      // 09da: dup
      // 09db: invokespecial java/util/ArrayList.<init> ()V
      // 09de: astore 81
      // 09e0: aload 80
      // 09e2: invokevirtual org/opentravel/ota/_2003/_05/FeesType.getFee ()Ljava/util/List;
      // 09e5: aload 81
      // 09e7: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 09ec: pop
      // 09ed: aload 52
      // 09ef: aload 80
      // 09f1: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setFees (Lorg/opentravel/ota/_2003/_05/FeesType;)V
      // 09f4: new org/opentravel/ota/_2003/_05/TotalType
      // 09f7: dup
      // 09f8: invokespecial org/opentravel/ota/_2003/_05/TotalType.<init> ()V
      // 09fb: astore 82
      // 09fd: new org/opentravel/ota/_2003/_05/TaxesType
      // 0a00: dup
      // 0a01: invokespecial org/opentravel/ota/_2003/_05/TaxesType.<init> ()V
      // 0a04: astore 83
      // 0a06: new java/util/ArrayList
      // 0a09: dup
      // 0a0a: invokespecial java/util/ArrayList.<init> ()V
      // 0a0d: astore 84
      // 0a0f: aload 83
      // 0a11: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.getTax ()Ljava/util/List;
      // 0a14: aload 84
      // 0a16: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0a1b: pop
      // 0a1c: aload 83
      // 0a1e: new java/math/BigDecimal
      // 0a21: dup
      // 0a22: ldc_w "-3993015132928973860.7314696499797257571"
      // 0a25: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 0a28: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.setAmount (Ljava/math/BigDecimal;)V
      // 0a2b: aload 83
      // 0a2d: ldc_w "CurrencyCode-1708970489"
      // 0a30: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.setCurrencyCode (Ljava/lang/String;)V
      // 0a33: aload 83
      // 0a35: new java/math/BigInteger
      // 0a38: dup
      // 0a39: ldc_w "52730690483381585076403911221965005354"
      // 0a3c: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 0a3f: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.setDecimalPlaces (Ljava/math/BigInteger;)V
      // 0a42: aload 82
      // 0a44: aload 83
      // 0a46: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setTaxes (Lorg/opentravel/ota/_2003/_05/TaxesType;)V
      // 0a49: aload 82
      // 0a4b: new java/math/BigDecimal
      // 0a4e: dup
      // 0a4f: ldc_w "-8375123657245669519.5491790787450629197"
      // 0a52: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 0a55: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAmountBeforeTax (Ljava/math/BigDecimal;)V
      // 0a58: aload 82
      // 0a5a: new java/math/BigDecimal
      // 0a5d: dup
      // 0a5e: ldc_w "4452982275543422373.663429735311139526"
      // 0a61: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 0a64: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAmountAfterTax (Ljava/math/BigDecimal;)V
      // 0a67: aload 82
      // 0a69: bipush 0
      // 0a6a: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0a6d: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAdditionalFeesExcludedIndicator (Ljava/lang/Boolean;)V
      // 0a70: aload 82
      // 0a72: ldc_w "Type767306150"
      // 0a75: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setType (Ljava/lang/String;)V
      // 0a78: aload 82
      // 0a7a: bipush 0
      // 0a7b: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0a7e: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setServiceOverrideIndicator (Ljava/lang/Boolean;)V
      // 0a81: aload 82
      // 0a83: bipush 1
      // 0a84: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0a87: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setRateOverrideIndicator (Ljava/lang/Boolean;)V
      // 0a8a: aload 82
      // 0a8c: new java/math/BigDecimal
      // 0a8f: dup
      // 0a90: ldc_w "-6431340273164521376.980232333372322416"
      // 0a93: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 0a96: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAmountIncludingMarkup (Ljava/math/BigDecimal;)V
      // 0a99: aload 82
      // 0a9b: ldc_w "CurrencyCode1161238987"
      // 0a9e: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setCurrencyCode (Ljava/lang/String;)V
      // 0aa1: aload 82
      // 0aa3: new java/math/BigInteger
      // 0aa6: dup
      // 0aa7: ldc_w "6061385212706765098343626313045122081"
      // 0aaa: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 0aad: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setDecimalPlaces (Ljava/math/BigInteger;)V
      // 0ab0: aload 52
      // 0ab2: aload 82
      // 0ab4: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setTotal (Lorg/opentravel/ota/_2003/_05/TotalType;)V
      // 0ab7: new org/opentravel/ota/_2003/_05/HotelReservationIDsType
      // 0aba: dup
      // 0abb: invokespecial org/opentravel/ota/_2003/_05/HotelReservationIDsType.<init> ()V
      // 0abe: astore 85
      // 0ac0: new java/util/ArrayList
      // 0ac3: dup
      // 0ac4: invokespecial java/util/ArrayList.<init> ()V
      // 0ac7: astore 86
      // 0ac9: aload 85
      // 0acb: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationIDsType.getHotelReservationID ()Ljava/util/List;
      // 0ace: aload 86
      // 0ad0: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0ad5: pop
      // 0ad6: aload 52
      // 0ad8: aload 85
      // 0ada: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setHotelReservationIDs (Lorg/opentravel/ota/_2003/_05/HotelReservationIDsType;)V
      // 0add: new org/opentravel/ota/_2003/_05/RoutingHopType
      // 0ae0: dup
      // 0ae1: invokespecial org/opentravel/ota/_2003/_05/RoutingHopType.<init> ()V
      // 0ae4: astore 87
      // 0ae6: new java/util/ArrayList
      // 0ae9: dup
      // 0aea: invokespecial java/util/ArrayList.<init> ()V
      // 0aed: astore 88
      // 0aef: aload 87
      // 0af1: invokevirtual org/opentravel/ota/_2003/_05/RoutingHopType.getRoutingHop ()Ljava/util/List;
      // 0af4: aload 88
      // 0af6: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0afb: pop
      // 0afc: aload 52
      // 0afe: aload 87
      // 0b00: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setRoutingHops (Lorg/opentravel/ota/_2003/_05/RoutingHopType;)V
      // 0b03: new org/opentravel/ota/_2003/_05/ProfilesType
      // 0b06: dup
      // 0b07: invokespecial org/opentravel/ota/_2003/_05/ProfilesType.<init> ()V
      // 0b0a: astore 89
      // 0b0c: new java/util/ArrayList
      // 0b0f: dup
      // 0b10: invokespecial java/util/ArrayList.<init> ()V
      // 0b13: astore 90
      // 0b15: aload 89
      // 0b17: invokevirtual org/opentravel/ota/_2003/_05/ProfilesType.getProfileInfo ()Ljava/util/List;
      // 0b1a: aload 90
      // 0b1c: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0b21: pop
      // 0b22: aload 52
      // 0b24: aload 89
      // 0b26: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setProfiles (Lorg/opentravel/ota/_2003/_05/ProfilesType;)V
      // 0b29: new org/opentravel/ota/_2003/_05/BookingRulesType
      // 0b2c: dup
      // 0b2d: invokespecial org/opentravel/ota/_2003/_05/BookingRulesType.<init> ()V
      // 0b30: astore 91
      // 0b32: new java/util/ArrayList
      // 0b35: dup
      // 0b36: invokespecial java/util/ArrayList.<init> ()V
      // 0b39: astore 92
      // 0b3b: aload 91
      // 0b3d: invokevirtual org/opentravel/ota/_2003/_05/BookingRulesType.getBookingRule ()Ljava/util/List;
      // 0b40: aload 92
      // 0b42: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0b47: pop
      // 0b48: aload 52
      // 0b4a: aload 91
      // 0b4c: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setBookingRules (Lorg/opentravel/ota/_2003/_05/BookingRulesType;)V
      // 0b4f: aload 41
      // 0b51: aload 52
      // 0b53: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setResGlobalInfo (Lorg/opentravel/ota/_2003/_05/ResGlobalInfoType;)V
      // 0b56: new org/opentravel/ota/_2003/_05/WrittenConfInstType
      // 0b59: dup
      // 0b5a: invokespecial org/opentravel/ota/_2003/_05/WrittenConfInstType.<init> ()V
      // 0b5d: astore 93
      // 0b5f: new org/opentravel/ota/_2003/_05/ParagraphType
      // 0b62: dup
      // 0b63: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 0b66: astore 94
      // 0b68: new java/util/ArrayList
      // 0b6b: dup
      // 0b6c: invokespecial java/util/ArrayList.<init> ()V
      // 0b6f: astore 95
      // 0b71: aload 94
      // 0b73: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 0b76: aload 95
      // 0b78: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0b7d: pop
      // 0b7e: aload 94
      // 0b80: ldc_w "Name1534612827"
      // 0b83: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 0b86: aload 94
      // 0b88: new java/math/BigInteger
      // 0b8b: dup
      // 0b8c: ldc_w "-64018011034130101818275229536908044928"
      // 0b8f: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 0b92: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 0b95: aload 94
      // 0b97: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0b9a: ldc_w "2020-03-24T09:54:33.644+08:00"
      // 0b9d: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 0ba0: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0ba3: aload 94
      // 0ba5: ldc_w "CreatorID-340354138"
      // 0ba8: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 0bab: aload 94
      // 0bad: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0bb0: ldc_w "2020-03-24T09:54:33.644+08:00"
      // 0bb3: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 0bb6: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0bb9: aload 94
      // 0bbb: ldc_w "LastModifierID-58883263"
      // 0bbe: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 0bc1: aload 94
      // 0bc3: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0bc6: ldc_w "2020-03-24T09:54:33.646+08:00"
      // 0bc9: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 0bcc: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0bcf: aload 94
      // 0bd1: ldc_w "Language-932231110"
      // 0bd4: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 0bd7: aload 93
      // 0bd9: aload 94
      // 0bdb: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setSupplementalData (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 0bde: new org/opentravel/ota/_2003/_05/EmailType
      // 0be1: dup
      // 0be2: invokespecial org/opentravel/ota/_2003/_05/EmailType.<init> ()V
      // 0be5: astore 96
      // 0be7: aload 96
      // 0be9: ldc_w "Value-276993206"
      // 0bec: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setValue (Ljava/lang/String;)V
      // 0bef: aload 96
      // 0bf1: ldc_w "EmailType1017903894"
      // 0bf4: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setEmailType (Ljava/lang/String;)V
      // 0bf7: aload 96
      // 0bf9: ldc_w "RPH-1438760353"
      // 0bfc: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRPH (Ljava/lang/String;)V
      // 0bff: aload 96
      // 0c01: ldc_w "Remark-1116810973"
      // 0c04: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRemark (Ljava/lang/String;)V
      // 0c07: aload 96
      // 0c09: bipush 0
      // 0c0a: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0c0d: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setDefaultInd (Ljava/lang/Boolean;)V
      // 0c10: aload 96
      // 0c12: ldc_w "ShareSynchInd-1966918961"
      // 0c15: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareSynchInd (Ljava/lang/String;)V
      // 0c18: aload 96
      // 0c1a: ldc_w "ShareMarketInd628928916"
      // 0c1d: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareMarketInd (Ljava/lang/String;)V
      // 0c20: aload 93
      // 0c22: aload 96
      // 0c24: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setEmail (Lorg/opentravel/ota/_2003/_05/EmailType;)V
      // 0c27: aload 93
      // 0c29: ldc_w "LanguageID1373977282"
      // 0c2c: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setLanguageID (Ljava/lang/String;)V
      // 0c2f: aload 93
      // 0c31: ldc_w "AddresseeName-1405091693"
      // 0c34: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddresseeName (Ljava/lang/String;)V
      // 0c37: aload 93
      // 0c39: ldc_w "Address1424609119"
      // 0c3c: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddress (Ljava/lang/String;)V
      // 0c3f: aload 93
      // 0c41: ldc_w "Telephone1599930612"
      // 0c44: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setTelephone (Ljava/lang/String;)V
      // 0c47: aload 93
      // 0c49: bipush 0
      // 0c4a: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0c4d: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setConfirmInd (Ljava/lang/Boolean;)V
      // 0c50: aload 41
      // 0c52: aload 93
      // 0c54: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setWrittenConfInst (Lorg/opentravel/ota/_2003/_05/WrittenConfInstType;)V
      // 0c57: new org/opentravel/ota/_2003/_05/HotelReservationType$Queue
      // 0c5a: dup
      // 0c5b: invokespecial org/opentravel/ota/_2003/_05/HotelReservationType$Queue.<init> ()V
      // 0c5e: astore 97
      // 0c60: aload 97
      // 0c62: ldc_w "PseudoCityCode411412973"
      // 0c65: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setPseudoCityCode (Ljava/lang/String;)V
      // 0c68: aload 97
      // 0c6a: ldc_w "QueueNumber852444443"
      // 0c6d: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setQueueNumber (Ljava/lang/String;)V
      // 0c70: aload 97
      // 0c72: ldc_w "QueueCategory-535679621"
      // 0c75: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setQueueCategory (Ljava/lang/String;)V
      // 0c78: aload 97
      // 0c7a: ldc_w "SystemCode-1039441254"
      // 0c7d: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setSystemCode (Ljava/lang/String;)V
      // 0c80: aload 97
      // 0c82: ldc_w "QueueID-1478226481"
      // 0c85: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setQueueID (Ljava/lang/String;)V
      // 0c88: aload 41
      // 0c8a: aload 97
      // 0c8c: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setQueue (Lorg/opentravel/ota/_2003/_05/HotelReservationType$Queue;)V
      // 0c8f: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 0c92: dup
      // 0c93: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 0c96: astore 98
      // 0c98: new java/util/ArrayList
      // 0c9b: dup
      // 0c9c: invokespecial java/util/ArrayList.<init> ()V
      // 0c9f: astore 99
      // 0ca1: aload 98
      // 0ca3: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 0ca6: aload 99
      // 0ca8: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0cad: pop
      // 0cae: aload 41
      // 0cb0: aload 98
      // 0cb2: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 0cb5: aload 41
      // 0cb7: bipush 0
      // 0cb8: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0cbb: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRoomStayReservation (Ljava/lang/Boolean;)V
      // 0cbe: aload 41
      // 0cc0: ldc_w "ResStatus447503972"
      // 0cc3: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setResStatus (Ljava/lang/String;)V
      // 0cc6: aload 41
      // 0cc8: bipush 0
      // 0cc9: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0ccc: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setForcedSellIndicator (Ljava/lang/Boolean;)V
      // 0ccf: aload 41
      // 0cd1: bipush 0
      // 0cd2: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0cd5: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setServiceOverrideIndicator (Ljava/lang/Boolean;)V
      // 0cd8: aload 41
      // 0cda: bipush 0
      // 0cdb: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0cde: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRateOverrideIndicator (Ljava/lang/Boolean;)V
      // 0ce1: aload 41
      // 0ce3: bipush 1
      // 0ce4: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0ce7: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setWalkInIndicator (Ljava/lang/Boolean;)V
      // 0cea: aload 41
      // 0cec: bipush 0
      // 0ced: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0cf0: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRoomNumberLockedIndicator (Ljava/lang/Boolean;)V
      // 0cf3: aload 41
      // 0cf5: ldc_w "OriginalDeliveryMethodCode204326991"
      // 0cf8: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setOriginalDeliveryMethodCode (Ljava/lang/String;)V
      // 0cfb: aload 41
      // 0cfd: bipush 1
      // 0cfe: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0d01: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setPassiveIndicator (Ljava/lang/Boolean;)V
      // 0d04: aload 41
      // 0d06: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0d09: ldc_w "2020-03-24T09:54:33.647+08:00"
      // 0d0c: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 0d0f: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0d12: aload 41
      // 0d14: ldc_w "CreatorID-306755023"
      // 0d17: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setCreatorID (Ljava/lang/String;)V
      // 0d1a: aload 41
      // 0d1c: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0d1f: ldc_w "2020-03-24T09:54:33.648+08:00"
      // 0d22: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 0d25: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0d28: aload 41
      // 0d2a: ldc_w "LastModifierID-1344023102"
      // 0d2d: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setLastModifierID (Ljava/lang/String;)V
      // 0d30: aload 41
      // 0d32: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0d35: ldc_w "2020-03-24T09:54:33.648+08:00"
      // 0d38: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 0d3b: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0d3e: new org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms
      // 0d41: dup
      // 0d42: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms.<init> ()V
      // 0d45: astore 100
      // 0d47: new java/util/ArrayList
      // 0d4a: dup
      // 0d4b: invokespecial java/util/ArrayList.<init> ()V
      // 0d4e: astore 101
      // 0d50: aload 100
      // 0d52: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms.getRebateProgram ()Ljava/util/List;
      // 0d55: aload 101
      // 0d57: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0d5c: pop
      // 0d5d: aload 41
      // 0d5f: aload 100
      // 0d61: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRebatePrograms (Lorg/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms;)V
      // 0d64: aload 40
      // 0d66: aload 41
      // 0d68: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 0d6d: pop
      // 0d6e: aload 39
      // 0d70: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.getHotelReservation ()Ljava/util/List;
      // 0d73: aload 40
      // 0d75: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0d7a: pop
      // 0d7b: new org/opentravel/ota/_2003/_05/RoutingHopType
      // 0d7e: dup
      // 0d7f: invokespecial org/opentravel/ota/_2003/_05/RoutingHopType.<init> ()V
      // 0d82: astore 102
      // 0d84: new java/util/ArrayList
      // 0d87: dup
      // 0d88: invokespecial java/util/ArrayList.<init> ()V
      // 0d8b: astore 103
      // 0d8d: aload 102
      // 0d8f: invokevirtual org/opentravel/ota/_2003/_05/RoutingHopType.getRoutingHop ()Ljava/util/List;
      // 0d92: aload 103
      // 0d94: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0d99: pop
      // 0d9a: aload 39
      // 0d9c: aload 102
      // 0d9e: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setRoutingHops (Lorg/opentravel/ota/_2003/_05/RoutingHopType;)V
      // 0da1: new org/opentravel/ota/_2003/_05/WrittenConfInstType
      // 0da4: dup
      // 0da5: invokespecial org/opentravel/ota/_2003/_05/WrittenConfInstType.<init> ()V
      // 0da8: astore 104
      // 0daa: new org/opentravel/ota/_2003/_05/ParagraphType
      // 0dad: dup
      // 0dae: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 0db1: astore 105
      // 0db3: new java/util/ArrayList
      // 0db6: dup
      // 0db7: invokespecial java/util/ArrayList.<init> ()V
      // 0dba: astore 106
      // 0dbc: aload 105
      // 0dbe: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 0dc1: aload 106
      // 0dc3: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0dc8: pop
      // 0dc9: aload 105
      // 0dcb: ldc_w "Name1288389700"
      // 0dce: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 0dd1: aload 105
      // 0dd3: new java/math/BigInteger
      // 0dd6: dup
      // 0dd7: ldc_w "-40806423995688078891479983679236277397"
      // 0dda: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 0ddd: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 0de0: aload 105
      // 0de2: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0de5: ldc_w "2020-03-24T09:54:33.649+08:00"
      // 0de8: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 0deb: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0dee: aload 105
      // 0df0: ldc_w "CreatorID891700703"
      // 0df3: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 0df6: aload 105
      // 0df8: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0dfb: ldc_w "2020-03-24T09:54:33.650+08:00"
      // 0dfe: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 0e01: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0e04: aload 105
      // 0e06: ldc_w "LastModifierID1207502892"
      // 0e09: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 0e0c: aload 105
      // 0e0e: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 0e11: ldc_w "2020-03-24T09:54:33.650+08:00"
      // 0e14: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 0e17: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 0e1a: aload 105
      // 0e1c: ldc_w "Language-1508006249"
      // 0e1f: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 0e22: aload 104
      // 0e24: aload 105
      // 0e26: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setSupplementalData (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 0e29: new org/opentravel/ota/_2003/_05/EmailType
      // 0e2c: dup
      // 0e2d: invokespecial org/opentravel/ota/_2003/_05/EmailType.<init> ()V
      // 0e30: astore 107
      // 0e32: aload 107
      // 0e34: ldc_w "Value-1851822724"
      // 0e37: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setValue (Ljava/lang/String;)V
      // 0e3a: aload 107
      // 0e3c: ldc_w "EmailType605059631"
      // 0e3f: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setEmailType (Ljava/lang/String;)V
      // 0e42: aload 107
      // 0e44: ldc_w "RPH-1250421693"
      // 0e47: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRPH (Ljava/lang/String;)V
      // 0e4a: aload 107
      // 0e4c: ldc_w "Remark986991366"
      // 0e4f: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRemark (Ljava/lang/String;)V
      // 0e52: aload 107
      // 0e54: bipush 0
      // 0e55: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0e58: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setDefaultInd (Ljava/lang/Boolean;)V
      // 0e5b: aload 107
      // 0e5d: ldc_w "ShareSynchInd-1639837957"
      // 0e60: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareSynchInd (Ljava/lang/String;)V
      // 0e63: aload 107
      // 0e65: ldc_w "ShareMarketInd1433134847"
      // 0e68: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareMarketInd (Ljava/lang/String;)V
      // 0e6b: aload 104
      // 0e6d: aload 107
      // 0e6f: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setEmail (Lorg/opentravel/ota/_2003/_05/EmailType;)V
      // 0e72: aload 104
      // 0e74: ldc_w "LanguageID-514704008"
      // 0e77: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setLanguageID (Ljava/lang/String;)V
      // 0e7a: aload 104
      // 0e7c: ldc_w "AddresseeName1627936024"
      // 0e7f: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddresseeName (Ljava/lang/String;)V
      // 0e82: aload 104
      // 0e84: ldc_w "Address-826648356"
      // 0e87: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddress (Ljava/lang/String;)V
      // 0e8a: aload 104
      // 0e8c: ldc_w "Telephone626869625"
      // 0e8f: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setTelephone (Ljava/lang/String;)V
      // 0e92: aload 104
      // 0e94: bipush 1
      // 0e95: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0e98: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setConfirmInd (Ljava/lang/Boolean;)V
      // 0e9b: aload 39
      // 0e9d: aload 104
      // 0e9f: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setWrittenConfInst (Lorg/opentravel/ota/_2003/_05/WrittenConfInstType;)V
      // 0ea2: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 0ea5: dup
      // 0ea6: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 0ea9: astore 108
      // 0eab: new java/util/ArrayList
      // 0eae: dup
      // 0eaf: invokespecial java/util/ArrayList.<init> ()V
      // 0eb2: astore 109
      // 0eb4: aload 108
      // 0eb6: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 0eb9: aload 109
      // 0ebb: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0ec0: pop
      // 0ec1: aload 39
      // 0ec3: aload 108
      // 0ec5: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 0ec8: aload 4
      // 0eca: aload 39
      // 0ecc: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setHotelReservations (Lorg/opentravel/ota/_2003/_05/HotelReservationsType;)V
      // 0ecf: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 0ed2: dup
      // 0ed3: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 0ed6: astore 110
      // 0ed8: new java/util/ArrayList
      // 0edb: dup
      // 0edc: invokespecial java/util/ArrayList.<init> ()V
      // 0edf: astore 111
      // 0ee1: aconst_null
      // 0ee2: astore 112
      // 0ee4: aload 111
      // 0ee6: aload 112
      // 0ee8: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 0eed: pop
      // 0eee: aload 110
      // 0ef0: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 0ef3: aload 111
      // 0ef5: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 0efa: pop
      // 0efb: aload 4
      // 0efd: aload 110
      // 0eff: invokevirtual org/htng/_2011b/HTNGHotelCheckInNotifRQ.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 0f02: aload 3
      // 0f03: aload 4
      // 0f05: invokeinterface org/htng/_2011b/StayNotification.checkedIn (Lorg/htng/_2011b/HTNGHotelCheckInNotifRQ;)Lorg/htng/_2011b/HTNGResponseBaseType; 2
      // 0f0a: astore 113
      // 0f0c: getstatic java/lang/System.out Ljava/io/PrintStream;
      // 0f0f: new java/lang/StringBuilder
      // 0f12: dup
      // 0f13: invokespecial java/lang/StringBuilder.<init> ()V
      // 0f16: ldc_w "checkedIn.result="
      // 0f19: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0f1c: aload 113
      // 0f1e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 0f21: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0f24: invokevirtual java/io/PrintStream.println (Ljava/lang/String;)V
      // 0f27: getstatic java/lang/System.out Ljava/io/PrintStream;
      // 0f2a: ldc_w "Invoking checkedOut..."
      // 0f2d: invokevirtual java/io/PrintStream.println (Ljava/lang/String;)V
      // 0f30: new org/htng/_2011b/HTNGHotelCheckOutNotifRQ
      // 0f33: dup
      // 0f34: invokespecial org/htng/_2011b/HTNGHotelCheckOutNotifRQ.<init> ()V
      // 0f37: astore 4
      // 0f39: new org/opentravel/ota/_2003/_05/POSType
      // 0f3c: dup
      // 0f3d: invokespecial org/opentravel/ota/_2003/_05/POSType.<init> ()V
      // 0f40: astore 5
      // 0f42: new java/util/ArrayList
      // 0f45: dup
      // 0f46: invokespecial java/util/ArrayList.<init> ()V
      // 0f49: astore 6
      // 0f4b: new org/opentravel/ota/_2003/_05/SourceType
      // 0f4e: dup
      // 0f4f: invokespecial org/opentravel/ota/_2003/_05/SourceType.<init> ()V
      // 0f52: astore 7
      // 0f54: new org/opentravel/ota/_2003/_05/SourceType$RequestorID
      // 0f57: dup
      // 0f58: invokespecial org/opentravel/ota/_2003/_05/SourceType$RequestorID.<init> ()V
      // 0f5b: astore 8
      // 0f5d: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 0f60: dup
      // 0f61: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 0f64: astore 9
      // 0f66: aload 9
      // 0f68: ldc_w "Value1498181503"
      // 0f6b: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 0f6e: aload 9
      // 0f70: ldc_w "Division1253909617"
      // 0f73: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 0f76: aload 9
      // 0f78: ldc_w "Department-1295497563"
      // 0f7b: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 0f7e: aload 9
      // 0f80: ldc_w "CompanyShortName-1829369274"
      // 0f83: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 0f86: aload 9
      // 0f88: ldc_w "TravelSector557919627"
      // 0f8b: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 0f8e: aload 9
      // 0f90: ldc_w "Code1143538891"
      // 0f93: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 0f96: aload 9
      // 0f98: ldc_w "CodeContext276401128"
      // 0f9b: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 0f9e: aload 8
      // 0fa0: aload 9
      // 0fa2: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 0fa5: aload 8
      // 0fa7: ldc_w "URL-1530590885"
      // 0faa: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setURL (Ljava/lang/String;)V
      // 0fad: aload 8
      // 0faf: ldc_w "Type-548783699"
      // 0fb2: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setType (Ljava/lang/String;)V
      // 0fb5: aload 8
      // 0fb7: ldc_w "Instance-579230708"
      // 0fba: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setInstance (Ljava/lang/String;)V
      // 0fbd: aload 8
      // 0fbf: ldc_w "IDContext1059974892"
      // 0fc2: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setIDContext (Ljava/lang/String;)V
      // 0fc5: aload 8
      // 0fc7: ldc_w "ID-1455609302"
      // 0fca: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setID (Ljava/lang/String;)V
      // 0fcd: aload 8
      // 0fcf: ldc_w "MessagePassword2122123070"
      // 0fd2: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setMessagePassword (Ljava/lang/String;)V
      // 0fd5: aload 7
      // 0fd7: aload 8
      // 0fd9: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setRequestorID (Lorg/opentravel/ota/_2003/_05/SourceType$RequestorID;)V
      // 0fdc: new org/opentravel/ota/_2003/_05/SourceType$Position
      // 0fdf: dup
      // 0fe0: invokespecial org/opentravel/ota/_2003/_05/SourceType$Position.<init> ()V
      // 0fe3: astore 10
      // 0fe5: aload 10
      // 0fe7: ldc_w "Latitude-1307837878"
      // 0fea: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setLatitude (Ljava/lang/String;)V
      // 0fed: aload 10
      // 0fef: ldc_w "Longitude-836847432"
      // 0ff2: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setLongitude (Ljava/lang/String;)V
      // 0ff5: aload 10
      // 0ff7: ldc_w "Altitude1349950824"
      // 0ffa: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setAltitude (Ljava/lang/String;)V
      // 0ffd: aload 10
      // 0fff: ldc_w "AltitudeUnitOfMeasureCode-1177931341"
      // 1002: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setAltitudeUnitOfMeasureCode (Ljava/lang/String;)V
      // 1005: aload 10
      // 1007: ldc_w "PositionAccuracy-1640722760"
      // 100a: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setPositionAccuracy (Ljava/lang/String;)V
      // 100d: aload 7
      // 100f: aload 10
      // 1011: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setPosition (Lorg/opentravel/ota/_2003/_05/SourceType$Position;)V
      // 1014: new org/opentravel/ota/_2003/_05/SourceType$BookingChannel
      // 1017: dup
      // 1018: invokespecial org/opentravel/ota/_2003/_05/SourceType$BookingChannel.<init> ()V
      // 101b: astore 11
      // 101d: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 1020: dup
      // 1021: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 1024: astore 12
      // 1026: aload 12
      // 1028: ldc_w "Value1345435130"
      // 102b: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 102e: aload 12
      // 1030: ldc_w "Division-1165865970"
      // 1033: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 1036: aload 12
      // 1038: ldc_w "Department-1124001009"
      // 103b: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 103e: aload 12
      // 1040: ldc_w "CompanyShortName957753095"
      // 1043: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 1046: aload 12
      // 1048: ldc_w "TravelSector-2044347225"
      // 104b: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 104e: aload 12
      // 1050: ldc_w "Code-388911267"
      // 1053: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 1056: aload 12
      // 1058: ldc_w "CodeContext1439333784"
      // 105b: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 105e: aload 11
      // 1060: aload 12
      // 1062: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 1065: aload 11
      // 1067: ldc_w "Type942246153"
      // 106a: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setType (Ljava/lang/String;)V
      // 106d: aload 11
      // 106f: bipush 0
      // 1070: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1073: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setPrimary (Ljava/lang/Boolean;)V
      // 1076: aload 7
      // 1078: aload 11
      // 107a: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setBookingChannel (Lorg/opentravel/ota/_2003/_05/SourceType$BookingChannel;)V
      // 107d: aload 7
      // 107f: ldc_w "AgentSine-873093854"
      // 1082: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAgentSine (Ljava/lang/String;)V
      // 1085: aload 7
      // 1087: ldc_w "PseudoCityCode-414325555"
      // 108a: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setPseudoCityCode (Ljava/lang/String;)V
      // 108d: aload 7
      // 108f: ldc_w "ISOCountry-263376232"
      // 1092: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setISOCountry (Ljava/lang/String;)V
      // 1095: aload 7
      // 1097: ldc_w "ISOCurrency-1428978041"
      // 109a: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setISOCurrency (Ljava/lang/String;)V
      // 109d: aload 7
      // 109f: ldc_w "AgentDutyCode364889147"
      // 10a2: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAgentDutyCode (Ljava/lang/String;)V
      // 10a5: aload 7
      // 10a7: ldc_w "AirlineVendorID906051990"
      // 10aa: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAirlineVendorID (Ljava/lang/String;)V
      // 10ad: aload 7
      // 10af: ldc_w "AirportCode1413360565"
      // 10b2: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAirportCode (Ljava/lang/String;)V
      // 10b5: aload 7
      // 10b7: ldc_w "FirstDepartPoint785405850"
      // 10ba: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setFirstDepartPoint (Ljava/lang/String;)V
      // 10bd: aload 7
      // 10bf: ldc_w "ERSPUserID1854272857"
      // 10c2: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setERSPUserID (Ljava/lang/String;)V
      // 10c5: aload 7
      // 10c7: ldc_w "TerminalID-1654583875"
      // 10ca: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setTerminalID (Ljava/lang/String;)V
      // 10cd: aload 6
      // 10cf: aload 7
      // 10d1: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 10d6: pop
      // 10d7: aload 5
      // 10d9: invokevirtual org/opentravel/ota/_2003/_05/POSType.getSource ()Ljava/util/List;
      // 10dc: aload 6
      // 10de: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 10e3: pop
      // 10e4: aload 4
      // 10e6: aload 5
      // 10e8: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setPOS (Lorg/opentravel/ota/_2003/_05/POSType;)V
      // 10eb: new org/opentravel/ota/_2003/_05/UniqueIDType
      // 10ee: dup
      // 10ef: invokespecial org/opentravel/ota/_2003/_05/UniqueIDType.<init> ()V
      // 10f2: astore 13
      // 10f4: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 10f7: dup
      // 10f8: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 10fb: astore 14
      // 10fd: aload 14
      // 10ff: ldc_w "Value-1966516493"
      // 1102: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 1105: aload 14
      // 1107: ldc_w "Division2114662845"
      // 110a: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 110d: aload 14
      // 110f: ldc_w "Department1912624134"
      // 1112: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 1115: aload 14
      // 1117: ldc_w "CompanyShortName-96292195"
      // 111a: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 111d: aload 14
      // 111f: ldc_w "TravelSector-2112429035"
      // 1122: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 1125: aload 14
      // 1127: ldc_w "Code-14851945"
      // 112a: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 112d: aload 14
      // 112f: ldc_w "CodeContext1496685545"
      // 1132: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 1135: aload 13
      // 1137: aload 14
      // 1139: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 113c: aload 13
      // 113e: ldc_w "URL559556376"
      // 1141: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setURL (Ljava/lang/String;)V
      // 1144: aload 13
      // 1146: ldc_w "Type-1486370570"
      // 1149: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setType (Ljava/lang/String;)V
      // 114c: aload 13
      // 114e: ldc_w "Instance-1608022844"
      // 1151: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setInstance (Ljava/lang/String;)V
      // 1154: aload 13
      // 1156: ldc_w "IDContext1667933769"
      // 1159: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setIDContext (Ljava/lang/String;)V
      // 115c: aload 13
      // 115e: ldc_w "ID-442042163"
      // 1161: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setID (Ljava/lang/String;)V
      // 1164: aload 4
      // 1166: aload 13
      // 1168: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setUniqueID (Lorg/opentravel/ota/_2003/_05/UniqueIDType;)V
      // 116b: new org/htng/_2011b/HTNGRequestBaseType$PropertyInfo
      // 116e: dup
      // 116f: invokespecial org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.<init> ()V
      // 1172: astore 15
      // 1174: aload 15
      // 1176: ldc_w "ChainCode766993577"
      // 1179: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setChainCode (Ljava/lang/String;)V
      // 117c: aload 15
      // 117e: ldc_w "BrandCode-1083809232"
      // 1181: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setBrandCode (Ljava/lang/String;)V
      // 1184: aload 15
      // 1186: ldc_w "HotelCode-1672627249"
      // 1189: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCode (Ljava/lang/String;)V
      // 118c: aload 15
      // 118e: ldc_w "HotelCityCode-1123273590"
      // 1191: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCityCode (Ljava/lang/String;)V
      // 1194: aload 15
      // 1196: ldc_w "HotelName-2144227104"
      // 1199: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelName (Ljava/lang/String;)V
      // 119c: aload 15
      // 119e: ldc_w "HotelCodeContext1477820328"
      // 11a1: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCodeContext (Ljava/lang/String;)V
      // 11a4: aload 15
      // 11a6: ldc_w "ChainName1105116815"
      // 11a9: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setChainName (Ljava/lang/String;)V
      // 11ac: aload 15
      // 11ae: ldc_w "BrandName-308682241"
      // 11b1: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setBrandName (Ljava/lang/String;)V
      // 11b4: aload 15
      // 11b6: ldc_w "AreaID-1315920495"
      // 11b9: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setAreaID (Ljava/lang/String;)V
      // 11bc: aload 4
      // 11be: aload 15
      // 11c0: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setPropertyInfo (Lorg/htng/_2011b/HTNGRequestBaseType$PropertyInfo;)V
      // 11c3: aload 4
      // 11c5: ldc_w "EchoToken1743334317"
      // 11c8: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setEchoToken (Ljava/lang/String;)V
      // 11cb: aload 4
      // 11cd: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 11d0: ldc_w "2020-03-24T09:54:33.652+08:00"
      // 11d3: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 11d6: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setTimeStamp (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 11d9: aload 4
      // 11db: ldc_w "Target-987664311"
      // 11de: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setTarget (Ljava/lang/String;)V
      // 11e1: aload 4
      // 11e3: ldc_w "TargetName-1018820939"
      // 11e6: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setTargetName (Ljava/lang/String;)V
      // 11e9: aload 4
      // 11eb: new java/math/BigDecimal
      // 11ee: dup
      // 11ef: ldc_w "-1911299935227364724.5929172619290057667"
      // 11f2: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 11f5: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setVersion (Ljava/math/BigDecimal;)V
      // 11f8: aload 4
      // 11fa: ldc_w "TransactionIdentifier-283216620"
      // 11fd: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setTransactionIdentifier (Ljava/lang/String;)V
      // 1200: aload 4
      // 1202: new java/math/BigInteger
      // 1205: dup
      // 1206: ldc_w "-35969493020818100864529325640447559519"
      // 1209: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 120c: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setSequenceNmbr (Ljava/math/BigInteger;)V
      // 120f: aload 4
      // 1211: ldc_w "TransactionStatusCode1869829156"
      // 1214: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setTransactionStatusCode (Ljava/lang/String;)V
      // 1217: aload 4
      // 1219: bipush 0
      // 121a: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 121d: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setRetransmissionIndicator (Ljava/lang/Boolean;)V
      // 1220: aload 4
      // 1222: ldc_w "CorrelationID1808789574"
      // 1225: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setCorrelationID (Ljava/lang/String;)V
      // 1228: aload 4
      // 122a: ldc_w "PrimaryLangID980850717"
      // 122d: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setPrimaryLangID (Ljava/lang/String;)V
      // 1230: aload 4
      // 1232: ldc_w "AltLangID2108605591"
      // 1235: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setAltLangID (Ljava/lang/String;)V
      // 1238: new org/htng/_2011b/HTNGCollectionOfUniqueIDs
      // 123b: dup
      // 123c: invokespecial org/htng/_2011b/HTNGCollectionOfUniqueIDs.<init> ()V
      // 123f: astore 16
      // 1241: new org/opentravel/ota/_2003/_05/UniqueIDType
      // 1244: dup
      // 1245: invokespecial org/opentravel/ota/_2003/_05/UniqueIDType.<init> ()V
      // 1248: astore 17
      // 124a: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 124d: dup
      // 124e: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 1251: astore 18
      // 1253: aload 18
      // 1255: ldc_w "Value-2066905015"
      // 1258: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 125b: aload 18
      // 125d: ldc_w "Division-658961544"
      // 1260: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 1263: aload 18
      // 1265: ldc_w "Department322773993"
      // 1268: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 126b: aload 18
      // 126d: ldc_w "CompanyShortName1776627771"
      // 1270: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 1273: aload 18
      // 1275: ldc_w "TravelSector90831290"
      // 1278: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 127b: aload 18
      // 127d: ldc_w "Code-135556085"
      // 1280: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 1283: aload 18
      // 1285: ldc_w "CodeContext-815039815"
      // 1288: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 128b: aload 17
      // 128d: aload 18
      // 128f: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 1292: aload 17
      // 1294: ldc_w "URL2032695094"
      // 1297: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setURL (Ljava/lang/String;)V
      // 129a: aload 17
      // 129c: ldc_w "Type501462050"
      // 129f: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setType (Ljava/lang/String;)V
      // 12a2: aload 17
      // 12a4: ldc_w "Instance-1651675497"
      // 12a7: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setInstance (Ljava/lang/String;)V
      // 12aa: aload 17
      // 12ac: ldc_w "IDContext616231746"
      // 12af: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setIDContext (Ljava/lang/String;)V
      // 12b2: aload 17
      // 12b4: ldc_w "ID-531839441"
      // 12b7: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setID (Ljava/lang/String;)V
      // 12ba: aload 16
      // 12bc: aload 17
      // 12be: invokevirtual org/htng/_2011b/HTNGCollectionOfUniqueIDs.setUniqueID (Lorg/opentravel/ota/_2003/_05/UniqueIDType;)V
      // 12c1: aload 4
      // 12c3: aload 16
      // 12c5: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setAffectedGuests (Lorg/htng/_2011b/HTNGCollectionOfUniqueIDs;)V
      // 12c8: new org/htng/_2011b/HTNGRoomElementType
      // 12cb: dup
      // 12cc: invokespecial org/htng/_2011b/HTNGRoomElementType.<init> ()V
      // 12cf: astore 19
      // 12d1: new org/opentravel/ota/_2003/_05/RoomTypeType
      // 12d4: dup
      // 12d5: invokespecial org/opentravel/ota/_2003/_05/RoomTypeType.<init> ()V
      // 12d8: astore 20
      // 12da: new org/opentravel/ota/_2003/_05/ParagraphType
      // 12dd: dup
      // 12de: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 12e1: astore 21
      // 12e3: new java/util/ArrayList
      // 12e6: dup
      // 12e7: invokespecial java/util/ArrayList.<init> ()V
      // 12ea: astore 22
      // 12ec: aload 21
      // 12ee: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 12f1: aload 22
      // 12f3: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 12f8: pop
      // 12f9: aload 21
      // 12fb: ldc_w "Name1855443812"
      // 12fe: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 1301: aload 21
      // 1303: new java/math/BigInteger
      // 1306: dup
      // 1307: ldc_w "20837385915337565703069109549735092685"
      // 130a: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 130d: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 1310: aload 21
      // 1312: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1315: ldc_w "2020-03-24T09:54:33.654+08:00"
      // 1318: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 131b: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 131e: aload 21
      // 1320: ldc_w "CreatorID315645048"
      // 1323: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 1326: aload 21
      // 1328: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 132b: ldc_w "2020-03-24T09:54:33.654+08:00"
      // 132e: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1331: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 1334: aload 21
      // 1336: ldc_w "LastModifierID143957535"
      // 1339: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 133c: aload 21
      // 133e: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1341: ldc_w "2020-03-24T09:54:33.655+08:00"
      // 1344: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1347: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 134a: aload 21
      // 134c: ldc_w "Language2039463792"
      // 134f: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 1352: aload 20
      // 1354: aload 21
      // 1356: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomDescription (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 1359: new org/opentravel/ota/_2003/_05/AdditionalDetailsType
      // 135c: dup
      // 135d: invokespecial org/opentravel/ota/_2003/_05/AdditionalDetailsType.<init> ()V
      // 1360: astore 23
      // 1362: new java/util/ArrayList
      // 1365: dup
      // 1366: invokespecial java/util/ArrayList.<init> ()V
      // 1369: astore 24
      // 136b: aload 23
      // 136d: invokevirtual org/opentravel/ota/_2003/_05/AdditionalDetailsType.getAdditionalDetail ()Ljava/util/List;
      // 1370: aload 24
      // 1372: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1377: pop
      // 1378: aload 20
      // 137a: aload 23
      // 137c: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setAdditionalDetails (Lorg/opentravel/ota/_2003/_05/AdditionalDetailsType;)V
      // 137f: new org/opentravel/ota/_2003/_05/RoomTypeType$Amenities
      // 1382: dup
      // 1383: invokespecial org/opentravel/ota/_2003/_05/RoomTypeType$Amenities.<init> ()V
      // 1386: astore 25
      // 1388: new java/util/ArrayList
      // 138b: dup
      // 138c: invokespecial java/util/ArrayList.<init> ()V
      // 138f: astore 26
      // 1391: aload 25
      // 1393: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType$Amenities.getAmenity ()Ljava/util/List;
      // 1396: aload 26
      // 1398: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 139d: pop
      // 139e: aload 20
      // 13a0: aload 25
      // 13a2: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setAmenities (Lorg/opentravel/ota/_2003/_05/RoomTypeType$Amenities;)V
      // 13a5: new java/util/ArrayList
      // 13a8: dup
      // 13a9: invokespecial java/util/ArrayList.<init> ()V
      // 13ac: astore 27
      // 13ae: aload 20
      // 13b0: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getOccupancy ()Ljava/util/List;
      // 13b3: aload 27
      // 13b5: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 13ba: pop
      // 13bb: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 13be: dup
      // 13bf: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 13c2: astore 28
      // 13c4: new java/util/ArrayList
      // 13c7: dup
      // 13c8: invokespecial java/util/ArrayList.<init> ()V
      // 13cb: astore 29
      // 13cd: aload 28
      // 13cf: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 13d2: aload 29
      // 13d4: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 13d9: pop
      // 13da: aload 20
      // 13dc: aload 28
      // 13de: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 13e1: aload 20
      // 13e3: new java/math/BigInteger
      // 13e6: dup
      // 13e7: ldc_w "-1538980252937800688120687670793385664"
      // 13ea: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 13ed: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setNumberOfUnits (Ljava/math/BigInteger;)V
      // 13f0: aload 20
      // 13f2: bipush 0
      // 13f3: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 13f6: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsRoom (Ljava/lang/Boolean;)V
      // 13f9: aload 20
      // 13fb: bipush 0
      // 13fc: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 13ff: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsConverted (Ljava/lang/Boolean;)V
      // 1402: aload 20
      // 1404: bipush 0
      // 1405: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1408: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsAlternate (Ljava/lang/Boolean;)V
      // 140b: aload 20
      // 140d: ldc_w "ReqdGuaranteeType1834132996"
      // 1410: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setReqdGuaranteeType (Ljava/lang/String;)V
      // 1413: aload 20
      // 1415: ldc_w "RoomType1983483235"
      // 1418: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomType (Ljava/lang/String;)V
      // 141b: aload 20
      // 141d: ldc_w "RoomTypeCode-1143446575"
      // 1420: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomTypeCode (Ljava/lang/String;)V
      // 1423: aload 20
      // 1425: ldc_w "RoomCategory-297738710"
      // 1428: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomCategory (Ljava/lang/String;)V
      // 142b: aload 20
      // 142d: ldc_w "RoomID131208278"
      // 1430: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomID (Ljava/lang/String;)V
      // 1433: aload 20
      // 1435: ldc_w -1541042031
      // 1438: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 143b: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setFloor (Ljava/lang/Integer;)V
      // 143e: aload 20
      // 1440: ldc_w "InvBlockCode969216008"
      // 1443: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setInvBlockCode (Ljava/lang/String;)V
      // 1446: aload 20
      // 1448: ldc_w "RoomLocationCode-1673854550"
      // 144b: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomLocationCode (Ljava/lang/String;)V
      // 144e: aload 20
      // 1450: ldc_w "RoomViewCode-1967856890"
      // 1453: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomViewCode (Ljava/lang/String;)V
      // 1456: new java/util/ArrayList
      // 1459: dup
      // 145a: invokespecial java/util/ArrayList.<init> ()V
      // 145d: astore 30
      // 145f: aload 20
      // 1461: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getBedTypeCode ()Ljava/util/List;
      // 1464: aload 30
      // 1466: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 146b: pop
      // 146c: aload 20
      // 146e: bipush 0
      // 146f: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1472: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setNonSmoking (Ljava/lang/Boolean;)V
      // 1475: aload 20
      // 1477: ldc_w "Configuration-933465707"
      // 147a: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setConfiguration (Ljava/lang/String;)V
      // 147d: aload 20
      // 147f: ldc_w "SizeMeasurement432777200"
      // 1482: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setSizeMeasurement (Ljava/lang/String;)V
      // 1485: aload 20
      // 1487: ldc_w 408335221
      // 148a: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 148d: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setQuantity (Ljava/lang/Integer;)V
      // 1490: aload 20
      // 1492: bipush 0
      // 1493: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1496: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setComposite (Ljava/lang/Boolean;)V
      // 1499: aload 20
      // 149b: ldc_w "RoomClassificationCode508621276"
      // 149e: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomClassificationCode (Ljava/lang/String;)V
      // 14a1: aload 20
      // 14a3: ldc_w "RoomArchitectureCode1745235380"
      // 14a6: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomArchitectureCode (Ljava/lang/String;)V
      // 14a9: aload 20
      // 14ab: ldc_w "RoomGender572394786"
      // 14ae: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomGender (Ljava/lang/String;)V
      // 14b1: aload 20
      // 14b3: bipush 1
      // 14b4: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 14b7: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setSharedRoomInd (Ljava/lang/Boolean;)V
      // 14ba: aload 20
      // 14bc: ldc_w "PromotionCode2017634912"
      // 14bf: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setPromotionCode (Ljava/lang/String;)V
      // 14c2: new java/util/ArrayList
      // 14c5: dup
      // 14c6: invokespecial java/util/ArrayList.<init> ()V
      // 14c9: astore 31
      // 14cb: aload 20
      // 14cd: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getPromotionVendorCode ()Ljava/util/List;
      // 14d0: aload 31
      // 14d2: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 14d7: pop
      // 14d8: aload 19
      // 14da: aload 20
      // 14dc: invokevirtual org/htng/_2011b/HTNGRoomElementType.setRoomType (Lorg/opentravel/ota/_2003/_05/RoomTypeType;)V
      // 14df: new org/htng/_2011b/HTNGTelephoneExtensionType
      // 14e2: dup
      // 14e3: invokespecial org/htng/_2011b/HTNGTelephoneExtensionType.<init> ()V
      // 14e6: astore 32
      // 14e8: new java/util/ArrayList
      // 14eb: dup
      // 14ec: invokespecial java/util/ArrayList.<init> ()V
      // 14ef: astore 33
      // 14f1: aload 32
      // 14f3: invokevirtual org/htng/_2011b/HTNGTelephoneExtensionType.getTelephoneExtention ()Ljava/util/List;
      // 14f6: aload 33
      // 14f8: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 14fd: pop
      // 14fe: aload 19
      // 1500: aload 32
      // 1502: invokevirtual org/htng/_2011b/HTNGRoomElementType.setTelephoneExtensions (Lorg/htng/_2011b/HTNGTelephoneExtensionType;)V
      // 1505: getstatic org/htng/_2011b/HTNGHousekeepingStatusType.OCCUPIED_DIRTY Lorg/htng/_2011b/HTNGHousekeepingStatusType;
      // 1508: astore 34
      // 150a: aload 19
      // 150c: aload 34
      // 150e: invokevirtual org/htng/_2011b/HTNGRoomElementType.setHKStatus (Lorg/htng/_2011b/HTNGHousekeepingStatusType;)V
      // 1511: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 1514: dup
      // 1515: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 1518: astore 35
      // 151a: new java/util/ArrayList
      // 151d: dup
      // 151e: invokespecial java/util/ArrayList.<init> ()V
      // 1521: astore 36
      // 1523: aload 35
      // 1525: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 1528: aload 36
      // 152a: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 152f: pop
      // 1530: aload 19
      // 1532: aload 35
      // 1534: invokevirtual org/htng/_2011b/HTNGRoomElementType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 1537: aload 19
      // 1539: ldc_w "RoomID654841477"
      // 153c: invokevirtual org/htng/_2011b/HTNGRoomElementType.setRoomID (Ljava/lang/String;)V
      // 153f: new org/htng/_2011b/HTNGComponentRoomsType
      // 1542: dup
      // 1543: invokespecial org/htng/_2011b/HTNGComponentRoomsType.<init> ()V
      // 1546: astore 37
      // 1548: new java/util/ArrayList
      // 154b: dup
      // 154c: invokespecial java/util/ArrayList.<init> ()V
      // 154f: astore 38
      // 1551: aload 37
      // 1553: invokevirtual org/htng/_2011b/HTNGComponentRoomsType.getComponentRoom ()Ljava/util/List;
      // 1556: aload 38
      // 1558: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 155d: pop
      // 155e: aload 19
      // 1560: aload 37
      // 1562: invokevirtual org/htng/_2011b/HTNGRoomElementType.setComponentRooms (Lorg/htng/_2011b/HTNGComponentRoomsType;)V
      // 1565: aload 4
      // 1567: aload 19
      // 1569: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setRoom (Lorg/htng/_2011b/HTNGRoomElementType;)V
      // 156c: new org/opentravel/ota/_2003/_05/HotelReservationsType
      // 156f: dup
      // 1570: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType.<init> ()V
      // 1573: astore 39
      // 1575: new java/util/ArrayList
      // 1578: dup
      // 1579: invokespecial java/util/ArrayList.<init> ()V
      // 157c: astore 40
      // 157e: new org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation
      // 1581: dup
      // 1582: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.<init> ()V
      // 1585: astore 41
      // 1587: new org/opentravel/ota/_2003/_05/POSType
      // 158a: dup
      // 158b: invokespecial org/opentravel/ota/_2003/_05/POSType.<init> ()V
      // 158e: astore 42
      // 1590: new java/util/ArrayList
      // 1593: dup
      // 1594: invokespecial java/util/ArrayList.<init> ()V
      // 1597: astore 43
      // 1599: aload 42
      // 159b: invokevirtual org/opentravel/ota/_2003/_05/POSType.getSource ()Ljava/util/List;
      // 159e: aload 43
      // 15a0: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 15a5: pop
      // 15a6: aload 41
      // 15a8: aload 42
      // 15aa: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setPOS (Lorg/opentravel/ota/_2003/_05/POSType;)V
      // 15ad: new java/util/ArrayList
      // 15b0: dup
      // 15b1: invokespecial java/util/ArrayList.<init> ()V
      // 15b4: astore 44
      // 15b6: aload 41
      // 15b8: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.getUniqueID ()Ljava/util/List;
      // 15bb: aload 44
      // 15bd: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 15c2: pop
      // 15c3: new org/opentravel/ota/_2003/_05/RoomStaysType
      // 15c6: dup
      // 15c7: invokespecial org/opentravel/ota/_2003/_05/RoomStaysType.<init> ()V
      // 15ca: astore 45
      // 15cc: new java/util/ArrayList
      // 15cf: dup
      // 15d0: invokespecial java/util/ArrayList.<init> ()V
      // 15d3: astore 46
      // 15d5: aload 45
      // 15d7: invokevirtual org/opentravel/ota/_2003/_05/RoomStaysType.getRoomStay ()Ljava/util/List;
      // 15da: aload 46
      // 15dc: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 15e1: pop
      // 15e2: aload 41
      // 15e4: aload 45
      // 15e6: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRoomStays (Lorg/opentravel/ota/_2003/_05/RoomStaysType;)V
      // 15e9: new org/opentravel/ota/_2003/_05/ServicesType
      // 15ec: dup
      // 15ed: invokespecial org/opentravel/ota/_2003/_05/ServicesType.<init> ()V
      // 15f0: astore 47
      // 15f2: new java/util/ArrayList
      // 15f5: dup
      // 15f6: invokespecial java/util/ArrayList.<init> ()V
      // 15f9: astore 48
      // 15fb: aload 47
      // 15fd: invokevirtual org/opentravel/ota/_2003/_05/ServicesType.getService ()Ljava/util/List;
      // 1600: aload 48
      // 1602: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1607: pop
      // 1608: aload 41
      // 160a: aload 47
      // 160c: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setServices (Lorg/opentravel/ota/_2003/_05/ServicesType;)V
      // 160f: new java/util/ArrayList
      // 1612: dup
      // 1613: invokespecial java/util/ArrayList.<init> ()V
      // 1616: astore 49
      // 1618: aload 41
      // 161a: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.getBillingInstructionCode ()Ljava/util/List;
      // 161d: aload 49
      // 161f: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1624: pop
      // 1625: new org/opentravel/ota/_2003/_05/ResGuestsType
      // 1628: dup
      // 1629: invokespecial org/opentravel/ota/_2003/_05/ResGuestsType.<init> ()V
      // 162c: astore 50
      // 162e: new java/util/ArrayList
      // 1631: dup
      // 1632: invokespecial java/util/ArrayList.<init> ()V
      // 1635: astore 51
      // 1637: aload 50
      // 1639: invokevirtual org/opentravel/ota/_2003/_05/ResGuestsType.getResGuest ()Ljava/util/List;
      // 163c: aload 51
      // 163e: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1643: pop
      // 1644: aload 41
      // 1646: aload 50
      // 1648: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setResGuests (Lorg/opentravel/ota/_2003/_05/ResGuestsType;)V
      // 164b: new org/opentravel/ota/_2003/_05/ResGlobalInfoType
      // 164e: dup
      // 164f: invokespecial org/opentravel/ota/_2003/_05/ResGlobalInfoType.<init> ()V
      // 1652: astore 52
      // 1654: new org/opentravel/ota/_2003/_05/GuestCountType
      // 1657: dup
      // 1658: invokespecial org/opentravel/ota/_2003/_05/GuestCountType.<init> ()V
      // 165b: astore 53
      // 165d: new java/util/ArrayList
      // 1660: dup
      // 1661: invokespecial java/util/ArrayList.<init> ()V
      // 1664: astore 54
      // 1666: aload 53
      // 1668: invokevirtual org/opentravel/ota/_2003/_05/GuestCountType.getGuestCount ()Ljava/util/List;
      // 166b: aload 54
      // 166d: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1672: pop
      // 1673: aload 53
      // 1675: bipush 1
      // 1676: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1679: invokevirtual org/opentravel/ota/_2003/_05/GuestCountType.setIsPerRoom (Ljava/lang/Boolean;)V
      // 167c: aload 52
      // 167e: aload 53
      // 1680: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setGuestCounts (Lorg/opentravel/ota/_2003/_05/GuestCountType;)V
      // 1683: new org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan
      // 1686: dup
      // 1687: invokespecial org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.<init> ()V
      // 168a: astore 55
      // 168c: new org/opentravel/ota/_2003/_05/TimeInstantType
      // 168f: dup
      // 1690: invokespecial org/opentravel/ota/_2003/_05/TimeInstantType.<init> ()V
      // 1693: astore 56
      // 1695: aload 56
      // 1697: ldc_w "Value-1798931523"
      // 169a: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setValue (Ljava/lang/String;)V
      // 169d: aload 56
      // 169f: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 16a2: ldc_w "-P147069599Y7M9DT20H14M28.069S"
      // 16a5: invokevirtual javax/xml/datatype/DatatypeFactory.newDuration (Ljava/lang/String;)Ljavax/xml/datatype/Duration;
      // 16a8: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setWindowBefore (Ljavax/xml/datatype/Duration;)V
      // 16ab: aload 56
      // 16ad: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 16b0: ldc_w "P249292592Y7M11DT6H12M8.283S"
      // 16b3: invokevirtual javax/xml/datatype/DatatypeFactory.newDuration (Ljava/lang/String;)Ljavax/xml/datatype/Duration;
      // 16b6: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setWindowAfter (Ljavax/xml/datatype/Duration;)V
      // 16b9: aload 56
      // 16bb: bipush 1
      // 16bc: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 16bf: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setCrossDateAllowedIndicator (Ljava/lang/Boolean;)V
      // 16c2: aload 55
      // 16c4: aload 56
      // 16c6: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setDateWindowRange (Lorg/opentravel/ota/_2003/_05/TimeInstantType;)V
      // 16c9: new org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow
      // 16cc: dup
      // 16cd: invokespecial org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.<init> ()V
      // 16d0: astore 57
      // 16d2: aload 57
      // 16d4: ldc_w "EarliestDate857876162"
      // 16d7: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.setEarliestDate (Ljava/lang/String;)V
      // 16da: aload 57
      // 16dc: ldc_w "LatestDate-1333649083"
      // 16df: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.setLatestDate (Ljava/lang/String;)V
      // 16e2: getstatic org/opentravel/ota/_2003/_05/DayOfWeekType.WED Lorg/opentravel/ota/_2003/_05/DayOfWeekType;
      // 16e5: astore 58
      // 16e7: aload 57
      // 16e9: aload 58
      // 16eb: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.setDOW (Lorg/opentravel/ota/_2003/_05/DayOfWeekType;)V
      // 16ee: aload 55
      // 16f0: aload 57
      // 16f2: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setStartDateWindow (Lorg/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow;)V
      // 16f5: new org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow
      // 16f8: dup
      // 16f9: invokespecial org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.<init> ()V
      // 16fc: astore 59
      // 16fe: aload 59
      // 1700: ldc_w "EarliestDate407969061"
      // 1703: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.setEarliestDate (Ljava/lang/String;)V
      // 1706: aload 59
      // 1708: ldc_w "LatestDate-1889178042"
      // 170b: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.setLatestDate (Ljava/lang/String;)V
      // 170e: getstatic org/opentravel/ota/_2003/_05/DayOfWeekType.FRI Lorg/opentravel/ota/_2003/_05/DayOfWeekType;
      // 1711: astore 60
      // 1713: aload 59
      // 1715: aload 60
      // 1717: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.setDOW (Lorg/opentravel/ota/_2003/_05/DayOfWeekType;)V
      // 171a: aload 55
      // 171c: aload 59
      // 171e: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setEndDateWindow (Lorg/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow;)V
      // 1721: aload 55
      // 1723: ldc_w "Start-1202048410"
      // 1726: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setStart (Ljava/lang/String;)V
      // 1729: aload 55
      // 172b: ldc_w "Duration-1729267644"
      // 172e: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setDuration (Ljava/lang/String;)V
      // 1731: aload 55
      // 1733: ldc_w "End389553467"
      // 1736: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setEnd (Ljava/lang/String;)V
      // 1739: aload 55
      // 173b: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 173e: ldc_w "-P45805631Y8M4DT8H59M4.978S"
      // 1741: invokevirtual javax/xml/datatype/DatatypeFactory.newDuration (Ljava/lang/String;)Ljavax/xml/datatype/Duration;
      // 1744: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setIncrement (Ljavax/xml/datatype/Duration;)V
      // 1747: aload 52
      // 1749: aload 55
      // 174b: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setTimeSpan (Lorg/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan;)V
      // 174e: new org/opentravel/ota/_2003/_05/ResGuestRPHsType
      // 1751: dup
      // 1752: invokespecial org/opentravel/ota/_2003/_05/ResGuestRPHsType.<init> ()V
      // 1755: astore 61
      // 1757: aload 61
      // 1759: ldc_w "Value2008431809"
      // 175c: invokevirtual org/opentravel/ota/_2003/_05/ResGuestRPHsType.setValue (Ljava/lang/String;)V
      // 175f: aload 52
      // 1761: aload 61
      // 1763: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setResGuestRPHs (Lorg/opentravel/ota/_2003/_05/ResGuestRPHsType;)V
      // 1766: new org/opentravel/ota/_2003/_05/MembershipType
      // 1769: dup
      // 176a: invokespecial org/opentravel/ota/_2003/_05/MembershipType.<init> ()V
      // 176d: astore 62
      // 176f: new java/util/ArrayList
      // 1772: dup
      // 1773: invokespecial java/util/ArrayList.<init> ()V
      // 1776: astore 63
      // 1778: aload 62
      // 177a: invokevirtual org/opentravel/ota/_2003/_05/MembershipType.getMembership ()Ljava/util/List;
      // 177d: aload 63
      // 177f: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1784: pop
      // 1785: aload 52
      // 1787: aload 62
      // 1789: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setMemberships (Lorg/opentravel/ota/_2003/_05/MembershipType;)V
      // 178c: new org/opentravel/ota/_2003/_05/CommentType
      // 178f: dup
      // 1790: invokespecial org/opentravel/ota/_2003/_05/CommentType.<init> ()V
      // 1793: astore 64
      // 1795: new java/util/ArrayList
      // 1798: dup
      // 1799: invokespecial java/util/ArrayList.<init> ()V
      // 179c: astore 65
      // 179e: aload 64
      // 17a0: invokevirtual org/opentravel/ota/_2003/_05/CommentType.getComment ()Ljava/util/List;
      // 17a3: aload 65
      // 17a5: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 17aa: pop
      // 17ab: aload 52
      // 17ad: aload 64
      // 17af: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setComments (Lorg/opentravel/ota/_2003/_05/CommentType;)V
      // 17b2: new org/opentravel/ota/_2003/_05/SpecialRequestType
      // 17b5: dup
      // 17b6: invokespecial org/opentravel/ota/_2003/_05/SpecialRequestType.<init> ()V
      // 17b9: astore 66
      // 17bb: new java/util/ArrayList
      // 17be: dup
      // 17bf: invokespecial java/util/ArrayList.<init> ()V
      // 17c2: astore 67
      // 17c4: aload 66
      // 17c6: invokevirtual org/opentravel/ota/_2003/_05/SpecialRequestType.getSpecialRequest ()Ljava/util/List;
      // 17c9: aload 67
      // 17cb: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 17d0: pop
      // 17d1: aload 52
      // 17d3: aload 66
      // 17d5: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setSpecialRequests (Lorg/opentravel/ota/_2003/_05/SpecialRequestType;)V
      // 17d8: new org/opentravel/ota/_2003/_05/GuaranteeType
      // 17db: dup
      // 17dc: invokespecial org/opentravel/ota/_2003/_05/GuaranteeType.<init> ()V
      // 17df: astore 68
      // 17e1: new org/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted
      // 17e4: dup
      // 17e5: invokespecial org/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted.<init> ()V
      // 17e8: astore 69
      // 17ea: new java/util/ArrayList
      // 17ed: dup
      // 17ee: invokespecial java/util/ArrayList.<init> ()V
      // 17f1: astore 70
      // 17f3: aload 69
      // 17f5: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted.getGuaranteeAccepted ()Ljava/util/List;
      // 17f8: aload 70
      // 17fa: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 17ff: pop
      // 1800: aload 68
      // 1802: aload 69
      // 1804: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setGuaranteesAccepted (Lorg/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted;)V
      // 1807: new org/opentravel/ota/_2003/_05/GuaranteeType$Deadline
      // 180a: dup
      // 180b: invokespecial org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.<init> ()V
      // 180e: astore 71
      // 1810: aload 71
      // 1812: ldc_w "AbsoluteDeadline1500371212"
      // 1815: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setAbsoluteDeadline (Ljava/lang/String;)V
      // 1818: getstatic org/opentravel/ota/_2003/_05/TimeUnitType.HOUR Lorg/opentravel/ota/_2003/_05/TimeUnitType;
      // 181b: astore 72
      // 181d: aload 71
      // 181f: aload 72
      // 1821: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setOffsetTimeUnit (Lorg/opentravel/ota/_2003/_05/TimeUnitType;)V
      // 1824: aload 71
      // 1826: ldc_w -191351917
      // 1829: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 182c: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setOffsetUnitMultiplier (Ljava/lang/Integer;)V
      // 182f: aload 71
      // 1831: ldc_w "OffsetDropTime-604838057"
      // 1834: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setOffsetDropTime (Ljava/lang/String;)V
      // 1837: aload 68
      // 1839: aload 71
      // 183b: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setDeadline (Lorg/opentravel/ota/_2003/_05/GuaranteeType$Deadline;)V
      // 183e: new org/opentravel/ota/_2003/_05/CommentType
      // 1841: dup
      // 1842: invokespecial org/opentravel/ota/_2003/_05/CommentType.<init> ()V
      // 1845: astore 73
      // 1847: new java/util/ArrayList
      // 184a: dup
      // 184b: invokespecial java/util/ArrayList.<init> ()V
      // 184e: astore 74
      // 1850: aload 73
      // 1852: invokevirtual org/opentravel/ota/_2003/_05/CommentType.getComment ()Ljava/util/List;
      // 1855: aload 74
      // 1857: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 185c: pop
      // 185d: aload 68
      // 185f: aload 73
      // 1861: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setComments (Lorg/opentravel/ota/_2003/_05/CommentType;)V
      // 1864: new java/util/ArrayList
      // 1867: dup
      // 1868: invokespecial java/util/ArrayList.<init> ()V
      // 186b: astore 75
      // 186d: aload 68
      // 186f: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.getGuaranteeDescription ()Ljava/util/List;
      // 1872: aload 75
      // 1874: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1879: pop
      // 187a: aload 68
      // 187c: ldc_w "RetributionType1683433812"
      // 187f: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setRetributionType (Ljava/lang/String;)V
      // 1882: aload 68
      // 1884: ldc_w "GuaranteeCode447220638"
      // 1887: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setGuaranteeCode (Ljava/lang/String;)V
      // 188a: aload 68
      // 188c: ldc_w "GuaranteeType200039170"
      // 188f: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setGuaranteeType (Ljava/lang/String;)V
      // 1892: aload 68
      // 1894: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1897: ldc_w "2020-03-24T09:54:33.659+08:00"
      // 189a: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 189d: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setHoldTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 18a0: aload 52
      // 18a2: aload 68
      // 18a4: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setGuarantee (Lorg/opentravel/ota/_2003/_05/GuaranteeType;)V
      // 18a7: new org/opentravel/ota/_2003/_05/RequiredPaymentsType
      // 18aa: dup
      // 18ab: invokespecial org/opentravel/ota/_2003/_05/RequiredPaymentsType.<init> ()V
      // 18ae: astore 76
      // 18b0: new java/util/ArrayList
      // 18b3: dup
      // 18b4: invokespecial java/util/ArrayList.<init> ()V
      // 18b7: astore 77
      // 18b9: aload 76
      // 18bb: invokevirtual org/opentravel/ota/_2003/_05/RequiredPaymentsType.getGuaranteePayment ()Ljava/util/List;
      // 18be: aload 77
      // 18c0: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 18c5: pop
      // 18c6: aload 52
      // 18c8: aload 76
      // 18ca: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setDepositPayments (Lorg/opentravel/ota/_2003/_05/RequiredPaymentsType;)V
      // 18cd: new org/opentravel/ota/_2003/_05/CancelPenaltiesType
      // 18d0: dup
      // 18d1: invokespecial org/opentravel/ota/_2003/_05/CancelPenaltiesType.<init> ()V
      // 18d4: astore 78
      // 18d6: new java/util/ArrayList
      // 18d9: dup
      // 18da: invokespecial java/util/ArrayList.<init> ()V
      // 18dd: astore 79
      // 18df: aload 78
      // 18e1: invokevirtual org/opentravel/ota/_2003/_05/CancelPenaltiesType.getCancelPenalty ()Ljava/util/List;
      // 18e4: aload 79
      // 18e6: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 18eb: pop
      // 18ec: aload 78
      // 18ee: bipush 1
      // 18ef: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 18f2: invokevirtual org/opentravel/ota/_2003/_05/CancelPenaltiesType.setCancelPolicyIndicator (Ljava/lang/Boolean;)V
      // 18f5: aload 52
      // 18f7: aload 78
      // 18f9: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setCancelPenalties (Lorg/opentravel/ota/_2003/_05/CancelPenaltiesType;)V
      // 18fc: new org/opentravel/ota/_2003/_05/FeesType
      // 18ff: dup
      // 1900: invokespecial org/opentravel/ota/_2003/_05/FeesType.<init> ()V
      // 1903: astore 80
      // 1905: new java/util/ArrayList
      // 1908: dup
      // 1909: invokespecial java/util/ArrayList.<init> ()V
      // 190c: astore 81
      // 190e: aload 80
      // 1910: invokevirtual org/opentravel/ota/_2003/_05/FeesType.getFee ()Ljava/util/List;
      // 1913: aload 81
      // 1915: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 191a: pop
      // 191b: aload 52
      // 191d: aload 80
      // 191f: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setFees (Lorg/opentravel/ota/_2003/_05/FeesType;)V
      // 1922: new org/opentravel/ota/_2003/_05/TotalType
      // 1925: dup
      // 1926: invokespecial org/opentravel/ota/_2003/_05/TotalType.<init> ()V
      // 1929: astore 82
      // 192b: new org/opentravel/ota/_2003/_05/TaxesType
      // 192e: dup
      // 192f: invokespecial org/opentravel/ota/_2003/_05/TaxesType.<init> ()V
      // 1932: astore 83
      // 1934: new java/util/ArrayList
      // 1937: dup
      // 1938: invokespecial java/util/ArrayList.<init> ()V
      // 193b: astore 84
      // 193d: aload 83
      // 193f: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.getTax ()Ljava/util/List;
      // 1942: aload 84
      // 1944: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1949: pop
      // 194a: aload 83
      // 194c: new java/math/BigDecimal
      // 194f: dup
      // 1950: ldc_w "-4754282316175340264.2275118981599819345"
      // 1953: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 1956: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.setAmount (Ljava/math/BigDecimal;)V
      // 1959: aload 83
      // 195b: ldc_w "CurrencyCode-1217960124"
      // 195e: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.setCurrencyCode (Ljava/lang/String;)V
      // 1961: aload 83
      // 1963: new java/math/BigInteger
      // 1966: dup
      // 1967: ldc_w "-8022174766272393784927848318714648438"
      // 196a: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 196d: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.setDecimalPlaces (Ljava/math/BigInteger;)V
      // 1970: aload 82
      // 1972: aload 83
      // 1974: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setTaxes (Lorg/opentravel/ota/_2003/_05/TaxesType;)V
      // 1977: aload 82
      // 1979: new java/math/BigDecimal
      // 197c: dup
      // 197d: ldc_w "-3369466742266757854.4216429348271761048"
      // 1980: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 1983: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAmountBeforeTax (Ljava/math/BigDecimal;)V
      // 1986: aload 82
      // 1988: new java/math/BigDecimal
      // 198b: dup
      // 198c: ldc_w "-7471716148306161034.1940287893899413457"
      // 198f: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 1992: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAmountAfterTax (Ljava/math/BigDecimal;)V
      // 1995: aload 82
      // 1997: bipush 0
      // 1998: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 199b: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAdditionalFeesExcludedIndicator (Ljava/lang/Boolean;)V
      // 199e: aload 82
      // 19a0: ldc_w "Type-11193043"
      // 19a3: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setType (Ljava/lang/String;)V
      // 19a6: aload 82
      // 19a8: bipush 0
      // 19a9: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 19ac: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setServiceOverrideIndicator (Ljava/lang/Boolean;)V
      // 19af: aload 82
      // 19b1: bipush 0
      // 19b2: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 19b5: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setRateOverrideIndicator (Ljava/lang/Boolean;)V
      // 19b8: aload 82
      // 19ba: new java/math/BigDecimal
      // 19bd: dup
      // 19be: ldc_w "-583834110616228782.4149060213494029770"
      // 19c1: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 19c4: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAmountIncludingMarkup (Ljava/math/BigDecimal;)V
      // 19c7: aload 82
      // 19c9: ldc_w "CurrencyCode367432006"
      // 19cc: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setCurrencyCode (Ljava/lang/String;)V
      // 19cf: aload 82
      // 19d1: new java/math/BigInteger
      // 19d4: dup
      // 19d5: ldc_w "-1976832044534108261490340479628362921"
      // 19d8: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 19db: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setDecimalPlaces (Ljava/math/BigInteger;)V
      // 19de: aload 52
      // 19e0: aload 82
      // 19e2: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setTotal (Lorg/opentravel/ota/_2003/_05/TotalType;)V
      // 19e5: new org/opentravel/ota/_2003/_05/HotelReservationIDsType
      // 19e8: dup
      // 19e9: invokespecial org/opentravel/ota/_2003/_05/HotelReservationIDsType.<init> ()V
      // 19ec: astore 85
      // 19ee: new java/util/ArrayList
      // 19f1: dup
      // 19f2: invokespecial java/util/ArrayList.<init> ()V
      // 19f5: astore 86
      // 19f7: aload 85
      // 19f9: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationIDsType.getHotelReservationID ()Ljava/util/List;
      // 19fc: aload 86
      // 19fe: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1a03: pop
      // 1a04: aload 52
      // 1a06: aload 85
      // 1a08: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setHotelReservationIDs (Lorg/opentravel/ota/_2003/_05/HotelReservationIDsType;)V
      // 1a0b: new org/opentravel/ota/_2003/_05/RoutingHopType
      // 1a0e: dup
      // 1a0f: invokespecial org/opentravel/ota/_2003/_05/RoutingHopType.<init> ()V
      // 1a12: astore 87
      // 1a14: new java/util/ArrayList
      // 1a17: dup
      // 1a18: invokespecial java/util/ArrayList.<init> ()V
      // 1a1b: astore 88
      // 1a1d: aload 87
      // 1a1f: invokevirtual org/opentravel/ota/_2003/_05/RoutingHopType.getRoutingHop ()Ljava/util/List;
      // 1a22: aload 88
      // 1a24: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1a29: pop
      // 1a2a: aload 52
      // 1a2c: aload 87
      // 1a2e: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setRoutingHops (Lorg/opentravel/ota/_2003/_05/RoutingHopType;)V
      // 1a31: new org/opentravel/ota/_2003/_05/ProfilesType
      // 1a34: dup
      // 1a35: invokespecial org/opentravel/ota/_2003/_05/ProfilesType.<init> ()V
      // 1a38: astore 89
      // 1a3a: new java/util/ArrayList
      // 1a3d: dup
      // 1a3e: invokespecial java/util/ArrayList.<init> ()V
      // 1a41: astore 90
      // 1a43: aload 89
      // 1a45: invokevirtual org/opentravel/ota/_2003/_05/ProfilesType.getProfileInfo ()Ljava/util/List;
      // 1a48: aload 90
      // 1a4a: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1a4f: pop
      // 1a50: aload 52
      // 1a52: aload 89
      // 1a54: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setProfiles (Lorg/opentravel/ota/_2003/_05/ProfilesType;)V
      // 1a57: new org/opentravel/ota/_2003/_05/BookingRulesType
      // 1a5a: dup
      // 1a5b: invokespecial org/opentravel/ota/_2003/_05/BookingRulesType.<init> ()V
      // 1a5e: astore 91
      // 1a60: new java/util/ArrayList
      // 1a63: dup
      // 1a64: invokespecial java/util/ArrayList.<init> ()V
      // 1a67: astore 92
      // 1a69: aload 91
      // 1a6b: invokevirtual org/opentravel/ota/_2003/_05/BookingRulesType.getBookingRule ()Ljava/util/List;
      // 1a6e: aload 92
      // 1a70: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1a75: pop
      // 1a76: aload 52
      // 1a78: aload 91
      // 1a7a: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setBookingRules (Lorg/opentravel/ota/_2003/_05/BookingRulesType;)V
      // 1a7d: aload 41
      // 1a7f: aload 52
      // 1a81: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setResGlobalInfo (Lorg/opentravel/ota/_2003/_05/ResGlobalInfoType;)V
      // 1a84: new org/opentravel/ota/_2003/_05/WrittenConfInstType
      // 1a87: dup
      // 1a88: invokespecial org/opentravel/ota/_2003/_05/WrittenConfInstType.<init> ()V
      // 1a8b: astore 93
      // 1a8d: new org/opentravel/ota/_2003/_05/ParagraphType
      // 1a90: dup
      // 1a91: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 1a94: astore 94
      // 1a96: new java/util/ArrayList
      // 1a99: dup
      // 1a9a: invokespecial java/util/ArrayList.<init> ()V
      // 1a9d: astore 95
      // 1a9f: aload 94
      // 1aa1: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 1aa4: aload 95
      // 1aa6: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1aab: pop
      // 1aac: aload 94
      // 1aae: ldc_w "Name-980895055"
      // 1ab1: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 1ab4: aload 94
      // 1ab6: new java/math/BigInteger
      // 1ab9: dup
      // 1aba: ldc_w "25348195041988577147983198213267789001"
      // 1abd: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 1ac0: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 1ac3: aload 94
      // 1ac5: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1ac8: ldc_w "2020-03-24T09:54:33.661+08:00"
      // 1acb: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1ace: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 1ad1: aload 94
      // 1ad3: ldc_w "CreatorID1082522895"
      // 1ad6: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 1ad9: aload 94
      // 1adb: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1ade: ldc_w "2020-03-24T09:54:33.662+08:00"
      // 1ae1: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1ae4: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 1ae7: aload 94
      // 1ae9: ldc_w "LastModifierID-1220734266"
      // 1aec: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 1aef: aload 94
      // 1af1: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1af4: ldc_w "2020-03-24T09:54:33.662+08:00"
      // 1af7: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1afa: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 1afd: aload 94
      // 1aff: ldc_w "Language-661099173"
      // 1b02: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 1b05: aload 93
      // 1b07: aload 94
      // 1b09: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setSupplementalData (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 1b0c: new org/opentravel/ota/_2003/_05/EmailType
      // 1b0f: dup
      // 1b10: invokespecial org/opentravel/ota/_2003/_05/EmailType.<init> ()V
      // 1b13: astore 96
      // 1b15: aload 96
      // 1b17: ldc_w "Value-1921371791"
      // 1b1a: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setValue (Ljava/lang/String;)V
      // 1b1d: aload 96
      // 1b1f: ldc_w "EmailType-1241453687"
      // 1b22: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setEmailType (Ljava/lang/String;)V
      // 1b25: aload 96
      // 1b27: ldc_w "RPH-366638488"
      // 1b2a: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRPH (Ljava/lang/String;)V
      // 1b2d: aload 96
      // 1b2f: ldc_w "Remark374523662"
      // 1b32: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRemark (Ljava/lang/String;)V
      // 1b35: aload 96
      // 1b37: bipush 0
      // 1b38: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1b3b: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setDefaultInd (Ljava/lang/Boolean;)V
      // 1b3e: aload 96
      // 1b40: ldc_w "ShareSynchInd-256004027"
      // 1b43: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareSynchInd (Ljava/lang/String;)V
      // 1b46: aload 96
      // 1b48: ldc_w "ShareMarketInd600709418"
      // 1b4b: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareMarketInd (Ljava/lang/String;)V
      // 1b4e: aload 93
      // 1b50: aload 96
      // 1b52: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setEmail (Lorg/opentravel/ota/_2003/_05/EmailType;)V
      // 1b55: aload 93
      // 1b57: ldc_w "LanguageID2090036297"
      // 1b5a: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setLanguageID (Ljava/lang/String;)V
      // 1b5d: aload 93
      // 1b5f: ldc_w "AddresseeName1724638184"
      // 1b62: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddresseeName (Ljava/lang/String;)V
      // 1b65: aload 93
      // 1b67: ldc_w "Address-69115298"
      // 1b6a: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddress (Ljava/lang/String;)V
      // 1b6d: aload 93
      // 1b6f: ldc_w "Telephone2008834445"
      // 1b72: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setTelephone (Ljava/lang/String;)V
      // 1b75: aload 93
      // 1b77: bipush 0
      // 1b78: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1b7b: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setConfirmInd (Ljava/lang/Boolean;)V
      // 1b7e: aload 41
      // 1b80: aload 93
      // 1b82: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setWrittenConfInst (Lorg/opentravel/ota/_2003/_05/WrittenConfInstType;)V
      // 1b85: new org/opentravel/ota/_2003/_05/HotelReservationType$Queue
      // 1b88: dup
      // 1b89: invokespecial org/opentravel/ota/_2003/_05/HotelReservationType$Queue.<init> ()V
      // 1b8c: astore 97
      // 1b8e: aload 97
      // 1b90: ldc_w "PseudoCityCode-977995443"
      // 1b93: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setPseudoCityCode (Ljava/lang/String;)V
      // 1b96: aload 97
      // 1b98: ldc_w "QueueNumber-660151102"
      // 1b9b: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setQueueNumber (Ljava/lang/String;)V
      // 1b9e: aload 97
      // 1ba0: ldc_w "QueueCategory-818957288"
      // 1ba3: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setQueueCategory (Ljava/lang/String;)V
      // 1ba6: aload 97
      // 1ba8: ldc_w "SystemCode-1775068760"
      // 1bab: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setSystemCode (Ljava/lang/String;)V
      // 1bae: aload 97
      // 1bb0: ldc_w "QueueID24493918"
      // 1bb3: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setQueueID (Ljava/lang/String;)V
      // 1bb6: aload 41
      // 1bb8: aload 97
      // 1bba: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setQueue (Lorg/opentravel/ota/_2003/_05/HotelReservationType$Queue;)V
      // 1bbd: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 1bc0: dup
      // 1bc1: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 1bc4: astore 98
      // 1bc6: new java/util/ArrayList
      // 1bc9: dup
      // 1bca: invokespecial java/util/ArrayList.<init> ()V
      // 1bcd: astore 99
      // 1bcf: aload 98
      // 1bd1: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 1bd4: aload 99
      // 1bd6: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1bdb: pop
      // 1bdc: aload 41
      // 1bde: aload 98
      // 1be0: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 1be3: aload 41
      // 1be5: bipush 0
      // 1be6: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1be9: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRoomStayReservation (Ljava/lang/Boolean;)V
      // 1bec: aload 41
      // 1bee: ldc_w "ResStatus1173807722"
      // 1bf1: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setResStatus (Ljava/lang/String;)V
      // 1bf4: aload 41
      // 1bf6: bipush 1
      // 1bf7: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1bfa: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setForcedSellIndicator (Ljava/lang/Boolean;)V
      // 1bfd: aload 41
      // 1bff: bipush 0
      // 1c00: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1c03: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setServiceOverrideIndicator (Ljava/lang/Boolean;)V
      // 1c06: aload 41
      // 1c08: bipush 0
      // 1c09: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1c0c: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRateOverrideIndicator (Ljava/lang/Boolean;)V
      // 1c0f: aload 41
      // 1c11: bipush 0
      // 1c12: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1c15: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setWalkInIndicator (Ljava/lang/Boolean;)V
      // 1c18: aload 41
      // 1c1a: bipush 1
      // 1c1b: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1c1e: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRoomNumberLockedIndicator (Ljava/lang/Boolean;)V
      // 1c21: aload 41
      // 1c23: ldc_w "OriginalDeliveryMethodCode1873581456"
      // 1c26: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setOriginalDeliveryMethodCode (Ljava/lang/String;)V
      // 1c29: aload 41
      // 1c2b: bipush 0
      // 1c2c: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1c2f: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setPassiveIndicator (Ljava/lang/Boolean;)V
      // 1c32: aload 41
      // 1c34: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1c37: ldc_w "2020-03-24T09:54:33.663+08:00"
      // 1c3a: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1c3d: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 1c40: aload 41
      // 1c42: ldc_w "CreatorID602698142"
      // 1c45: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setCreatorID (Ljava/lang/String;)V
      // 1c48: aload 41
      // 1c4a: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1c4d: ldc_w "2020-03-24T09:54:33.663+08:00"
      // 1c50: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1c53: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 1c56: aload 41
      // 1c58: ldc_w "LastModifierID260899512"
      // 1c5b: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setLastModifierID (Ljava/lang/String;)V
      // 1c5e: aload 41
      // 1c60: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1c63: ldc_w "2020-03-24T09:54:33.663+08:00"
      // 1c66: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1c69: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 1c6c: new org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms
      // 1c6f: dup
      // 1c70: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms.<init> ()V
      // 1c73: astore 100
      // 1c75: new java/util/ArrayList
      // 1c78: dup
      // 1c79: invokespecial java/util/ArrayList.<init> ()V
      // 1c7c: astore 101
      // 1c7e: aload 100
      // 1c80: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms.getRebateProgram ()Ljava/util/List;
      // 1c83: aload 101
      // 1c85: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1c8a: pop
      // 1c8b: aload 41
      // 1c8d: aload 100
      // 1c8f: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRebatePrograms (Lorg/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms;)V
      // 1c92: aload 40
      // 1c94: aload 41
      // 1c96: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 1c9b: pop
      // 1c9c: aload 39
      // 1c9e: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.getHotelReservation ()Ljava/util/List;
      // 1ca1: aload 40
      // 1ca3: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1ca8: pop
      // 1ca9: new org/opentravel/ota/_2003/_05/RoutingHopType
      // 1cac: dup
      // 1cad: invokespecial org/opentravel/ota/_2003/_05/RoutingHopType.<init> ()V
      // 1cb0: astore 102
      // 1cb2: new java/util/ArrayList
      // 1cb5: dup
      // 1cb6: invokespecial java/util/ArrayList.<init> ()V
      // 1cb9: astore 103
      // 1cbb: aload 102
      // 1cbd: invokevirtual org/opentravel/ota/_2003/_05/RoutingHopType.getRoutingHop ()Ljava/util/List;
      // 1cc0: aload 103
      // 1cc2: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1cc7: pop
      // 1cc8: aload 39
      // 1cca: aload 102
      // 1ccc: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setRoutingHops (Lorg/opentravel/ota/_2003/_05/RoutingHopType;)V
      // 1ccf: new org/opentravel/ota/_2003/_05/WrittenConfInstType
      // 1cd2: dup
      // 1cd3: invokespecial org/opentravel/ota/_2003/_05/WrittenConfInstType.<init> ()V
      // 1cd6: astore 104
      // 1cd8: new org/opentravel/ota/_2003/_05/ParagraphType
      // 1cdb: dup
      // 1cdc: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 1cdf: astore 105
      // 1ce1: new java/util/ArrayList
      // 1ce4: dup
      // 1ce5: invokespecial java/util/ArrayList.<init> ()V
      // 1ce8: astore 106
      // 1cea: aload 105
      // 1cec: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 1cef: aload 106
      // 1cf1: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1cf6: pop
      // 1cf7: aload 105
      // 1cf9: ldc_w "Name444533044"
      // 1cfc: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 1cff: aload 105
      // 1d01: new java/math/BigInteger
      // 1d04: dup
      // 1d05: ldc_w "78502782007856149618991207022668461871"
      // 1d08: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 1d0b: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 1d0e: aload 105
      // 1d10: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1d13: ldc_w "2020-03-24T09:54:33.664+08:00"
      // 1d16: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1d19: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 1d1c: aload 105
      // 1d1e: ldc_w "CreatorID-219873204"
      // 1d21: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 1d24: aload 105
      // 1d26: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1d29: ldc_w "2020-03-24T09:54:33.664+08:00"
      // 1d2c: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1d2f: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 1d32: aload 105
      // 1d34: ldc_w "LastModifierID-1136194086"
      // 1d37: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 1d3a: aload 105
      // 1d3c: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 1d3f: ldc_w "2020-03-24T09:54:33.665+08:00"
      // 1d42: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 1d45: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 1d48: aload 105
      // 1d4a: ldc_w "Language1483830871"
      // 1d4d: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 1d50: aload 104
      // 1d52: aload 105
      // 1d54: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setSupplementalData (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 1d57: new org/opentravel/ota/_2003/_05/EmailType
      // 1d5a: dup
      // 1d5b: invokespecial org/opentravel/ota/_2003/_05/EmailType.<init> ()V
      // 1d5e: astore 107
      // 1d60: aload 107
      // 1d62: ldc_w "Value1602004838"
      // 1d65: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setValue (Ljava/lang/String;)V
      // 1d68: aload 107
      // 1d6a: ldc_w "EmailType-1569726554"
      // 1d6d: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setEmailType (Ljava/lang/String;)V
      // 1d70: aload 107
      // 1d72: ldc_w "RPH-1155325215"
      // 1d75: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRPH (Ljava/lang/String;)V
      // 1d78: aload 107
      // 1d7a: ldc_w "Remark-228462295"
      // 1d7d: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRemark (Ljava/lang/String;)V
      // 1d80: aload 107
      // 1d82: bipush 1
      // 1d83: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1d86: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setDefaultInd (Ljava/lang/Boolean;)V
      // 1d89: aload 107
      // 1d8b: ldc_w "ShareSynchInd1481950489"
      // 1d8e: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareSynchInd (Ljava/lang/String;)V
      // 1d91: aload 107
      // 1d93: ldc_w "ShareMarketInd-970129821"
      // 1d96: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareMarketInd (Ljava/lang/String;)V
      // 1d99: aload 104
      // 1d9b: aload 107
      // 1d9d: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setEmail (Lorg/opentravel/ota/_2003/_05/EmailType;)V
      // 1da0: aload 104
      // 1da2: ldc_w "LanguageID1396982288"
      // 1da5: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setLanguageID (Ljava/lang/String;)V
      // 1da8: aload 104
      // 1daa: ldc_w "AddresseeName1530576880"
      // 1dad: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddresseeName (Ljava/lang/String;)V
      // 1db0: aload 104
      // 1db2: ldc_w "Address-1100779723"
      // 1db5: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddress (Ljava/lang/String;)V
      // 1db8: aload 104
      // 1dba: ldc_w "Telephone1085341920"
      // 1dbd: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setTelephone (Ljava/lang/String;)V
      // 1dc0: aload 104
      // 1dc2: bipush 0
      // 1dc3: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1dc6: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setConfirmInd (Ljava/lang/Boolean;)V
      // 1dc9: aload 39
      // 1dcb: aload 104
      // 1dcd: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setWrittenConfInst (Lorg/opentravel/ota/_2003/_05/WrittenConfInstType;)V
      // 1dd0: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 1dd3: dup
      // 1dd4: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 1dd7: astore 108
      // 1dd9: new java/util/ArrayList
      // 1ddc: dup
      // 1ddd: invokespecial java/util/ArrayList.<init> ()V
      // 1de0: astore 109
      // 1de2: aload 108
      // 1de4: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 1de7: aload 109
      // 1de9: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1dee: pop
      // 1def: aload 39
      // 1df1: aload 108
      // 1df3: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 1df6: aload 4
      // 1df8: aload 39
      // 1dfa: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setHotelReservations (Lorg/opentravel/ota/_2003/_05/HotelReservationsType;)V
      // 1dfd: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 1e00: dup
      // 1e01: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 1e04: astore 110
      // 1e06: new java/util/ArrayList
      // 1e09: dup
      // 1e0a: invokespecial java/util/ArrayList.<init> ()V
      // 1e0d: astore 111
      // 1e0f: aconst_null
      // 1e10: astore 112
      // 1e12: aload 111
      // 1e14: aload 112
      // 1e16: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 1e1b: pop
      // 1e1c: aload 110
      // 1e1e: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 1e21: aload 111
      // 1e23: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 1e28: pop
      // 1e29: aload 4
      // 1e2b: aload 110
      // 1e2d: invokevirtual org/htng/_2011b/HTNGHotelCheckOutNotifRQ.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 1e30: aload 3
      // 1e31: aload 4
      // 1e33: invokeinterface org/htng/_2011b/StayNotification.checkedOut (Lorg/htng/_2011b/HTNGHotelCheckOutNotifRQ;)Lorg/htng/_2011b/HTNGResponseBaseType; 2
      // 1e38: astore 113
      // 1e3a: getstatic java/lang/System.out Ljava/io/PrintStream;
      // 1e3d: new java/lang/StringBuilder
      // 1e40: dup
      // 1e41: invokespecial java/lang/StringBuilder.<init> ()V
      // 1e44: ldc_w "checkedOut.result="
      // 1e47: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1e4a: aload 113
      // 1e4c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 1e4f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1e52: invokevirtual java/io/PrintStream.println (Ljava/lang/String;)V
      // 1e55: getstatic java/lang/System.out Ljava/io/PrintStream;
      // 1e58: ldc_w "Invoking roomMoved..."
      // 1e5b: invokevirtual java/io/PrintStream.println (Ljava/lang/String;)V
      // 1e5e: new org/htng/_2011b/HTNGHotelRoomMoveNotifRQ
      // 1e61: dup
      // 1e62: invokespecial org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.<init> ()V
      // 1e65: astore 4
      // 1e67: new org/opentravel/ota/_2003/_05/POSType
      // 1e6a: dup
      // 1e6b: invokespecial org/opentravel/ota/_2003/_05/POSType.<init> ()V
      // 1e6e: astore 5
      // 1e70: new java/util/ArrayList
      // 1e73: dup
      // 1e74: invokespecial java/util/ArrayList.<init> ()V
      // 1e77: astore 6
      // 1e79: new org/opentravel/ota/_2003/_05/SourceType
      // 1e7c: dup
      // 1e7d: invokespecial org/opentravel/ota/_2003/_05/SourceType.<init> ()V
      // 1e80: astore 7
      // 1e82: new org/opentravel/ota/_2003/_05/SourceType$RequestorID
      // 1e85: dup
      // 1e86: invokespecial org/opentravel/ota/_2003/_05/SourceType$RequestorID.<init> ()V
      // 1e89: astore 8
      // 1e8b: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 1e8e: dup
      // 1e8f: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 1e92: astore 9
      // 1e94: aload 9
      // 1e96: ldc_w "Value1483270456"
      // 1e99: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 1e9c: aload 9
      // 1e9e: ldc_w "Division1158349664"
      // 1ea1: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 1ea4: aload 9
      // 1ea6: ldc_w "Department792842643"
      // 1ea9: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 1eac: aload 9
      // 1eae: ldc_w "CompanyShortName871172043"
      // 1eb1: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 1eb4: aload 9
      // 1eb6: ldc_w "TravelSector1333409235"
      // 1eb9: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 1ebc: aload 9
      // 1ebe: ldc_w "Code-1190331142"
      // 1ec1: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 1ec4: aload 9
      // 1ec6: ldc_w "CodeContext1693616322"
      // 1ec9: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 1ecc: aload 8
      // 1ece: aload 9
      // 1ed0: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 1ed3: aload 8
      // 1ed5: ldc_w "URL986720807"
      // 1ed8: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setURL (Ljava/lang/String;)V
      // 1edb: aload 8
      // 1edd: ldc_w "Type1409672265"
      // 1ee0: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setType (Ljava/lang/String;)V
      // 1ee3: aload 8
      // 1ee5: ldc_w "Instance1900899009"
      // 1ee8: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setInstance (Ljava/lang/String;)V
      // 1eeb: aload 8
      // 1eed: ldc_w "IDContext1817750359"
      // 1ef0: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setIDContext (Ljava/lang/String;)V
      // 1ef3: aload 8
      // 1ef5: ldc_w "ID-1167519372"
      // 1ef8: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setID (Ljava/lang/String;)V
      // 1efb: aload 8
      // 1efd: ldc_w "MessagePassword-203944506"
      // 1f00: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setMessagePassword (Ljava/lang/String;)V
      // 1f03: aload 7
      // 1f05: aload 8
      // 1f07: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setRequestorID (Lorg/opentravel/ota/_2003/_05/SourceType$RequestorID;)V
      // 1f0a: new org/opentravel/ota/_2003/_05/SourceType$Position
      // 1f0d: dup
      // 1f0e: invokespecial org/opentravel/ota/_2003/_05/SourceType$Position.<init> ()V
      // 1f11: astore 10
      // 1f13: aload 10
      // 1f15: ldc_w "Latitude-1449189769"
      // 1f18: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setLatitude (Ljava/lang/String;)V
      // 1f1b: aload 10
      // 1f1d: ldc_w "Longitude-965096528"
      // 1f20: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setLongitude (Ljava/lang/String;)V
      // 1f23: aload 10
      // 1f25: ldc_w "Altitude334416925"
      // 1f28: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setAltitude (Ljava/lang/String;)V
      // 1f2b: aload 10
      // 1f2d: ldc_w "AltitudeUnitOfMeasureCode-1372527344"
      // 1f30: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setAltitudeUnitOfMeasureCode (Ljava/lang/String;)V
      // 1f33: aload 10
      // 1f35: ldc_w "PositionAccuracy-1137659198"
      // 1f38: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setPositionAccuracy (Ljava/lang/String;)V
      // 1f3b: aload 7
      // 1f3d: aload 10
      // 1f3f: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setPosition (Lorg/opentravel/ota/_2003/_05/SourceType$Position;)V
      // 1f42: new org/opentravel/ota/_2003/_05/SourceType$BookingChannel
      // 1f45: dup
      // 1f46: invokespecial org/opentravel/ota/_2003/_05/SourceType$BookingChannel.<init> ()V
      // 1f49: astore 11
      // 1f4b: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 1f4e: dup
      // 1f4f: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 1f52: astore 12
      // 1f54: aload 12
      // 1f56: ldc_w "Value2008146866"
      // 1f59: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 1f5c: aload 12
      // 1f5e: ldc_w "Division-311505538"
      // 1f61: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 1f64: aload 12
      // 1f66: ldc_w "Department-1790676812"
      // 1f69: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 1f6c: aload 12
      // 1f6e: ldc_w "CompanyShortName1713998674"
      // 1f71: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 1f74: aload 12
      // 1f76: ldc_w "TravelSector-1512260572"
      // 1f79: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 1f7c: aload 12
      // 1f7e: ldc_w "Code-709663578"
      // 1f81: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 1f84: aload 12
      // 1f86: ldc_w "CodeContext-1698745213"
      // 1f89: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 1f8c: aload 11
      // 1f8e: aload 12
      // 1f90: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 1f93: aload 11
      // 1f95: ldc_w "Type1532244808"
      // 1f98: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setType (Ljava/lang/String;)V
      // 1f9b: aload 11
      // 1f9d: bipush 0
      // 1f9e: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1fa1: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setPrimary (Ljava/lang/Boolean;)V
      // 1fa4: aload 7
      // 1fa6: aload 11
      // 1fa8: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setBookingChannel (Lorg/opentravel/ota/_2003/_05/SourceType$BookingChannel;)V
      // 1fab: aload 7
      // 1fad: ldc_w "AgentSine844389213"
      // 1fb0: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAgentSine (Ljava/lang/String;)V
      // 1fb3: aload 7
      // 1fb5: ldc_w "PseudoCityCode-345539012"
      // 1fb8: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setPseudoCityCode (Ljava/lang/String;)V
      // 1fbb: aload 7
      // 1fbd: ldc_w "ISOCountry44416470"
      // 1fc0: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setISOCountry (Ljava/lang/String;)V
      // 1fc3: aload 7
      // 1fc5: ldc_w "ISOCurrency1279820910"
      // 1fc8: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setISOCurrency (Ljava/lang/String;)V
      // 1fcb: aload 7
      // 1fcd: ldc_w "AgentDutyCode1787036008"
      // 1fd0: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAgentDutyCode (Ljava/lang/String;)V
      // 1fd3: aload 7
      // 1fd5: ldc_w "AirlineVendorID-1153807990"
      // 1fd8: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAirlineVendorID (Ljava/lang/String;)V
      // 1fdb: aload 7
      // 1fdd: ldc_w "AirportCode273157410"
      // 1fe0: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAirportCode (Ljava/lang/String;)V
      // 1fe3: aload 7
      // 1fe5: ldc_w "FirstDepartPoint-1684030570"
      // 1fe8: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setFirstDepartPoint (Ljava/lang/String;)V
      // 1feb: aload 7
      // 1fed: ldc_w "ERSPUserID1636509707"
      // 1ff0: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setERSPUserID (Ljava/lang/String;)V
      // 1ff3: aload 7
      // 1ff5: ldc_w "TerminalID635301680"
      // 1ff8: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setTerminalID (Ljava/lang/String;)V
      // 1ffb: aload 6
      // 1ffd: aload 7
      // 1fff: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 2004: pop
      // 2005: aload 5
      // 2007: invokevirtual org/opentravel/ota/_2003/_05/POSType.getSource ()Ljava/util/List;
      // 200a: aload 6
      // 200c: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2011: pop
      // 2012: aload 4
      // 2014: aload 5
      // 2016: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setPOS (Lorg/opentravel/ota/_2003/_05/POSType;)V
      // 2019: new org/opentravel/ota/_2003/_05/UniqueIDType
      // 201c: dup
      // 201d: invokespecial org/opentravel/ota/_2003/_05/UniqueIDType.<init> ()V
      // 2020: astore 13
      // 2022: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 2025: dup
      // 2026: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 2029: astore 14
      // 202b: aload 14
      // 202d: ldc_w "Value-1144577169"
      // 2030: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 2033: aload 14
      // 2035: ldc_w "Division-944165582"
      // 2038: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 203b: aload 14
      // 203d: ldc_w "Department839569870"
      // 2040: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 2043: aload 14
      // 2045: ldc_w "CompanyShortName-298577022"
      // 2048: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 204b: aload 14
      // 204d: ldc_w "TravelSector-664804738"
      // 2050: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 2053: aload 14
      // 2055: ldc_w "Code1550320134"
      // 2058: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 205b: aload 14
      // 205d: ldc_w "CodeContext1329764996"
      // 2060: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 2063: aload 13
      // 2065: aload 14
      // 2067: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 206a: aload 13
      // 206c: ldc_w "URL1182238742"
      // 206f: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setURL (Ljava/lang/String;)V
      // 2072: aload 13
      // 2074: ldc_w "Type208723250"
      // 2077: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setType (Ljava/lang/String;)V
      // 207a: aload 13
      // 207c: ldc_w "Instance1326420968"
      // 207f: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setInstance (Ljava/lang/String;)V
      // 2082: aload 13
      // 2084: ldc_w "IDContext-1744364664"
      // 2087: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setIDContext (Ljava/lang/String;)V
      // 208a: aload 13
      // 208c: ldc_w "ID-1477831493"
      // 208f: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setID (Ljava/lang/String;)V
      // 2092: aload 4
      // 2094: aload 13
      // 2096: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setUniqueID (Lorg/opentravel/ota/_2003/_05/UniqueIDType;)V
      // 2099: new org/htng/_2011b/HTNGRequestBaseType$PropertyInfo
      // 209c: dup
      // 209d: invokespecial org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.<init> ()V
      // 20a0: astore 15
      // 20a2: aload 15
      // 20a4: ldc_w "ChainCode-1577836686"
      // 20a7: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setChainCode (Ljava/lang/String;)V
      // 20aa: aload 15
      // 20ac: ldc_w "BrandCode709124570"
      // 20af: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setBrandCode (Ljava/lang/String;)V
      // 20b2: aload 15
      // 20b4: ldc_w "HotelCode-536620983"
      // 20b7: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCode (Ljava/lang/String;)V
      // 20ba: aload 15
      // 20bc: ldc_w "HotelCityCode-1730061398"
      // 20bf: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCityCode (Ljava/lang/String;)V
      // 20c2: aload 15
      // 20c4: ldc_w "HotelName-420278190"
      // 20c7: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelName (Ljava/lang/String;)V
      // 20ca: aload 15
      // 20cc: ldc_w "HotelCodeContext-310398629"
      // 20cf: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCodeContext (Ljava/lang/String;)V
      // 20d2: aload 15
      // 20d4: ldc_w "ChainName597180169"
      // 20d7: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setChainName (Ljava/lang/String;)V
      // 20da: aload 15
      // 20dc: ldc_w "BrandName21056763"
      // 20df: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setBrandName (Ljava/lang/String;)V
      // 20e2: aload 15
      // 20e4: ldc_w "AreaID810799732"
      // 20e7: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setAreaID (Ljava/lang/String;)V
      // 20ea: aload 4
      // 20ec: aload 15
      // 20ee: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setPropertyInfo (Lorg/htng/_2011b/HTNGRequestBaseType$PropertyInfo;)V
      // 20f1: aload 4
      // 20f3: ldc_w "EchoToken1465610489"
      // 20f6: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setEchoToken (Ljava/lang/String;)V
      // 20f9: aload 4
      // 20fb: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 20fe: ldc_w "2020-03-24T09:54:33.667+08:00"
      // 2101: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 2104: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setTimeStamp (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2107: aload 4
      // 2109: ldc_w "Target693660129"
      // 210c: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setTarget (Ljava/lang/String;)V
      // 210f: aload 4
      // 2111: ldc_w "TargetName-77210278"
      // 2114: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setTargetName (Ljava/lang/String;)V
      // 2117: aload 4
      // 2119: new java/math/BigDecimal
      // 211c: dup
      // 211d: ldc_w "5580459854420586539.3329510357141353466"
      // 2120: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 2123: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setVersion (Ljava/math/BigDecimal;)V
      // 2126: aload 4
      // 2128: ldc_w "TransactionIdentifier2112427234"
      // 212b: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setTransactionIdentifier (Ljava/lang/String;)V
      // 212e: aload 4
      // 2130: new java/math/BigInteger
      // 2133: dup
      // 2134: ldc_w "-50214428150198171325211575401539215556"
      // 2137: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 213a: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setSequenceNmbr (Ljava/math/BigInteger;)V
      // 213d: aload 4
      // 213f: ldc_w "TransactionStatusCode2100056907"
      // 2142: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setTransactionStatusCode (Ljava/lang/String;)V
      // 2145: aload 4
      // 2147: bipush 0
      // 2148: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 214b: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setRetransmissionIndicator (Ljava/lang/Boolean;)V
      // 214e: aload 4
      // 2150: ldc_w "CorrelationID630326244"
      // 2153: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setCorrelationID (Ljava/lang/String;)V
      // 2156: aload 4
      // 2158: ldc_w "PrimaryLangID-1705901188"
      // 215b: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setPrimaryLangID (Ljava/lang/String;)V
      // 215e: aload 4
      // 2160: ldc_w "AltLangID-1004066158"
      // 2163: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setAltLangID (Ljava/lang/String;)V
      // 2166: new org/htng/_2011b/HTNGCollectionOfUniqueIDs
      // 2169: dup
      // 216a: invokespecial org/htng/_2011b/HTNGCollectionOfUniqueIDs.<init> ()V
      // 216d: astore 16
      // 216f: new org/opentravel/ota/_2003/_05/UniqueIDType
      // 2172: dup
      // 2173: invokespecial org/opentravel/ota/_2003/_05/UniqueIDType.<init> ()V
      // 2176: astore 17
      // 2178: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 217b: dup
      // 217c: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 217f: astore 18
      // 2181: aload 18
      // 2183: ldc_w "Value-1008003922"
      // 2186: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 2189: aload 18
      // 218b: ldc_w "Division-576406708"
      // 218e: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 2191: aload 18
      // 2193: ldc_w "Department-1040937306"
      // 2196: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 2199: aload 18
      // 219b: ldc_w "CompanyShortName1802316931"
      // 219e: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 21a1: aload 18
      // 21a3: ldc_w "TravelSector2037201071"
      // 21a6: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 21a9: aload 18
      // 21ab: ldc_w "Code1373213658"
      // 21ae: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 21b1: aload 18
      // 21b3: ldc_w "CodeContext946882277"
      // 21b6: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 21b9: aload 17
      // 21bb: aload 18
      // 21bd: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 21c0: aload 17
      // 21c2: ldc_w "URL-947523014"
      // 21c5: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setURL (Ljava/lang/String;)V
      // 21c8: aload 17
      // 21ca: ldc_w "Type1541342850"
      // 21cd: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setType (Ljava/lang/String;)V
      // 21d0: aload 17
      // 21d2: ldc_w "Instance1845553264"
      // 21d5: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setInstance (Ljava/lang/String;)V
      // 21d8: aload 17
      // 21da: ldc_w "IDContext1806692270"
      // 21dd: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setIDContext (Ljava/lang/String;)V
      // 21e0: aload 17
      // 21e2: ldc_w "ID171385583"
      // 21e5: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setID (Ljava/lang/String;)V
      // 21e8: aload 16
      // 21ea: aload 17
      // 21ec: invokevirtual org/htng/_2011b/HTNGCollectionOfUniqueIDs.setUniqueID (Lorg/opentravel/ota/_2003/_05/UniqueIDType;)V
      // 21ef: aload 4
      // 21f1: aload 16
      // 21f3: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setAffectedGuests (Lorg/htng/_2011b/HTNGCollectionOfUniqueIDs;)V
      // 21f6: new org/htng/_2011b/HTNGHotelRoomMoveNotifRQ$SourceRoomInformation
      // 21f9: dup
      // 21fa: invokespecial org/htng/_2011b/HTNGHotelRoomMoveNotifRQ$SourceRoomInformation.<init> ()V
      // 21fd: astore 19
      // 21ff: new org/htng/_2011b/HTNGRoomElementType
      // 2202: dup
      // 2203: invokespecial org/htng/_2011b/HTNGRoomElementType.<init> ()V
      // 2206: astore 20
      // 2208: new org/opentravel/ota/_2003/_05/RoomTypeType
      // 220b: dup
      // 220c: invokespecial org/opentravel/ota/_2003/_05/RoomTypeType.<init> ()V
      // 220f: astore 21
      // 2211: new org/opentravel/ota/_2003/_05/ParagraphType
      // 2214: dup
      // 2215: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 2218: astore 22
      // 221a: new java/util/ArrayList
      // 221d: dup
      // 221e: invokespecial java/util/ArrayList.<init> ()V
      // 2221: astore 23
      // 2223: aload 22
      // 2225: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 2228: aload 23
      // 222a: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 222f: pop
      // 2230: aload 22
      // 2232: ldc_w "Name-714425190"
      // 2235: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 2238: aload 22
      // 223a: new java/math/BigInteger
      // 223d: dup
      // 223e: ldc_w "26582523638511810932060061980583188892"
      // 2241: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 2244: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 2247: aload 22
      // 2249: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 224c: ldc_w "2020-03-24T09:54:33.668+08:00"
      // 224f: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 2252: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2255: aload 22
      // 2257: ldc_w "CreatorID619985522"
      // 225a: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 225d: aload 22
      // 225f: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 2262: ldc_w "2020-03-24T09:54:33.668+08:00"
      // 2265: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 2268: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 226b: aload 22
      // 226d: ldc_w "LastModifierID-1385749568"
      // 2270: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 2273: aload 22
      // 2275: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 2278: ldc_w "2020-03-24T09:54:33.669+08:00"
      // 227b: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 227e: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2281: aload 22
      // 2283: ldc_w "Language1203947902"
      // 2286: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 2289: aload 21
      // 228b: aload 22
      // 228d: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomDescription (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 2290: new org/opentravel/ota/_2003/_05/AdditionalDetailsType
      // 2293: dup
      // 2294: invokespecial org/opentravel/ota/_2003/_05/AdditionalDetailsType.<init> ()V
      // 2297: astore 24
      // 2299: new java/util/ArrayList
      // 229c: dup
      // 229d: invokespecial java/util/ArrayList.<init> ()V
      // 22a0: astore 25
      // 22a2: aload 24
      // 22a4: invokevirtual org/opentravel/ota/_2003/_05/AdditionalDetailsType.getAdditionalDetail ()Ljava/util/List;
      // 22a7: aload 25
      // 22a9: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 22ae: pop
      // 22af: aload 21
      // 22b1: aload 24
      // 22b3: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setAdditionalDetails (Lorg/opentravel/ota/_2003/_05/AdditionalDetailsType;)V
      // 22b6: new org/opentravel/ota/_2003/_05/RoomTypeType$Amenities
      // 22b9: dup
      // 22ba: invokespecial org/opentravel/ota/_2003/_05/RoomTypeType$Amenities.<init> ()V
      // 22bd: astore 26
      // 22bf: new java/util/ArrayList
      // 22c2: dup
      // 22c3: invokespecial java/util/ArrayList.<init> ()V
      // 22c6: astore 27
      // 22c8: aload 26
      // 22ca: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType$Amenities.getAmenity ()Ljava/util/List;
      // 22cd: aload 27
      // 22cf: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 22d4: pop
      // 22d5: aload 21
      // 22d7: aload 26
      // 22d9: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setAmenities (Lorg/opentravel/ota/_2003/_05/RoomTypeType$Amenities;)V
      // 22dc: new java/util/ArrayList
      // 22df: dup
      // 22e0: invokespecial java/util/ArrayList.<init> ()V
      // 22e3: astore 28
      // 22e5: aload 21
      // 22e7: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getOccupancy ()Ljava/util/List;
      // 22ea: aload 28
      // 22ec: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 22f1: pop
      // 22f2: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 22f5: dup
      // 22f6: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 22f9: astore 29
      // 22fb: new java/util/ArrayList
      // 22fe: dup
      // 22ff: invokespecial java/util/ArrayList.<init> ()V
      // 2302: astore 30
      // 2304: aload 29
      // 2306: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 2309: aload 30
      // 230b: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2310: pop
      // 2311: aload 21
      // 2313: aload 29
      // 2315: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 2318: aload 21
      // 231a: new java/math/BigInteger
      // 231d: dup
      // 231e: ldc_w "21238821160505139691004217872296635441"
      // 2321: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 2324: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setNumberOfUnits (Ljava/math/BigInteger;)V
      // 2327: aload 21
      // 2329: bipush 1
      // 232a: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 232d: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsRoom (Ljava/lang/Boolean;)V
      // 2330: aload 21
      // 2332: bipush 0
      // 2333: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 2336: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsConverted (Ljava/lang/Boolean;)V
      // 2339: aload 21
      // 233b: bipush 0
      // 233c: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 233f: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsAlternate (Ljava/lang/Boolean;)V
      // 2342: aload 21
      // 2344: ldc_w "ReqdGuaranteeType228590963"
      // 2347: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setReqdGuaranteeType (Ljava/lang/String;)V
      // 234a: aload 21
      // 234c: ldc_w "RoomType720405763"
      // 234f: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomType (Ljava/lang/String;)V
      // 2352: aload 21
      // 2354: ldc_w "RoomTypeCode872417815"
      // 2357: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomTypeCode (Ljava/lang/String;)V
      // 235a: aload 21
      // 235c: ldc_w "RoomCategory-1567176382"
      // 235f: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomCategory (Ljava/lang/String;)V
      // 2362: aload 21
      // 2364: ldc_w "RoomID48721379"
      // 2367: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomID (Ljava/lang/String;)V
      // 236a: aload 21
      // 236c: ldc_w 459209404
      // 236f: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 2372: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setFloor (Ljava/lang/Integer;)V
      // 2375: aload 21
      // 2377: ldc_w "InvBlockCode1972346512"
      // 237a: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setInvBlockCode (Ljava/lang/String;)V
      // 237d: aload 21
      // 237f: ldc_w "RoomLocationCode-1954259237"
      // 2382: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomLocationCode (Ljava/lang/String;)V
      // 2385: aload 21
      // 2387: ldc_w "RoomViewCode-102333208"
      // 238a: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomViewCode (Ljava/lang/String;)V
      // 238d: new java/util/ArrayList
      // 2390: dup
      // 2391: invokespecial java/util/ArrayList.<init> ()V
      // 2394: astore 31
      // 2396: aload 21
      // 2398: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getBedTypeCode ()Ljava/util/List;
      // 239b: aload 31
      // 239d: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 23a2: pop
      // 23a3: aload 21
      // 23a5: bipush 1
      // 23a6: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 23a9: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setNonSmoking (Ljava/lang/Boolean;)V
      // 23ac: aload 21
      // 23ae: ldc_w "Configuration180790291"
      // 23b1: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setConfiguration (Ljava/lang/String;)V
      // 23b4: aload 21
      // 23b6: ldc_w "SizeMeasurement374934946"
      // 23b9: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setSizeMeasurement (Ljava/lang/String;)V
      // 23bc: aload 21
      // 23be: ldc_w -1228860744
      // 23c1: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 23c4: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setQuantity (Ljava/lang/Integer;)V
      // 23c7: aload 21
      // 23c9: bipush 1
      // 23ca: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 23cd: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setComposite (Ljava/lang/Boolean;)V
      // 23d0: aload 21
      // 23d2: ldc_w "RoomClassificationCode-1306801363"
      // 23d5: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomClassificationCode (Ljava/lang/String;)V
      // 23d8: aload 21
      // 23da: ldc_w "RoomArchitectureCode931188333"
      // 23dd: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomArchitectureCode (Ljava/lang/String;)V
      // 23e0: aload 21
      // 23e2: ldc_w "RoomGender-124877498"
      // 23e5: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomGender (Ljava/lang/String;)V
      // 23e8: aload 21
      // 23ea: bipush 0
      // 23eb: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 23ee: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setSharedRoomInd (Ljava/lang/Boolean;)V
      // 23f1: aload 21
      // 23f3: ldc_w "PromotionCode-731503733"
      // 23f6: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setPromotionCode (Ljava/lang/String;)V
      // 23f9: new java/util/ArrayList
      // 23fc: dup
      // 23fd: invokespecial java/util/ArrayList.<init> ()V
      // 2400: astore 32
      // 2402: aload 21
      // 2404: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getPromotionVendorCode ()Ljava/util/List;
      // 2407: aload 32
      // 2409: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 240e: pop
      // 240f: aload 20
      // 2411: aload 21
      // 2413: invokevirtual org/htng/_2011b/HTNGRoomElementType.setRoomType (Lorg/opentravel/ota/_2003/_05/RoomTypeType;)V
      // 2416: new org/htng/_2011b/HTNGTelephoneExtensionType
      // 2419: dup
      // 241a: invokespecial org/htng/_2011b/HTNGTelephoneExtensionType.<init> ()V
      // 241d: astore 33
      // 241f: new java/util/ArrayList
      // 2422: dup
      // 2423: invokespecial java/util/ArrayList.<init> ()V
      // 2426: astore 34
      // 2428: aload 33
      // 242a: invokevirtual org/htng/_2011b/HTNGTelephoneExtensionType.getTelephoneExtention ()Ljava/util/List;
      // 242d: aload 34
      // 242f: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2434: pop
      // 2435: aload 20
      // 2437: aload 33
      // 2439: invokevirtual org/htng/_2011b/HTNGRoomElementType.setTelephoneExtensions (Lorg/htng/_2011b/HTNGTelephoneExtensionType;)V
      // 243c: getstatic org/htng/_2011b/HTNGHousekeepingStatusType.NEEDS_INSPECTION Lorg/htng/_2011b/HTNGHousekeepingStatusType;
      // 243f: astore 35
      // 2441: aload 20
      // 2443: aload 35
      // 2445: invokevirtual org/htng/_2011b/HTNGRoomElementType.setHKStatus (Lorg/htng/_2011b/HTNGHousekeepingStatusType;)V
      // 2448: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 244b: dup
      // 244c: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 244f: astore 36
      // 2451: new java/util/ArrayList
      // 2454: dup
      // 2455: invokespecial java/util/ArrayList.<init> ()V
      // 2458: astore 37
      // 245a: aload 36
      // 245c: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 245f: aload 37
      // 2461: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2466: pop
      // 2467: aload 20
      // 2469: aload 36
      // 246b: invokevirtual org/htng/_2011b/HTNGRoomElementType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 246e: aload 20
      // 2470: ldc_w "RoomID-933460266"
      // 2473: invokevirtual org/htng/_2011b/HTNGRoomElementType.setRoomID (Ljava/lang/String;)V
      // 2476: new org/htng/_2011b/HTNGComponentRoomsType
      // 2479: dup
      // 247a: invokespecial org/htng/_2011b/HTNGComponentRoomsType.<init> ()V
      // 247d: astore 38
      // 247f: new java/util/ArrayList
      // 2482: dup
      // 2483: invokespecial java/util/ArrayList.<init> ()V
      // 2486: astore 39
      // 2488: aload 38
      // 248a: invokevirtual org/htng/_2011b/HTNGComponentRoomsType.getComponentRoom ()Ljava/util/List;
      // 248d: aload 39
      // 248f: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2494: pop
      // 2495: aload 20
      // 2497: aload 38
      // 2499: invokevirtual org/htng/_2011b/HTNGRoomElementType.setComponentRooms (Lorg/htng/_2011b/HTNGComponentRoomsType;)V
      // 249c: aload 19
      // 249e: aload 20
      // 24a0: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ$SourceRoomInformation.setRoom (Lorg/htng/_2011b/HTNGRoomElementType;)V
      // 24a3: new org/opentravel/ota/_2003/_05/HotelReservationsType
      // 24a6: dup
      // 24a7: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType.<init> ()V
      // 24aa: astore 40
      // 24ac: new java/util/ArrayList
      // 24af: dup
      // 24b0: invokespecial java/util/ArrayList.<init> ()V
      // 24b3: astore 41
      // 24b5: aload 40
      // 24b7: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.getHotelReservation ()Ljava/util/List;
      // 24ba: aload 41
      // 24bc: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 24c1: pop
      // 24c2: new org/opentravel/ota/_2003/_05/RoutingHopType
      // 24c5: dup
      // 24c6: invokespecial org/opentravel/ota/_2003/_05/RoutingHopType.<init> ()V
      // 24c9: astore 42
      // 24cb: new java/util/ArrayList
      // 24ce: dup
      // 24cf: invokespecial java/util/ArrayList.<init> ()V
      // 24d2: astore 43
      // 24d4: aload 42
      // 24d6: invokevirtual org/opentravel/ota/_2003/_05/RoutingHopType.getRoutingHop ()Ljava/util/List;
      // 24d9: aload 43
      // 24db: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 24e0: pop
      // 24e1: aload 40
      // 24e3: aload 42
      // 24e5: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setRoutingHops (Lorg/opentravel/ota/_2003/_05/RoutingHopType;)V
      // 24e8: new org/opentravel/ota/_2003/_05/WrittenConfInstType
      // 24eb: dup
      // 24ec: invokespecial org/opentravel/ota/_2003/_05/WrittenConfInstType.<init> ()V
      // 24ef: astore 44
      // 24f1: new org/opentravel/ota/_2003/_05/ParagraphType
      // 24f4: dup
      // 24f5: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 24f8: astore 45
      // 24fa: new java/util/ArrayList
      // 24fd: dup
      // 24fe: invokespecial java/util/ArrayList.<init> ()V
      // 2501: astore 46
      // 2503: aload 45
      // 2505: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 2508: aload 46
      // 250a: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 250f: pop
      // 2510: aload 45
      // 2512: ldc_w "Name1545817518"
      // 2515: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 2518: aload 45
      // 251a: new java/math/BigInteger
      // 251d: dup
      // 251e: ldc_w "-81924274149725913356605630355640484144"
      // 2521: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 2524: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 2527: aload 45
      // 2529: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 252c: ldc_w "2020-03-24T09:54:33.670+08:00"
      // 252f: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 2532: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2535: aload 45
      // 2537: ldc_w "CreatorID-163366442"
      // 253a: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 253d: aload 45
      // 253f: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 2542: ldc_w "2020-03-24T09:54:33.671+08:00"
      // 2545: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 2548: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 254b: aload 45
      // 254d: ldc_w "LastModifierID-1512846209"
      // 2550: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 2553: aload 45
      // 2555: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 2558: ldc_w "2020-03-24T09:54:33.671+08:00"
      // 255b: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 255e: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2561: aload 45
      // 2563: ldc_w "Language-1901068844"
      // 2566: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 2569: aload 44
      // 256b: aload 45
      // 256d: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setSupplementalData (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 2570: new org/opentravel/ota/_2003/_05/EmailType
      // 2573: dup
      // 2574: invokespecial org/opentravel/ota/_2003/_05/EmailType.<init> ()V
      // 2577: astore 47
      // 2579: aload 47
      // 257b: ldc_w "Value-1288376799"
      // 257e: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setValue (Ljava/lang/String;)V
      // 2581: aload 47
      // 2583: ldc_w "EmailType-1320985778"
      // 2586: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setEmailType (Ljava/lang/String;)V
      // 2589: aload 47
      // 258b: ldc_w "RPH-153123075"
      // 258e: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRPH (Ljava/lang/String;)V
      // 2591: aload 47
      // 2593: ldc_w "Remark1854251541"
      // 2596: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRemark (Ljava/lang/String;)V
      // 2599: aload 47
      // 259b: bipush 0
      // 259c: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 259f: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setDefaultInd (Ljava/lang/Boolean;)V
      // 25a2: aload 47
      // 25a4: ldc_w "ShareSynchInd-1179783486"
      // 25a7: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareSynchInd (Ljava/lang/String;)V
      // 25aa: aload 47
      // 25ac: ldc_w "ShareMarketInd-1095920808"
      // 25af: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareMarketInd (Ljava/lang/String;)V
      // 25b2: aload 44
      // 25b4: aload 47
      // 25b6: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setEmail (Lorg/opentravel/ota/_2003/_05/EmailType;)V
      // 25b9: aload 44
      // 25bb: ldc_w "LanguageID152611041"
      // 25be: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setLanguageID (Ljava/lang/String;)V
      // 25c1: aload 44
      // 25c3: ldc_w "AddresseeName194497397"
      // 25c6: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddresseeName (Ljava/lang/String;)V
      // 25c9: aload 44
      // 25cb: ldc_w "Address29023498"
      // 25ce: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddress (Ljava/lang/String;)V
      // 25d1: aload 44
      // 25d3: ldc_w "Telephone-160883238"
      // 25d6: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setTelephone (Ljava/lang/String;)V
      // 25d9: aload 44
      // 25db: bipush 0
      // 25dc: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 25df: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setConfirmInd (Ljava/lang/Boolean;)V
      // 25e2: aload 40
      // 25e4: aload 44
      // 25e6: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setWrittenConfInst (Lorg/opentravel/ota/_2003/_05/WrittenConfInstType;)V
      // 25e9: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 25ec: dup
      // 25ed: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 25f0: astore 48
      // 25f2: new java/util/ArrayList
      // 25f5: dup
      // 25f6: invokespecial java/util/ArrayList.<init> ()V
      // 25f9: astore 49
      // 25fb: aload 48
      // 25fd: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 2600: aload 49
      // 2602: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2607: pop
      // 2608: aload 40
      // 260a: aload 48
      // 260c: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 260f: aload 19
      // 2611: aload 40
      // 2613: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ$SourceRoomInformation.setHotelReservations (Lorg/opentravel/ota/_2003/_05/HotelReservationsType;)V
      // 2616: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 2619: dup
      // 261a: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 261d: astore 50
      // 261f: new java/util/ArrayList
      // 2622: dup
      // 2623: invokespecial java/util/ArrayList.<init> ()V
      // 2626: astore 51
      // 2628: aload 50
      // 262a: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 262d: aload 51
      // 262f: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2634: pop
      // 2635: aload 19
      // 2637: aload 50
      // 2639: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ$SourceRoomInformation.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 263c: aload 4
      // 263e: aload 19
      // 2640: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setSourceRoomInformation (Lorg/htng/_2011b/HTNGHotelRoomMoveNotifRQ$SourceRoomInformation;)V
      // 2643: new org/htng/_2011b/HTNGHotelRoomMoveNotifRQ$DestinationRoomInformation
      // 2646: dup
      // 2647: invokespecial org/htng/_2011b/HTNGHotelRoomMoveNotifRQ$DestinationRoomInformation.<init> ()V
      // 264a: astore 52
      // 264c: new org/htng/_2011b/HTNGRoomElementType
      // 264f: dup
      // 2650: invokespecial org/htng/_2011b/HTNGRoomElementType.<init> ()V
      // 2653: astore 53
      // 2655: new org/opentravel/ota/_2003/_05/RoomTypeType
      // 2658: dup
      // 2659: invokespecial org/opentravel/ota/_2003/_05/RoomTypeType.<init> ()V
      // 265c: astore 54
      // 265e: new org/opentravel/ota/_2003/_05/ParagraphType
      // 2661: dup
      // 2662: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 2665: astore 55
      // 2667: new java/util/ArrayList
      // 266a: dup
      // 266b: invokespecial java/util/ArrayList.<init> ()V
      // 266e: astore 56
      // 2670: aload 55
      // 2672: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 2675: aload 56
      // 2677: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 267c: pop
      // 267d: aload 55
      // 267f: ldc_w "Name-1505911470"
      // 2682: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 2685: aload 55
      // 2687: new java/math/BigInteger
      // 268a: dup
      // 268b: ldc_w "91339982990867978748511052597226487898"
      // 268e: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 2691: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 2694: aload 55
      // 2696: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 2699: ldc_w "2020-03-24T09:54:33.672+08:00"
      // 269c: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 269f: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 26a2: aload 55
      // 26a4: ldc_w "CreatorID-2044728309"
      // 26a7: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 26aa: aload 55
      // 26ac: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 26af: ldc_w "2020-03-24T09:54:33.672+08:00"
      // 26b2: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 26b5: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 26b8: aload 55
      // 26ba: ldc_w "LastModifierID-1020955240"
      // 26bd: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 26c0: aload 55
      // 26c2: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 26c5: ldc_w "2020-03-24T09:54:33.672+08:00"
      // 26c8: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 26cb: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 26ce: aload 55
      // 26d0: ldc_w "Language-1272209205"
      // 26d3: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 26d6: aload 54
      // 26d8: aload 55
      // 26da: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomDescription (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 26dd: new org/opentravel/ota/_2003/_05/AdditionalDetailsType
      // 26e0: dup
      // 26e1: invokespecial org/opentravel/ota/_2003/_05/AdditionalDetailsType.<init> ()V
      // 26e4: astore 57
      // 26e6: new java/util/ArrayList
      // 26e9: dup
      // 26ea: invokespecial java/util/ArrayList.<init> ()V
      // 26ed: astore 58
      // 26ef: aload 57
      // 26f1: invokevirtual org/opentravel/ota/_2003/_05/AdditionalDetailsType.getAdditionalDetail ()Ljava/util/List;
      // 26f4: aload 58
      // 26f6: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 26fb: pop
      // 26fc: aload 54
      // 26fe: aload 57
      // 2700: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setAdditionalDetails (Lorg/opentravel/ota/_2003/_05/AdditionalDetailsType;)V
      // 2703: new org/opentravel/ota/_2003/_05/RoomTypeType$Amenities
      // 2706: dup
      // 2707: invokespecial org/opentravel/ota/_2003/_05/RoomTypeType$Amenities.<init> ()V
      // 270a: astore 59
      // 270c: new java/util/ArrayList
      // 270f: dup
      // 2710: invokespecial java/util/ArrayList.<init> ()V
      // 2713: astore 60
      // 2715: aload 59
      // 2717: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType$Amenities.getAmenity ()Ljava/util/List;
      // 271a: aload 60
      // 271c: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2721: pop
      // 2722: aload 54
      // 2724: aload 59
      // 2726: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setAmenities (Lorg/opentravel/ota/_2003/_05/RoomTypeType$Amenities;)V
      // 2729: new java/util/ArrayList
      // 272c: dup
      // 272d: invokespecial java/util/ArrayList.<init> ()V
      // 2730: astore 61
      // 2732: aload 54
      // 2734: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getOccupancy ()Ljava/util/List;
      // 2737: aload 61
      // 2739: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 273e: pop
      // 273f: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 2742: dup
      // 2743: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 2746: astore 62
      // 2748: new java/util/ArrayList
      // 274b: dup
      // 274c: invokespecial java/util/ArrayList.<init> ()V
      // 274f: astore 63
      // 2751: aload 62
      // 2753: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 2756: aload 63
      // 2758: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 275d: pop
      // 275e: aload 54
      // 2760: aload 62
      // 2762: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 2765: aload 54
      // 2767: new java/math/BigInteger
      // 276a: dup
      // 276b: ldc_w "70521866592040353143789261439986195138"
      // 276e: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 2771: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setNumberOfUnits (Ljava/math/BigInteger;)V
      // 2774: aload 54
      // 2776: bipush 1
      // 2777: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 277a: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsRoom (Ljava/lang/Boolean;)V
      // 277d: aload 54
      // 277f: bipush 1
      // 2780: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 2783: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsConverted (Ljava/lang/Boolean;)V
      // 2786: aload 54
      // 2788: bipush 1
      // 2789: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 278c: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsAlternate (Ljava/lang/Boolean;)V
      // 278f: aload 54
      // 2791: ldc_w "ReqdGuaranteeType-55837050"
      // 2794: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setReqdGuaranteeType (Ljava/lang/String;)V
      // 2797: aload 54
      // 2799: ldc_w "RoomType260846152"
      // 279c: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomType (Ljava/lang/String;)V
      // 279f: aload 54
      // 27a1: ldc_w "RoomTypeCode656130195"
      // 27a4: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomTypeCode (Ljava/lang/String;)V
      // 27a7: aload 54
      // 27a9: ldc_w "RoomCategory1124044139"
      // 27ac: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomCategory (Ljava/lang/String;)V
      // 27af: aload 54
      // 27b1: ldc_w "RoomID-1800086955"
      // 27b4: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomID (Ljava/lang/String;)V
      // 27b7: aload 54
      // 27b9: ldc_w -1279535819
      // 27bc: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 27bf: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setFloor (Ljava/lang/Integer;)V
      // 27c2: aload 54
      // 27c4: ldc_w "InvBlockCode311520906"
      // 27c7: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setInvBlockCode (Ljava/lang/String;)V
      // 27ca: aload 54
      // 27cc: ldc_w "RoomLocationCode1601144064"
      // 27cf: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomLocationCode (Ljava/lang/String;)V
      // 27d2: aload 54
      // 27d4: ldc_w "RoomViewCode-1616520298"
      // 27d7: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomViewCode (Ljava/lang/String;)V
      // 27da: new java/util/ArrayList
      // 27dd: dup
      // 27de: invokespecial java/util/ArrayList.<init> ()V
      // 27e1: astore 64
      // 27e3: aload 54
      // 27e5: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getBedTypeCode ()Ljava/util/List;
      // 27e8: aload 64
      // 27ea: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 27ef: pop
      // 27f0: aload 54
      // 27f2: bipush 0
      // 27f3: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 27f6: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setNonSmoking (Ljava/lang/Boolean;)V
      // 27f9: aload 54
      // 27fb: ldc_w "Configuration2112155279"
      // 27fe: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setConfiguration (Ljava/lang/String;)V
      // 2801: aload 54
      // 2803: ldc_w "SizeMeasurement807675207"
      // 2806: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setSizeMeasurement (Ljava/lang/String;)V
      // 2809: aload 54
      // 280b: ldc_w 1327713397
      // 280e: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 2811: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setQuantity (Ljava/lang/Integer;)V
      // 2814: aload 54
      // 2816: bipush 1
      // 2817: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 281a: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setComposite (Ljava/lang/Boolean;)V
      // 281d: aload 54
      // 281f: ldc_w "RoomClassificationCode1885722081"
      // 2822: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomClassificationCode (Ljava/lang/String;)V
      // 2825: aload 54
      // 2827: ldc_w "RoomArchitectureCode-872661077"
      // 282a: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomArchitectureCode (Ljava/lang/String;)V
      // 282d: aload 54
      // 282f: ldc_w "RoomGender1287214769"
      // 2832: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomGender (Ljava/lang/String;)V
      // 2835: aload 54
      // 2837: bipush 1
      // 2838: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 283b: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setSharedRoomInd (Ljava/lang/Boolean;)V
      // 283e: aload 54
      // 2840: ldc_w "PromotionCode-147701130"
      // 2843: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setPromotionCode (Ljava/lang/String;)V
      // 2846: new java/util/ArrayList
      // 2849: dup
      // 284a: invokespecial java/util/ArrayList.<init> ()V
      // 284d: astore 65
      // 284f: aload 54
      // 2851: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getPromotionVendorCode ()Ljava/util/List;
      // 2854: aload 65
      // 2856: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 285b: pop
      // 285c: aload 53
      // 285e: aload 54
      // 2860: invokevirtual org/htng/_2011b/HTNGRoomElementType.setRoomType (Lorg/opentravel/ota/_2003/_05/RoomTypeType;)V
      // 2863: new org/htng/_2011b/HTNGTelephoneExtensionType
      // 2866: dup
      // 2867: invokespecial org/htng/_2011b/HTNGTelephoneExtensionType.<init> ()V
      // 286a: astore 66
      // 286c: new java/util/ArrayList
      // 286f: dup
      // 2870: invokespecial java/util/ArrayList.<init> ()V
      // 2873: astore 67
      // 2875: aload 66
      // 2877: invokevirtual org/htng/_2011b/HTNGTelephoneExtensionType.getTelephoneExtention ()Ljava/util/List;
      // 287a: aload 67
      // 287c: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2881: pop
      // 2882: aload 53
      // 2884: aload 66
      // 2886: invokevirtual org/htng/_2011b/HTNGRoomElementType.setTelephoneExtensions (Lorg/htng/_2011b/HTNGTelephoneExtensionType;)V
      // 2889: getstatic org/htng/_2011b/HTNGHousekeepingStatusType.OUT_OF_ORDER Lorg/htng/_2011b/HTNGHousekeepingStatusType;
      // 288c: astore 68
      // 288e: aload 53
      // 2890: aload 68
      // 2892: invokevirtual org/htng/_2011b/HTNGRoomElementType.setHKStatus (Lorg/htng/_2011b/HTNGHousekeepingStatusType;)V
      // 2895: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 2898: dup
      // 2899: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 289c: astore 69
      // 289e: new java/util/ArrayList
      // 28a1: dup
      // 28a2: invokespecial java/util/ArrayList.<init> ()V
      // 28a5: astore 70
      // 28a7: aload 69
      // 28a9: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 28ac: aload 70
      // 28ae: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 28b3: pop
      // 28b4: aload 53
      // 28b6: aload 69
      // 28b8: invokevirtual org/htng/_2011b/HTNGRoomElementType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 28bb: aload 53
      // 28bd: ldc_w "RoomID-759603276"
      // 28c0: invokevirtual org/htng/_2011b/HTNGRoomElementType.setRoomID (Ljava/lang/String;)V
      // 28c3: new org/htng/_2011b/HTNGComponentRoomsType
      // 28c6: dup
      // 28c7: invokespecial org/htng/_2011b/HTNGComponentRoomsType.<init> ()V
      // 28ca: astore 71
      // 28cc: new java/util/ArrayList
      // 28cf: dup
      // 28d0: invokespecial java/util/ArrayList.<init> ()V
      // 28d3: astore 72
      // 28d5: aload 71
      // 28d7: invokevirtual org/htng/_2011b/HTNGComponentRoomsType.getComponentRoom ()Ljava/util/List;
      // 28da: aload 72
      // 28dc: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 28e1: pop
      // 28e2: aload 53
      // 28e4: aload 71
      // 28e6: invokevirtual org/htng/_2011b/HTNGRoomElementType.setComponentRooms (Lorg/htng/_2011b/HTNGComponentRoomsType;)V
      // 28e9: aload 52
      // 28eb: aload 53
      // 28ed: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ$DestinationRoomInformation.setRoom (Lorg/htng/_2011b/HTNGRoomElementType;)V
      // 28f0: new org/opentravel/ota/_2003/_05/HotelReservationsType
      // 28f3: dup
      // 28f4: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType.<init> ()V
      // 28f7: astore 73
      // 28f9: new java/util/ArrayList
      // 28fc: dup
      // 28fd: invokespecial java/util/ArrayList.<init> ()V
      // 2900: astore 74
      // 2902: aload 73
      // 2904: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.getHotelReservation ()Ljava/util/List;
      // 2907: aload 74
      // 2909: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 290e: pop
      // 290f: new org/opentravel/ota/_2003/_05/RoutingHopType
      // 2912: dup
      // 2913: invokespecial org/opentravel/ota/_2003/_05/RoutingHopType.<init> ()V
      // 2916: astore 75
      // 2918: new java/util/ArrayList
      // 291b: dup
      // 291c: invokespecial java/util/ArrayList.<init> ()V
      // 291f: astore 76
      // 2921: aload 75
      // 2923: invokevirtual org/opentravel/ota/_2003/_05/RoutingHopType.getRoutingHop ()Ljava/util/List;
      // 2926: aload 76
      // 2928: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 292d: pop
      // 292e: aload 73
      // 2930: aload 75
      // 2932: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setRoutingHops (Lorg/opentravel/ota/_2003/_05/RoutingHopType;)V
      // 2935: new org/opentravel/ota/_2003/_05/WrittenConfInstType
      // 2938: dup
      // 2939: invokespecial org/opentravel/ota/_2003/_05/WrittenConfInstType.<init> ()V
      // 293c: astore 77
      // 293e: new org/opentravel/ota/_2003/_05/ParagraphType
      // 2941: dup
      // 2942: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 2945: astore 78
      // 2947: new java/util/ArrayList
      // 294a: dup
      // 294b: invokespecial java/util/ArrayList.<init> ()V
      // 294e: astore 79
      // 2950: aload 78
      // 2952: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 2955: aload 79
      // 2957: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 295c: pop
      // 295d: aload 78
      // 295f: ldc_w "Name-473700470"
      // 2962: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 2965: aload 78
      // 2967: new java/math/BigInteger
      // 296a: dup
      // 296b: ldc_w "-77027307554761066977527606373553086669"
      // 296e: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 2971: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 2974: aload 78
      // 2976: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 2979: ldc_w "2020-03-24T09:54:33.674+08:00"
      // 297c: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 297f: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2982: aload 78
      // 2984: ldc_w "CreatorID1723082574"
      // 2987: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 298a: aload 78
      // 298c: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 298f: ldc_w "2020-03-24T09:54:33.674+08:00"
      // 2992: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 2995: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2998: aload 78
      // 299a: ldc_w "LastModifierID-1409690717"
      // 299d: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 29a0: aload 78
      // 29a2: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 29a5: ldc_w "2020-03-24T09:54:33.675+08:00"
      // 29a8: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 29ab: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 29ae: aload 78
      // 29b0: ldc_w "Language960309047"
      // 29b3: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 29b6: aload 77
      // 29b8: aload 78
      // 29ba: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setSupplementalData (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 29bd: new org/opentravel/ota/_2003/_05/EmailType
      // 29c0: dup
      // 29c1: invokespecial org/opentravel/ota/_2003/_05/EmailType.<init> ()V
      // 29c4: astore 80
      // 29c6: aload 80
      // 29c8: ldc_w "Value-672734280"
      // 29cb: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setValue (Ljava/lang/String;)V
      // 29ce: aload 80
      // 29d0: ldc_w "EmailType1917351204"
      // 29d3: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setEmailType (Ljava/lang/String;)V
      // 29d6: aload 80
      // 29d8: ldc_w "RPH1340331197"
      // 29db: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRPH (Ljava/lang/String;)V
      // 29de: aload 80
      // 29e0: ldc_w "Remark1596803340"
      // 29e3: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRemark (Ljava/lang/String;)V
      // 29e6: aload 80
      // 29e8: bipush 0
      // 29e9: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 29ec: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setDefaultInd (Ljava/lang/Boolean;)V
      // 29ef: aload 80
      // 29f1: ldc_w "ShareSynchInd-5543922"
      // 29f4: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareSynchInd (Ljava/lang/String;)V
      // 29f7: aload 80
      // 29f9: ldc_w "ShareMarketInd-1999147402"
      // 29fc: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareMarketInd (Ljava/lang/String;)V
      // 29ff: aload 77
      // 2a01: aload 80
      // 2a03: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setEmail (Lorg/opentravel/ota/_2003/_05/EmailType;)V
      // 2a06: aload 77
      // 2a08: ldc_w "LanguageID-34891463"
      // 2a0b: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setLanguageID (Ljava/lang/String;)V
      // 2a0e: aload 77
      // 2a10: ldc_w "AddresseeName617814643"
      // 2a13: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddresseeName (Ljava/lang/String;)V
      // 2a16: aload 77
      // 2a18: ldc_w "Address-810861150"
      // 2a1b: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddress (Ljava/lang/String;)V
      // 2a1e: aload 77
      // 2a20: ldc_w "Telephone-980829060"
      // 2a23: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setTelephone (Ljava/lang/String;)V
      // 2a26: aload 77
      // 2a28: bipush 0
      // 2a29: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 2a2c: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setConfirmInd (Ljava/lang/Boolean;)V
      // 2a2f: aload 73
      // 2a31: aload 77
      // 2a33: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setWrittenConfInst (Lorg/opentravel/ota/_2003/_05/WrittenConfInstType;)V
      // 2a36: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 2a39: dup
      // 2a3a: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 2a3d: astore 81
      // 2a3f: new java/util/ArrayList
      // 2a42: dup
      // 2a43: invokespecial java/util/ArrayList.<init> ()V
      // 2a46: astore 82
      // 2a48: aload 81
      // 2a4a: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 2a4d: aload 82
      // 2a4f: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2a54: pop
      // 2a55: aload 73
      // 2a57: aload 81
      // 2a59: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 2a5c: aload 52
      // 2a5e: aload 73
      // 2a60: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ$DestinationRoomInformation.setHotelReservations (Lorg/opentravel/ota/_2003/_05/HotelReservationsType;)V
      // 2a63: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 2a66: dup
      // 2a67: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 2a6a: astore 83
      // 2a6c: new java/util/ArrayList
      // 2a6f: dup
      // 2a70: invokespecial java/util/ArrayList.<init> ()V
      // 2a73: astore 84
      // 2a75: aload 83
      // 2a77: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 2a7a: aload 84
      // 2a7c: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2a81: pop
      // 2a82: aload 52
      // 2a84: aload 83
      // 2a86: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ$DestinationRoomInformation.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 2a89: aload 4
      // 2a8b: aload 52
      // 2a8d: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setDestinationRoomInformation (Lorg/htng/_2011b/HTNGHotelRoomMoveNotifRQ$DestinationRoomInformation;)V
      // 2a90: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 2a93: dup
      // 2a94: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 2a97: astore 85
      // 2a99: new java/util/ArrayList
      // 2a9c: dup
      // 2a9d: invokespecial java/util/ArrayList.<init> ()V
      // 2aa0: astore 86
      // 2aa2: aconst_null
      // 2aa3: astore 87
      // 2aa5: aload 86
      // 2aa7: aload 87
      // 2aa9: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 2aae: pop
      // 2aaf: aload 85
      // 2ab1: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 2ab4: aload 86
      // 2ab6: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2abb: pop
      // 2abc: aload 4
      // 2abe: aload 85
      // 2ac0: invokevirtual org/htng/_2011b/HTNGHotelRoomMoveNotifRQ.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 2ac3: aload 3
      // 2ac4: aload 4
      // 2ac6: invokeinterface org/htng/_2011b/StayNotification.roomMoved (Lorg/htng/_2011b/HTNGHotelRoomMoveNotifRQ;)Lorg/htng/_2011b/HTNGResponseBaseType; 2
      // 2acb: astore 88
      // 2acd: getstatic java/lang/System.out Ljava/io/PrintStream;
      // 2ad0: new java/lang/StringBuilder
      // 2ad3: dup
      // 2ad4: invokespecial java/lang/StringBuilder.<init> ()V
      // 2ad7: ldc_w "roomMoved.result="
      // 2ada: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2add: aload 88
      // 2adf: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 2ae2: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 2ae5: invokevirtual java/io/PrintStream.println (Ljava/lang/String;)V
      // 2ae8: getstatic java/lang/System.out Ljava/io/PrintStream;
      // 2aeb: ldc_w "Invoking stayUpdated..."
      // 2aee: invokevirtual java/io/PrintStream.println (Ljava/lang/String;)V
      // 2af1: new org/htng/_2011b/HTNGHotelStayUpdateNotifRQ
      // 2af4: dup
      // 2af5: invokespecial org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.<init> ()V
      // 2af8: astore 4
      // 2afa: new org/opentravel/ota/_2003/_05/POSType
      // 2afd: dup
      // 2afe: invokespecial org/opentravel/ota/_2003/_05/POSType.<init> ()V
      // 2b01: astore 5
      // 2b03: new java/util/ArrayList
      // 2b06: dup
      // 2b07: invokespecial java/util/ArrayList.<init> ()V
      // 2b0a: astore 6
      // 2b0c: new org/opentravel/ota/_2003/_05/SourceType
      // 2b0f: dup
      // 2b10: invokespecial org/opentravel/ota/_2003/_05/SourceType.<init> ()V
      // 2b13: astore 7
      // 2b15: new org/opentravel/ota/_2003/_05/SourceType$RequestorID
      // 2b18: dup
      // 2b19: invokespecial org/opentravel/ota/_2003/_05/SourceType$RequestorID.<init> ()V
      // 2b1c: astore 8
      // 2b1e: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 2b21: dup
      // 2b22: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 2b25: astore 9
      // 2b27: aload 9
      // 2b29: ldc_w "Value-117338679"
      // 2b2c: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 2b2f: aload 9
      // 2b31: ldc_w "Division1557248548"
      // 2b34: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 2b37: aload 9
      // 2b39: ldc_w "Department1738057367"
      // 2b3c: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 2b3f: aload 9
      // 2b41: ldc_w "CompanyShortName1094868358"
      // 2b44: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 2b47: aload 9
      // 2b49: ldc_w "TravelSector-1491387385"
      // 2b4c: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 2b4f: aload 9
      // 2b51: ldc_w "Code1794546750"
      // 2b54: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 2b57: aload 9
      // 2b59: ldc_w "CodeContext986307669"
      // 2b5c: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 2b5f: aload 8
      // 2b61: aload 9
      // 2b63: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 2b66: aload 8
      // 2b68: ldc_w "URL-664415515"
      // 2b6b: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setURL (Ljava/lang/String;)V
      // 2b6e: aload 8
      // 2b70: ldc_w "Type-1273093052"
      // 2b73: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setType (Ljava/lang/String;)V
      // 2b76: aload 8
      // 2b78: ldc_w "Instance2010716144"
      // 2b7b: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setInstance (Ljava/lang/String;)V
      // 2b7e: aload 8
      // 2b80: ldc_w "IDContext1003185898"
      // 2b83: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setIDContext (Ljava/lang/String;)V
      // 2b86: aload 8
      // 2b88: ldc_w "ID902097920"
      // 2b8b: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setID (Ljava/lang/String;)V
      // 2b8e: aload 8
      // 2b90: ldc_w "MessagePassword1261275463"
      // 2b93: invokevirtual org/opentravel/ota/_2003/_05/SourceType$RequestorID.setMessagePassword (Ljava/lang/String;)V
      // 2b96: aload 7
      // 2b98: aload 8
      // 2b9a: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setRequestorID (Lorg/opentravel/ota/_2003/_05/SourceType$RequestorID;)V
      // 2b9d: new org/opentravel/ota/_2003/_05/SourceType$Position
      // 2ba0: dup
      // 2ba1: invokespecial org/opentravel/ota/_2003/_05/SourceType$Position.<init> ()V
      // 2ba4: astore 10
      // 2ba6: aload 10
      // 2ba8: ldc_w "Latitude-1209955131"
      // 2bab: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setLatitude (Ljava/lang/String;)V
      // 2bae: aload 10
      // 2bb0: ldc_w "Longitude1347482960"
      // 2bb3: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setLongitude (Ljava/lang/String;)V
      // 2bb6: aload 10
      // 2bb8: ldc_w "Altitude548260088"
      // 2bbb: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setAltitude (Ljava/lang/String;)V
      // 2bbe: aload 10
      // 2bc0: ldc_w "AltitudeUnitOfMeasureCode-1005611956"
      // 2bc3: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setAltitudeUnitOfMeasureCode (Ljava/lang/String;)V
      // 2bc6: aload 10
      // 2bc8: ldc_w "PositionAccuracy1049357127"
      // 2bcb: invokevirtual org/opentravel/ota/_2003/_05/SourceType$Position.setPositionAccuracy (Ljava/lang/String;)V
      // 2bce: aload 7
      // 2bd0: aload 10
      // 2bd2: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setPosition (Lorg/opentravel/ota/_2003/_05/SourceType$Position;)V
      // 2bd5: new org/opentravel/ota/_2003/_05/SourceType$BookingChannel
      // 2bd8: dup
      // 2bd9: invokespecial org/opentravel/ota/_2003/_05/SourceType$BookingChannel.<init> ()V
      // 2bdc: astore 11
      // 2bde: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 2be1: dup
      // 2be2: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 2be5: astore 12
      // 2be7: aload 12
      // 2be9: ldc_w "Value846677686"
      // 2bec: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 2bef: aload 12
      // 2bf1: ldc_w "Division2077686558"
      // 2bf4: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 2bf7: aload 12
      // 2bf9: ldc_w "Department1184620551"
      // 2bfc: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 2bff: aload 12
      // 2c01: ldc_w "CompanyShortName-2062647884"
      // 2c04: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 2c07: aload 12
      // 2c09: ldc_w "TravelSector-1140238569"
      // 2c0c: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 2c0f: aload 12
      // 2c11: ldc_w "Code-685842744"
      // 2c14: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 2c17: aload 12
      // 2c19: ldc_w "CodeContext1801007081"
      // 2c1c: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 2c1f: aload 11
      // 2c21: aload 12
      // 2c23: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 2c26: aload 11
      // 2c28: ldc_w "Type1792542704"
      // 2c2b: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setType (Ljava/lang/String;)V
      // 2c2e: aload 11
      // 2c30: bipush 0
      // 2c31: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 2c34: invokevirtual org/opentravel/ota/_2003/_05/SourceType$BookingChannel.setPrimary (Ljava/lang/Boolean;)V
      // 2c37: aload 7
      // 2c39: aload 11
      // 2c3b: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setBookingChannel (Lorg/opentravel/ota/_2003/_05/SourceType$BookingChannel;)V
      // 2c3e: aload 7
      // 2c40: ldc_w "AgentSine470171240"
      // 2c43: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAgentSine (Ljava/lang/String;)V
      // 2c46: aload 7
      // 2c48: ldc_w "PseudoCityCode-2002200671"
      // 2c4b: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setPseudoCityCode (Ljava/lang/String;)V
      // 2c4e: aload 7
      // 2c50: ldc_w "ISOCountry-172264770"
      // 2c53: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setISOCountry (Ljava/lang/String;)V
      // 2c56: aload 7
      // 2c58: ldc_w "ISOCurrency-1508339594"
      // 2c5b: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setISOCurrency (Ljava/lang/String;)V
      // 2c5e: aload 7
      // 2c60: ldc_w "AgentDutyCode-674655526"
      // 2c63: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAgentDutyCode (Ljava/lang/String;)V
      // 2c66: aload 7
      // 2c68: ldc_w "AirlineVendorID348296933"
      // 2c6b: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAirlineVendorID (Ljava/lang/String;)V
      // 2c6e: aload 7
      // 2c70: ldc_w "AirportCode-498174422"
      // 2c73: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setAirportCode (Ljava/lang/String;)V
      // 2c76: aload 7
      // 2c78: ldc_w "FirstDepartPoint1757127375"
      // 2c7b: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setFirstDepartPoint (Ljava/lang/String;)V
      // 2c7e: aload 7
      // 2c80: ldc_w "ERSPUserID-1604487940"
      // 2c83: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setERSPUserID (Ljava/lang/String;)V
      // 2c86: aload 7
      // 2c88: ldc_w "TerminalID2141973873"
      // 2c8b: invokevirtual org/opentravel/ota/_2003/_05/SourceType.setTerminalID (Ljava/lang/String;)V
      // 2c8e: aload 6
      // 2c90: aload 7
      // 2c92: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 2c97: pop
      // 2c98: aload 5
      // 2c9a: invokevirtual org/opentravel/ota/_2003/_05/POSType.getSource ()Ljava/util/List;
      // 2c9d: aload 6
      // 2c9f: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2ca4: pop
      // 2ca5: aload 4
      // 2ca7: aload 5
      // 2ca9: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setPOS (Lorg/opentravel/ota/_2003/_05/POSType;)V
      // 2cac: new org/opentravel/ota/_2003/_05/UniqueIDType
      // 2caf: dup
      // 2cb0: invokespecial org/opentravel/ota/_2003/_05/UniqueIDType.<init> ()V
      // 2cb3: astore 13
      // 2cb5: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 2cb8: dup
      // 2cb9: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 2cbc: astore 14
      // 2cbe: aload 14
      // 2cc0: ldc_w "Value-1822165175"
      // 2cc3: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 2cc6: aload 14
      // 2cc8: ldc_w "Division951234529"
      // 2ccb: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 2cce: aload 14
      // 2cd0: ldc_w "Department-926411645"
      // 2cd3: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 2cd6: aload 14
      // 2cd8: ldc_w "CompanyShortName-1048433841"
      // 2cdb: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 2cde: aload 14
      // 2ce0: ldc_w "TravelSector685903141"
      // 2ce3: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 2ce6: aload 14
      // 2ce8: ldc_w "Code107833377"
      // 2ceb: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 2cee: aload 14
      // 2cf0: ldc_w "CodeContext-2036676596"
      // 2cf3: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 2cf6: aload 13
      // 2cf8: aload 14
      // 2cfa: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 2cfd: aload 13
      // 2cff: ldc_w "URL-1323384487"
      // 2d02: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setURL (Ljava/lang/String;)V
      // 2d05: aload 13
      // 2d07: ldc_w "Type-1245781775"
      // 2d0a: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setType (Ljava/lang/String;)V
      // 2d0d: aload 13
      // 2d0f: ldc_w "Instance837096430"
      // 2d12: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setInstance (Ljava/lang/String;)V
      // 2d15: aload 13
      // 2d17: ldc_w "IDContext-1695806863"
      // 2d1a: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setIDContext (Ljava/lang/String;)V
      // 2d1d: aload 13
      // 2d1f: ldc_w "ID-242117124"
      // 2d22: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setID (Ljava/lang/String;)V
      // 2d25: aload 4
      // 2d27: aload 13
      // 2d29: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setUniqueID (Lorg/opentravel/ota/_2003/_05/UniqueIDType;)V
      // 2d2c: new org/htng/_2011b/HTNGRequestBaseType$PropertyInfo
      // 2d2f: dup
      // 2d30: invokespecial org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.<init> ()V
      // 2d33: astore 15
      // 2d35: aload 15
      // 2d37: ldc_w "ChainCode1643044228"
      // 2d3a: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setChainCode (Ljava/lang/String;)V
      // 2d3d: aload 15
      // 2d3f: ldc_w "BrandCode1385758709"
      // 2d42: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setBrandCode (Ljava/lang/String;)V
      // 2d45: aload 15
      // 2d47: ldc_w "HotelCode-429688482"
      // 2d4a: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCode (Ljava/lang/String;)V
      // 2d4d: aload 15
      // 2d4f: ldc_w "HotelCityCode-899325378"
      // 2d52: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCityCode (Ljava/lang/String;)V
      // 2d55: aload 15
      // 2d57: ldc_w "HotelName583131652"
      // 2d5a: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelName (Ljava/lang/String;)V
      // 2d5d: aload 15
      // 2d5f: ldc_w "HotelCodeContext1220201430"
      // 2d62: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setHotelCodeContext (Ljava/lang/String;)V
      // 2d65: aload 15
      // 2d67: ldc_w "ChainName1055828259"
      // 2d6a: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setChainName (Ljava/lang/String;)V
      // 2d6d: aload 15
      // 2d6f: ldc_w "BrandName-1151444223"
      // 2d72: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setBrandName (Ljava/lang/String;)V
      // 2d75: aload 15
      // 2d77: ldc_w "AreaID-237736991"
      // 2d7a: invokevirtual org/htng/_2011b/HTNGRequestBaseType$PropertyInfo.setAreaID (Ljava/lang/String;)V
      // 2d7d: aload 4
      // 2d7f: aload 15
      // 2d81: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setPropertyInfo (Lorg/htng/_2011b/HTNGRequestBaseType$PropertyInfo;)V
      // 2d84: aload 4
      // 2d86: ldc_w "EchoToken-1837203705"
      // 2d89: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setEchoToken (Ljava/lang/String;)V
      // 2d8c: aload 4
      // 2d8e: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 2d91: ldc_w "2020-03-24T09:54:33.677+08:00"
      // 2d94: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 2d97: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setTimeStamp (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2d9a: aload 4
      // 2d9c: ldc_w "Target-1345396278"
      // 2d9f: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setTarget (Ljava/lang/String;)V
      // 2da2: aload 4
      // 2da4: ldc_w "TargetName-1063015800"
      // 2da7: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setTargetName (Ljava/lang/String;)V
      // 2daa: aload 4
      // 2dac: new java/math/BigDecimal
      // 2daf: dup
      // 2db0: ldc_w "-7812359946265393159.6981395893622112710"
      // 2db3: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 2db6: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setVersion (Ljava/math/BigDecimal;)V
      // 2db9: aload 4
      // 2dbb: ldc_w "TransactionIdentifier296532809"
      // 2dbe: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setTransactionIdentifier (Ljava/lang/String;)V
      // 2dc1: aload 4
      // 2dc3: new java/math/BigInteger
      // 2dc6: dup
      // 2dc7: ldc_w "62921020418652077445634474436684898858"
      // 2dca: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 2dcd: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setSequenceNmbr (Ljava/math/BigInteger;)V
      // 2dd0: aload 4
      // 2dd2: ldc_w "TransactionStatusCode-1643242921"
      // 2dd5: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setTransactionStatusCode (Ljava/lang/String;)V
      // 2dd8: aload 4
      // 2dda: bipush 0
      // 2ddb: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 2dde: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setRetransmissionIndicator (Ljava/lang/Boolean;)V
      // 2de1: aload 4
      // 2de3: ldc_w "CorrelationID-2019477561"
      // 2de6: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setCorrelationID (Ljava/lang/String;)V
      // 2de9: aload 4
      // 2deb: ldc_w "PrimaryLangID-1914034244"
      // 2dee: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setPrimaryLangID (Ljava/lang/String;)V
      // 2df1: aload 4
      // 2df3: ldc_w "AltLangID-406791349"
      // 2df6: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setAltLangID (Ljava/lang/String;)V
      // 2df9: new org/htng/_2011b/HTNGCollectionOfUniqueIDs
      // 2dfc: dup
      // 2dfd: invokespecial org/htng/_2011b/HTNGCollectionOfUniqueIDs.<init> ()V
      // 2e00: astore 16
      // 2e02: new org/opentravel/ota/_2003/_05/UniqueIDType
      // 2e05: dup
      // 2e06: invokespecial org/opentravel/ota/_2003/_05/UniqueIDType.<init> ()V
      // 2e09: astore 17
      // 2e0b: new org/opentravel/ota/_2003/_05/CompanyNameType
      // 2e0e: dup
      // 2e0f: invokespecial org/opentravel/ota/_2003/_05/CompanyNameType.<init> ()V
      // 2e12: astore 18
      // 2e14: aload 18
      // 2e16: ldc_w "Value567989696"
      // 2e19: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setValue (Ljava/lang/String;)V
      // 2e1c: aload 18
      // 2e1e: ldc_w "Division-1669255288"
      // 2e21: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDivision (Ljava/lang/String;)V
      // 2e24: aload 18
      // 2e26: ldc_w "Department-1575412770"
      // 2e29: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setDepartment (Ljava/lang/String;)V
      // 2e2c: aload 18
      // 2e2e: ldc_w "CompanyShortName-181696745"
      // 2e31: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCompanyShortName (Ljava/lang/String;)V
      // 2e34: aload 18
      // 2e36: ldc_w "TravelSector764833463"
      // 2e39: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setTravelSector (Ljava/lang/String;)V
      // 2e3c: aload 18
      // 2e3e: ldc_w "Code2115745930"
      // 2e41: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCode (Ljava/lang/String;)V
      // 2e44: aload 18
      // 2e46: ldc_w "CodeContext2011566254"
      // 2e49: invokevirtual org/opentravel/ota/_2003/_05/CompanyNameType.setCodeContext (Ljava/lang/String;)V
      // 2e4c: aload 17
      // 2e4e: aload 18
      // 2e50: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setCompanyName (Lorg/opentravel/ota/_2003/_05/CompanyNameType;)V
      // 2e53: aload 17
      // 2e55: ldc_w "URL887021801"
      // 2e58: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setURL (Ljava/lang/String;)V
      // 2e5b: aload 17
      // 2e5d: ldc_w "Type1968560351"
      // 2e60: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setType (Ljava/lang/String;)V
      // 2e63: aload 17
      // 2e65: ldc_w "Instance830880547"
      // 2e68: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setInstance (Ljava/lang/String;)V
      // 2e6b: aload 17
      // 2e6d: ldc_w "IDContext231290687"
      // 2e70: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setIDContext (Ljava/lang/String;)V
      // 2e73: aload 17
      // 2e75: ldc_w "ID377988356"
      // 2e78: invokevirtual org/opentravel/ota/_2003/_05/UniqueIDType.setID (Ljava/lang/String;)V
      // 2e7b: aload 16
      // 2e7d: aload 17
      // 2e7f: invokevirtual org/htng/_2011b/HTNGCollectionOfUniqueIDs.setUniqueID (Lorg/opentravel/ota/_2003/_05/UniqueIDType;)V
      // 2e82: aload 4
      // 2e84: aload 16
      // 2e86: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setAffectedGuests (Lorg/htng/_2011b/HTNGCollectionOfUniqueIDs;)V
      // 2e89: new org/htng/_2011b/HTNGRoomElementType
      // 2e8c: dup
      // 2e8d: invokespecial org/htng/_2011b/HTNGRoomElementType.<init> ()V
      // 2e90: astore 19
      // 2e92: new org/opentravel/ota/_2003/_05/RoomTypeType
      // 2e95: dup
      // 2e96: invokespecial org/opentravel/ota/_2003/_05/RoomTypeType.<init> ()V
      // 2e99: astore 20
      // 2e9b: new org/opentravel/ota/_2003/_05/ParagraphType
      // 2e9e: dup
      // 2e9f: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 2ea2: astore 21
      // 2ea4: new java/util/ArrayList
      // 2ea7: dup
      // 2ea8: invokespecial java/util/ArrayList.<init> ()V
      // 2eab: astore 22
      // 2ead: aload 21
      // 2eaf: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 2eb2: aload 22
      // 2eb4: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2eb9: pop
      // 2eba: aload 21
      // 2ebc: ldc_w "Name1669555588"
      // 2ebf: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 2ec2: aload 21
      // 2ec4: new java/math/BigInteger
      // 2ec7: dup
      // 2ec8: ldc_w "-89435873797947833518575170771393859768"
      // 2ecb: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 2ece: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 2ed1: aload 21
      // 2ed3: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 2ed6: ldc_w "2020-03-24T09:54:33.678+08:00"
      // 2ed9: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 2edc: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2edf: aload 21
      // 2ee1: ldc_w "CreatorID1207219441"
      // 2ee4: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 2ee7: aload 21
      // 2ee9: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 2eec: ldc_w "2020-03-24T09:54:33.678+08:00"
      // 2eef: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 2ef2: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2ef5: aload 21
      // 2ef7: ldc_w "LastModifierID-706954335"
      // 2efa: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 2efd: aload 21
      // 2eff: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 2f02: ldc_w "2020-03-24T09:54:33.679+08:00"
      // 2f05: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 2f08: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 2f0b: aload 21
      // 2f0d: ldc_w "Language1422806538"
      // 2f10: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 2f13: aload 20
      // 2f15: aload 21
      // 2f17: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomDescription (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 2f1a: new org/opentravel/ota/_2003/_05/AdditionalDetailsType
      // 2f1d: dup
      // 2f1e: invokespecial org/opentravel/ota/_2003/_05/AdditionalDetailsType.<init> ()V
      // 2f21: astore 23
      // 2f23: new java/util/ArrayList
      // 2f26: dup
      // 2f27: invokespecial java/util/ArrayList.<init> ()V
      // 2f2a: astore 24
      // 2f2c: aload 23
      // 2f2e: invokevirtual org/opentravel/ota/_2003/_05/AdditionalDetailsType.getAdditionalDetail ()Ljava/util/List;
      // 2f31: aload 24
      // 2f33: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2f38: pop
      // 2f39: aload 20
      // 2f3b: aload 23
      // 2f3d: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setAdditionalDetails (Lorg/opentravel/ota/_2003/_05/AdditionalDetailsType;)V
      // 2f40: new org/opentravel/ota/_2003/_05/RoomTypeType$Amenities
      // 2f43: dup
      // 2f44: invokespecial org/opentravel/ota/_2003/_05/RoomTypeType$Amenities.<init> ()V
      // 2f47: astore 25
      // 2f49: new java/util/ArrayList
      // 2f4c: dup
      // 2f4d: invokespecial java/util/ArrayList.<init> ()V
      // 2f50: astore 26
      // 2f52: aload 25
      // 2f54: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType$Amenities.getAmenity ()Ljava/util/List;
      // 2f57: aload 26
      // 2f59: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2f5e: pop
      // 2f5f: aload 20
      // 2f61: aload 25
      // 2f63: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setAmenities (Lorg/opentravel/ota/_2003/_05/RoomTypeType$Amenities;)V
      // 2f66: new java/util/ArrayList
      // 2f69: dup
      // 2f6a: invokespecial java/util/ArrayList.<init> ()V
      // 2f6d: astore 27
      // 2f6f: aload 20
      // 2f71: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getOccupancy ()Ljava/util/List;
      // 2f74: aload 27
      // 2f76: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2f7b: pop
      // 2f7c: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 2f7f: dup
      // 2f80: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 2f83: astore 28
      // 2f85: new java/util/ArrayList
      // 2f88: dup
      // 2f89: invokespecial java/util/ArrayList.<init> ()V
      // 2f8c: astore 29
      // 2f8e: aload 28
      // 2f90: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 2f93: aload 29
      // 2f95: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 2f9a: pop
      // 2f9b: aload 20
      // 2f9d: aload 28
      // 2f9f: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 2fa2: aload 20
      // 2fa4: new java/math/BigInteger
      // 2fa7: dup
      // 2fa8: ldc_w "84125289379255895222702276149950075367"
      // 2fab: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 2fae: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setNumberOfUnits (Ljava/math/BigInteger;)V
      // 2fb1: aload 20
      // 2fb3: bipush 0
      // 2fb4: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 2fb7: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsRoom (Ljava/lang/Boolean;)V
      // 2fba: aload 20
      // 2fbc: bipush 1
      // 2fbd: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 2fc0: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsConverted (Ljava/lang/Boolean;)V
      // 2fc3: aload 20
      // 2fc5: bipush 1
      // 2fc6: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 2fc9: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setIsAlternate (Ljava/lang/Boolean;)V
      // 2fcc: aload 20
      // 2fce: ldc_w "ReqdGuaranteeType2043420606"
      // 2fd1: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setReqdGuaranteeType (Ljava/lang/String;)V
      // 2fd4: aload 20
      // 2fd6: ldc_w "RoomType-1116730977"
      // 2fd9: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomType (Ljava/lang/String;)V
      // 2fdc: aload 20
      // 2fde: ldc_w "RoomTypeCode-1441447694"
      // 2fe1: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomTypeCode (Ljava/lang/String;)V
      // 2fe4: aload 20
      // 2fe6: ldc_w "RoomCategory168452446"
      // 2fe9: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomCategory (Ljava/lang/String;)V
      // 2fec: aload 20
      // 2fee: ldc_w "RoomID1486159088"
      // 2ff1: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomID (Ljava/lang/String;)V
      // 2ff4: aload 20
      // 2ff6: ldc_w 1703152386
      // 2ff9: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 2ffc: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setFloor (Ljava/lang/Integer;)V
      // 2fff: aload 20
      // 3001: ldc_w "InvBlockCode95549340"
      // 3004: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setInvBlockCode (Ljava/lang/String;)V
      // 3007: aload 20
      // 3009: ldc_w "RoomLocationCode1461594546"
      // 300c: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomLocationCode (Ljava/lang/String;)V
      // 300f: aload 20
      // 3011: ldc_w "RoomViewCode649972692"
      // 3014: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomViewCode (Ljava/lang/String;)V
      // 3017: new java/util/ArrayList
      // 301a: dup
      // 301b: invokespecial java/util/ArrayList.<init> ()V
      // 301e: astore 30
      // 3020: aload 20
      // 3022: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getBedTypeCode ()Ljava/util/List;
      // 3025: aload 30
      // 3027: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 302c: pop
      // 302d: aload 20
      // 302f: bipush 1
      // 3030: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 3033: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setNonSmoking (Ljava/lang/Boolean;)V
      // 3036: aload 20
      // 3038: ldc_w "Configuration-1769696195"
      // 303b: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setConfiguration (Ljava/lang/String;)V
      // 303e: aload 20
      // 3040: ldc_w "SizeMeasurement922815342"
      // 3043: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setSizeMeasurement (Ljava/lang/String;)V
      // 3046: aload 20
      // 3048: ldc_w 1514155802
      // 304b: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 304e: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setQuantity (Ljava/lang/Integer;)V
      // 3051: aload 20
      // 3053: bipush 0
      // 3054: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 3057: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setComposite (Ljava/lang/Boolean;)V
      // 305a: aload 20
      // 305c: ldc_w "RoomClassificationCode-1121894528"
      // 305f: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomClassificationCode (Ljava/lang/String;)V
      // 3062: aload 20
      // 3064: ldc_w "RoomArchitectureCode-444283252"
      // 3067: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomArchitectureCode (Ljava/lang/String;)V
      // 306a: aload 20
      // 306c: ldc_w "RoomGender-1542915930"
      // 306f: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setRoomGender (Ljava/lang/String;)V
      // 3072: aload 20
      // 3074: bipush 1
      // 3075: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 3078: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setSharedRoomInd (Ljava/lang/Boolean;)V
      // 307b: aload 20
      // 307d: ldc_w "PromotionCode-1886751702"
      // 3080: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.setPromotionCode (Ljava/lang/String;)V
      // 3083: new java/util/ArrayList
      // 3086: dup
      // 3087: invokespecial java/util/ArrayList.<init> ()V
      // 308a: astore 31
      // 308c: aload 20
      // 308e: invokevirtual org/opentravel/ota/_2003/_05/RoomTypeType.getPromotionVendorCode ()Ljava/util/List;
      // 3091: aload 31
      // 3093: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3098: pop
      // 3099: aload 19
      // 309b: aload 20
      // 309d: invokevirtual org/htng/_2011b/HTNGRoomElementType.setRoomType (Lorg/opentravel/ota/_2003/_05/RoomTypeType;)V
      // 30a0: new org/htng/_2011b/HTNGTelephoneExtensionType
      // 30a3: dup
      // 30a4: invokespecial org/htng/_2011b/HTNGTelephoneExtensionType.<init> ()V
      // 30a7: astore 32
      // 30a9: new java/util/ArrayList
      // 30ac: dup
      // 30ad: invokespecial java/util/ArrayList.<init> ()V
      // 30b0: astore 33
      // 30b2: aload 32
      // 30b4: invokevirtual org/htng/_2011b/HTNGTelephoneExtensionType.getTelephoneExtention ()Ljava/util/List;
      // 30b7: aload 33
      // 30b9: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 30be: pop
      // 30bf: aload 19
      // 30c1: aload 32
      // 30c3: invokevirtual org/htng/_2011b/HTNGRoomElementType.setTelephoneExtensions (Lorg/htng/_2011b/HTNGTelephoneExtensionType;)V
      // 30c6: getstatic org/htng/_2011b/HTNGHousekeepingStatusType.OFF_MARKET Lorg/htng/_2011b/HTNGHousekeepingStatusType;
      // 30c9: astore 34
      // 30cb: aload 19
      // 30cd: aload 34
      // 30cf: invokevirtual org/htng/_2011b/HTNGRoomElementType.setHKStatus (Lorg/htng/_2011b/HTNGHousekeepingStatusType;)V
      // 30d2: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 30d5: dup
      // 30d6: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 30d9: astore 35
      // 30db: new java/util/ArrayList
      // 30de: dup
      // 30df: invokespecial java/util/ArrayList.<init> ()V
      // 30e2: astore 36
      // 30e4: aload 35
      // 30e6: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 30e9: aload 36
      // 30eb: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 30f0: pop
      // 30f1: aload 19
      // 30f3: aload 35
      // 30f5: invokevirtual org/htng/_2011b/HTNGRoomElementType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 30f8: aload 19
      // 30fa: ldc_w "RoomID370262643"
      // 30fd: invokevirtual org/htng/_2011b/HTNGRoomElementType.setRoomID (Ljava/lang/String;)V
      // 3100: new org/htng/_2011b/HTNGComponentRoomsType
      // 3103: dup
      // 3104: invokespecial org/htng/_2011b/HTNGComponentRoomsType.<init> ()V
      // 3107: astore 37
      // 3109: new java/util/ArrayList
      // 310c: dup
      // 310d: invokespecial java/util/ArrayList.<init> ()V
      // 3110: astore 38
      // 3112: aload 37
      // 3114: invokevirtual org/htng/_2011b/HTNGComponentRoomsType.getComponentRoom ()Ljava/util/List;
      // 3117: aload 38
      // 3119: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 311e: pop
      // 311f: aload 19
      // 3121: aload 37
      // 3123: invokevirtual org/htng/_2011b/HTNGRoomElementType.setComponentRooms (Lorg/htng/_2011b/HTNGComponentRoomsType;)V
      // 3126: aload 4
      // 3128: aload 19
      // 312a: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setRoom (Lorg/htng/_2011b/HTNGRoomElementType;)V
      // 312d: new org/opentravel/ota/_2003/_05/HotelReservationsType
      // 3130: dup
      // 3131: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType.<init> ()V
      // 3134: astore 39
      // 3136: new java/util/ArrayList
      // 3139: dup
      // 313a: invokespecial java/util/ArrayList.<init> ()V
      // 313d: astore 40
      // 313f: new org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation
      // 3142: dup
      // 3143: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.<init> ()V
      // 3146: astore 41
      // 3148: new org/opentravel/ota/_2003/_05/POSType
      // 314b: dup
      // 314c: invokespecial org/opentravel/ota/_2003/_05/POSType.<init> ()V
      // 314f: astore 42
      // 3151: new java/util/ArrayList
      // 3154: dup
      // 3155: invokespecial java/util/ArrayList.<init> ()V
      // 3158: astore 43
      // 315a: aload 42
      // 315c: invokevirtual org/opentravel/ota/_2003/_05/POSType.getSource ()Ljava/util/List;
      // 315f: aload 43
      // 3161: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3166: pop
      // 3167: aload 41
      // 3169: aload 42
      // 316b: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setPOS (Lorg/opentravel/ota/_2003/_05/POSType;)V
      // 316e: new java/util/ArrayList
      // 3171: dup
      // 3172: invokespecial java/util/ArrayList.<init> ()V
      // 3175: astore 44
      // 3177: aload 41
      // 3179: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.getUniqueID ()Ljava/util/List;
      // 317c: aload 44
      // 317e: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3183: pop
      // 3184: new org/opentravel/ota/_2003/_05/RoomStaysType
      // 3187: dup
      // 3188: invokespecial org/opentravel/ota/_2003/_05/RoomStaysType.<init> ()V
      // 318b: astore 45
      // 318d: new java/util/ArrayList
      // 3190: dup
      // 3191: invokespecial java/util/ArrayList.<init> ()V
      // 3194: astore 46
      // 3196: aload 45
      // 3198: invokevirtual org/opentravel/ota/_2003/_05/RoomStaysType.getRoomStay ()Ljava/util/List;
      // 319b: aload 46
      // 319d: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 31a2: pop
      // 31a3: aload 41
      // 31a5: aload 45
      // 31a7: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRoomStays (Lorg/opentravel/ota/_2003/_05/RoomStaysType;)V
      // 31aa: new org/opentravel/ota/_2003/_05/ServicesType
      // 31ad: dup
      // 31ae: invokespecial org/opentravel/ota/_2003/_05/ServicesType.<init> ()V
      // 31b1: astore 47
      // 31b3: new java/util/ArrayList
      // 31b6: dup
      // 31b7: invokespecial java/util/ArrayList.<init> ()V
      // 31ba: astore 48
      // 31bc: aload 47
      // 31be: invokevirtual org/opentravel/ota/_2003/_05/ServicesType.getService ()Ljava/util/List;
      // 31c1: aload 48
      // 31c3: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 31c8: pop
      // 31c9: aload 41
      // 31cb: aload 47
      // 31cd: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setServices (Lorg/opentravel/ota/_2003/_05/ServicesType;)V
      // 31d0: new java/util/ArrayList
      // 31d3: dup
      // 31d4: invokespecial java/util/ArrayList.<init> ()V
      // 31d7: astore 49
      // 31d9: aload 41
      // 31db: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.getBillingInstructionCode ()Ljava/util/List;
      // 31de: aload 49
      // 31e0: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 31e5: pop
      // 31e6: new org/opentravel/ota/_2003/_05/ResGuestsType
      // 31e9: dup
      // 31ea: invokespecial org/opentravel/ota/_2003/_05/ResGuestsType.<init> ()V
      // 31ed: astore 50
      // 31ef: new java/util/ArrayList
      // 31f2: dup
      // 31f3: invokespecial java/util/ArrayList.<init> ()V
      // 31f6: astore 51
      // 31f8: aload 50
      // 31fa: invokevirtual org/opentravel/ota/_2003/_05/ResGuestsType.getResGuest ()Ljava/util/List;
      // 31fd: aload 51
      // 31ff: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3204: pop
      // 3205: aload 41
      // 3207: aload 50
      // 3209: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setResGuests (Lorg/opentravel/ota/_2003/_05/ResGuestsType;)V
      // 320c: new org/opentravel/ota/_2003/_05/ResGlobalInfoType
      // 320f: dup
      // 3210: invokespecial org/opentravel/ota/_2003/_05/ResGlobalInfoType.<init> ()V
      // 3213: astore 52
      // 3215: new org/opentravel/ota/_2003/_05/GuestCountType
      // 3218: dup
      // 3219: invokespecial org/opentravel/ota/_2003/_05/GuestCountType.<init> ()V
      // 321c: astore 53
      // 321e: new java/util/ArrayList
      // 3221: dup
      // 3222: invokespecial java/util/ArrayList.<init> ()V
      // 3225: astore 54
      // 3227: aload 53
      // 3229: invokevirtual org/opentravel/ota/_2003/_05/GuestCountType.getGuestCount ()Ljava/util/List;
      // 322c: aload 54
      // 322e: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3233: pop
      // 3234: aload 53
      // 3236: bipush 1
      // 3237: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 323a: invokevirtual org/opentravel/ota/_2003/_05/GuestCountType.setIsPerRoom (Ljava/lang/Boolean;)V
      // 323d: aload 52
      // 323f: aload 53
      // 3241: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setGuestCounts (Lorg/opentravel/ota/_2003/_05/GuestCountType;)V
      // 3244: new org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan
      // 3247: dup
      // 3248: invokespecial org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.<init> ()V
      // 324b: astore 55
      // 324d: new org/opentravel/ota/_2003/_05/TimeInstantType
      // 3250: dup
      // 3251: invokespecial org/opentravel/ota/_2003/_05/TimeInstantType.<init> ()V
      // 3254: astore 56
      // 3256: aload 56
      // 3258: ldc_w "Value522820771"
      // 325b: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setValue (Ljava/lang/String;)V
      // 325e: aload 56
      // 3260: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 3263: ldc_w "-P291811822Y1M27DT22H26M56.716S"
      // 3266: invokevirtual javax/xml/datatype/DatatypeFactory.newDuration (Ljava/lang/String;)Ljavax/xml/datatype/Duration;
      // 3269: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setWindowBefore (Ljavax/xml/datatype/Duration;)V
      // 326c: aload 56
      // 326e: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 3271: ldc_w "P186272488Y10M23DT16H44M27.700S"
      // 3274: invokevirtual javax/xml/datatype/DatatypeFactory.newDuration (Ljava/lang/String;)Ljavax/xml/datatype/Duration;
      // 3277: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setWindowAfter (Ljavax/xml/datatype/Duration;)V
      // 327a: aload 56
      // 327c: bipush 1
      // 327d: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 3280: invokevirtual org/opentravel/ota/_2003/_05/TimeInstantType.setCrossDateAllowedIndicator (Ljava/lang/Boolean;)V
      // 3283: aload 55
      // 3285: aload 56
      // 3287: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setDateWindowRange (Lorg/opentravel/ota/_2003/_05/TimeInstantType;)V
      // 328a: new org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow
      // 328d: dup
      // 328e: invokespecial org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.<init> ()V
      // 3291: astore 57
      // 3293: aload 57
      // 3295: ldc_w "EarliestDate-1721841605"
      // 3298: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.setEarliestDate (Ljava/lang/String;)V
      // 329b: aload 57
      // 329d: ldc_w "LatestDate-2002680113"
      // 32a0: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.setLatestDate (Ljava/lang/String;)V
      // 32a3: getstatic org/opentravel/ota/_2003/_05/DayOfWeekType.WED Lorg/opentravel/ota/_2003/_05/DayOfWeekType;
      // 32a6: astore 58
      // 32a8: aload 57
      // 32aa: aload 58
      // 32ac: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow.setDOW (Lorg/opentravel/ota/_2003/_05/DayOfWeekType;)V
      // 32af: aload 55
      // 32b1: aload 57
      // 32b3: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setStartDateWindow (Lorg/opentravel/ota/_2003/_05/DateTimeSpanType$StartDateWindow;)V
      // 32b6: new org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow
      // 32b9: dup
      // 32ba: invokespecial org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.<init> ()V
      // 32bd: astore 59
      // 32bf: aload 59
      // 32c1: ldc_w "EarliestDate-1180182515"
      // 32c4: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.setEarliestDate (Ljava/lang/String;)V
      // 32c7: aload 59
      // 32c9: ldc_w "LatestDate513239070"
      // 32cc: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.setLatestDate (Ljava/lang/String;)V
      // 32cf: getstatic org/opentravel/ota/_2003/_05/DayOfWeekType.FRI Lorg/opentravel/ota/_2003/_05/DayOfWeekType;
      // 32d2: astore 60
      // 32d4: aload 59
      // 32d6: aload 60
      // 32d8: invokevirtual org/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow.setDOW (Lorg/opentravel/ota/_2003/_05/DayOfWeekType;)V
      // 32db: aload 55
      // 32dd: aload 59
      // 32df: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setEndDateWindow (Lorg/opentravel/ota/_2003/_05/DateTimeSpanType$EndDateWindow;)V
      // 32e2: aload 55
      // 32e4: ldc_w "Start1584235034"
      // 32e7: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setStart (Ljava/lang/String;)V
      // 32ea: aload 55
      // 32ec: ldc_w "Duration820094899"
      // 32ef: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setDuration (Ljava/lang/String;)V
      // 32f2: aload 55
      // 32f4: ldc_w "End-1007030889"
      // 32f7: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setEnd (Ljava/lang/String;)V
      // 32fa: aload 55
      // 32fc: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 32ff: ldc_w "P187368805Y10M27DT11H17M7.549S"
      // 3302: invokevirtual javax/xml/datatype/DatatypeFactory.newDuration (Ljava/lang/String;)Ljavax/xml/datatype/Duration;
      // 3305: invokevirtual org/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan.setIncrement (Ljavax/xml/datatype/Duration;)V
      // 3308: aload 52
      // 330a: aload 55
      // 330c: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setTimeSpan (Lorg/opentravel/ota/_2003/_05/ResCommonDetailType$TimeSpan;)V
      // 330f: new org/opentravel/ota/_2003/_05/ResGuestRPHsType
      // 3312: dup
      // 3313: invokespecial org/opentravel/ota/_2003/_05/ResGuestRPHsType.<init> ()V
      // 3316: astore 61
      // 3318: aload 61
      // 331a: ldc_w "Value-1922225514"
      // 331d: invokevirtual org/opentravel/ota/_2003/_05/ResGuestRPHsType.setValue (Ljava/lang/String;)V
      // 3320: aload 52
      // 3322: aload 61
      // 3324: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setResGuestRPHs (Lorg/opentravel/ota/_2003/_05/ResGuestRPHsType;)V
      // 3327: new org/opentravel/ota/_2003/_05/MembershipType
      // 332a: dup
      // 332b: invokespecial org/opentravel/ota/_2003/_05/MembershipType.<init> ()V
      // 332e: astore 62
      // 3330: new java/util/ArrayList
      // 3333: dup
      // 3334: invokespecial java/util/ArrayList.<init> ()V
      // 3337: astore 63
      // 3339: aload 62
      // 333b: invokevirtual org/opentravel/ota/_2003/_05/MembershipType.getMembership ()Ljava/util/List;
      // 333e: aload 63
      // 3340: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3345: pop
      // 3346: aload 52
      // 3348: aload 62
      // 334a: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setMemberships (Lorg/opentravel/ota/_2003/_05/MembershipType;)V
      // 334d: new org/opentravel/ota/_2003/_05/CommentType
      // 3350: dup
      // 3351: invokespecial org/opentravel/ota/_2003/_05/CommentType.<init> ()V
      // 3354: astore 64
      // 3356: new java/util/ArrayList
      // 3359: dup
      // 335a: invokespecial java/util/ArrayList.<init> ()V
      // 335d: astore 65
      // 335f: aload 64
      // 3361: invokevirtual org/opentravel/ota/_2003/_05/CommentType.getComment ()Ljava/util/List;
      // 3364: aload 65
      // 3366: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 336b: pop
      // 336c: aload 52
      // 336e: aload 64
      // 3370: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setComments (Lorg/opentravel/ota/_2003/_05/CommentType;)V
      // 3373: new org/opentravel/ota/_2003/_05/SpecialRequestType
      // 3376: dup
      // 3377: invokespecial org/opentravel/ota/_2003/_05/SpecialRequestType.<init> ()V
      // 337a: astore 66
      // 337c: new java/util/ArrayList
      // 337f: dup
      // 3380: invokespecial java/util/ArrayList.<init> ()V
      // 3383: astore 67
      // 3385: aload 66
      // 3387: invokevirtual org/opentravel/ota/_2003/_05/SpecialRequestType.getSpecialRequest ()Ljava/util/List;
      // 338a: aload 67
      // 338c: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3391: pop
      // 3392: aload 52
      // 3394: aload 66
      // 3396: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setSpecialRequests (Lorg/opentravel/ota/_2003/_05/SpecialRequestType;)V
      // 3399: new org/opentravel/ota/_2003/_05/GuaranteeType
      // 339c: dup
      // 339d: invokespecial org/opentravel/ota/_2003/_05/GuaranteeType.<init> ()V
      // 33a0: astore 68
      // 33a2: new org/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted
      // 33a5: dup
      // 33a6: invokespecial org/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted.<init> ()V
      // 33a9: astore 69
      // 33ab: new java/util/ArrayList
      // 33ae: dup
      // 33af: invokespecial java/util/ArrayList.<init> ()V
      // 33b2: astore 70
      // 33b4: aload 69
      // 33b6: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted.getGuaranteeAccepted ()Ljava/util/List;
      // 33b9: aload 70
      // 33bb: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 33c0: pop
      // 33c1: aload 68
      // 33c3: aload 69
      // 33c5: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setGuaranteesAccepted (Lorg/opentravel/ota/_2003/_05/GuaranteeType$GuaranteesAccepted;)V
      // 33c8: new org/opentravel/ota/_2003/_05/GuaranteeType$Deadline
      // 33cb: dup
      // 33cc: invokespecial org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.<init> ()V
      // 33cf: astore 71
      // 33d1: aload 71
      // 33d3: ldc_w "AbsoluteDeadline-1373600980"
      // 33d6: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setAbsoluteDeadline (Ljava/lang/String;)V
      // 33d9: getstatic org/opentravel/ota/_2003/_05/TimeUnitType.SECOND Lorg/opentravel/ota/_2003/_05/TimeUnitType;
      // 33dc: astore 72
      // 33de: aload 71
      // 33e0: aload 72
      // 33e2: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setOffsetTimeUnit (Lorg/opentravel/ota/_2003/_05/TimeUnitType;)V
      // 33e5: aload 71
      // 33e7: ldc_w 1635550559
      // 33ea: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 33ed: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setOffsetUnitMultiplier (Ljava/lang/Integer;)V
      // 33f0: aload 71
      // 33f2: ldc_w "OffsetDropTime228575337"
      // 33f5: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType$Deadline.setOffsetDropTime (Ljava/lang/String;)V
      // 33f8: aload 68
      // 33fa: aload 71
      // 33fc: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setDeadline (Lorg/opentravel/ota/_2003/_05/GuaranteeType$Deadline;)V
      // 33ff: new org/opentravel/ota/_2003/_05/CommentType
      // 3402: dup
      // 3403: invokespecial org/opentravel/ota/_2003/_05/CommentType.<init> ()V
      // 3406: astore 73
      // 3408: new java/util/ArrayList
      // 340b: dup
      // 340c: invokespecial java/util/ArrayList.<init> ()V
      // 340f: astore 74
      // 3411: aload 73
      // 3413: invokevirtual org/opentravel/ota/_2003/_05/CommentType.getComment ()Ljava/util/List;
      // 3416: aload 74
      // 3418: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 341d: pop
      // 341e: aload 68
      // 3420: aload 73
      // 3422: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setComments (Lorg/opentravel/ota/_2003/_05/CommentType;)V
      // 3425: new java/util/ArrayList
      // 3428: dup
      // 3429: invokespecial java/util/ArrayList.<init> ()V
      // 342c: astore 75
      // 342e: aload 68
      // 3430: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.getGuaranteeDescription ()Ljava/util/List;
      // 3433: aload 75
      // 3435: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 343a: pop
      // 343b: aload 68
      // 343d: ldc_w "RetributionType-1079143409"
      // 3440: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setRetributionType (Ljava/lang/String;)V
      // 3443: aload 68
      // 3445: ldc_w "GuaranteeCode1979120454"
      // 3448: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setGuaranteeCode (Ljava/lang/String;)V
      // 344b: aload 68
      // 344d: ldc_w "GuaranteeType-980727781"
      // 3450: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setGuaranteeType (Ljava/lang/String;)V
      // 3453: aload 68
      // 3455: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 3458: ldc_w "2020-03-24T09:54:33.683+08:00"
      // 345b: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 345e: invokevirtual org/opentravel/ota/_2003/_05/GuaranteeType.setHoldTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 3461: aload 52
      // 3463: aload 68
      // 3465: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setGuarantee (Lorg/opentravel/ota/_2003/_05/GuaranteeType;)V
      // 3468: new org/opentravel/ota/_2003/_05/RequiredPaymentsType
      // 346b: dup
      // 346c: invokespecial org/opentravel/ota/_2003/_05/RequiredPaymentsType.<init> ()V
      // 346f: astore 76
      // 3471: new java/util/ArrayList
      // 3474: dup
      // 3475: invokespecial java/util/ArrayList.<init> ()V
      // 3478: astore 77
      // 347a: aload 76
      // 347c: invokevirtual org/opentravel/ota/_2003/_05/RequiredPaymentsType.getGuaranteePayment ()Ljava/util/List;
      // 347f: aload 77
      // 3481: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3486: pop
      // 3487: aload 52
      // 3489: aload 76
      // 348b: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setDepositPayments (Lorg/opentravel/ota/_2003/_05/RequiredPaymentsType;)V
      // 348e: new org/opentravel/ota/_2003/_05/CancelPenaltiesType
      // 3491: dup
      // 3492: invokespecial org/opentravel/ota/_2003/_05/CancelPenaltiesType.<init> ()V
      // 3495: astore 78
      // 3497: new java/util/ArrayList
      // 349a: dup
      // 349b: invokespecial java/util/ArrayList.<init> ()V
      // 349e: astore 79
      // 34a0: aload 78
      // 34a2: invokevirtual org/opentravel/ota/_2003/_05/CancelPenaltiesType.getCancelPenalty ()Ljava/util/List;
      // 34a5: aload 79
      // 34a7: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 34ac: pop
      // 34ad: aload 78
      // 34af: bipush 1
      // 34b0: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 34b3: invokevirtual org/opentravel/ota/_2003/_05/CancelPenaltiesType.setCancelPolicyIndicator (Ljava/lang/Boolean;)V
      // 34b6: aload 52
      // 34b8: aload 78
      // 34ba: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setCancelPenalties (Lorg/opentravel/ota/_2003/_05/CancelPenaltiesType;)V
      // 34bd: new org/opentravel/ota/_2003/_05/FeesType
      // 34c0: dup
      // 34c1: invokespecial org/opentravel/ota/_2003/_05/FeesType.<init> ()V
      // 34c4: astore 80
      // 34c6: new java/util/ArrayList
      // 34c9: dup
      // 34ca: invokespecial java/util/ArrayList.<init> ()V
      // 34cd: astore 81
      // 34cf: aload 80
      // 34d1: invokevirtual org/opentravel/ota/_2003/_05/FeesType.getFee ()Ljava/util/List;
      // 34d4: aload 81
      // 34d6: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 34db: pop
      // 34dc: aload 52
      // 34de: aload 80
      // 34e0: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setFees (Lorg/opentravel/ota/_2003/_05/FeesType;)V
      // 34e3: new org/opentravel/ota/_2003/_05/TotalType
      // 34e6: dup
      // 34e7: invokespecial org/opentravel/ota/_2003/_05/TotalType.<init> ()V
      // 34ea: astore 82
      // 34ec: new org/opentravel/ota/_2003/_05/TaxesType
      // 34ef: dup
      // 34f0: invokespecial org/opentravel/ota/_2003/_05/TaxesType.<init> ()V
      // 34f3: astore 83
      // 34f5: new java/util/ArrayList
      // 34f8: dup
      // 34f9: invokespecial java/util/ArrayList.<init> ()V
      // 34fc: astore 84
      // 34fe: aload 83
      // 3500: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.getTax ()Ljava/util/List;
      // 3503: aload 84
      // 3505: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 350a: pop
      // 350b: aload 83
      // 350d: new java/math/BigDecimal
      // 3510: dup
      // 3511: ldc_w "7458947002153225720.8272084245673439389"
      // 3514: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 3517: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.setAmount (Ljava/math/BigDecimal;)V
      // 351a: aload 83
      // 351c: ldc_w "CurrencyCode1185970703"
      // 351f: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.setCurrencyCode (Ljava/lang/String;)V
      // 3522: aload 83
      // 3524: new java/math/BigInteger
      // 3527: dup
      // 3528: ldc_w "66419256745608180214855630760608179709"
      // 352b: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 352e: invokevirtual org/opentravel/ota/_2003/_05/TaxesType.setDecimalPlaces (Ljava/math/BigInteger;)V
      // 3531: aload 82
      // 3533: aload 83
      // 3535: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setTaxes (Lorg/opentravel/ota/_2003/_05/TaxesType;)V
      // 3538: aload 82
      // 353a: new java/math/BigDecimal
      // 353d: dup
      // 353e: ldc_w "8156144015277415528.851888187559572326"
      // 3541: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 3544: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAmountBeforeTax (Ljava/math/BigDecimal;)V
      // 3547: aload 82
      // 3549: new java/math/BigDecimal
      // 354c: dup
      // 354d: ldc_w "-7493355449898103593.1582836458408329088"
      // 3550: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 3553: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAmountAfterTax (Ljava/math/BigDecimal;)V
      // 3556: aload 82
      // 3558: bipush 1
      // 3559: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 355c: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAdditionalFeesExcludedIndicator (Ljava/lang/Boolean;)V
      // 355f: aload 82
      // 3561: ldc_w "Type-2078246267"
      // 3564: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setType (Ljava/lang/String;)V
      // 3567: aload 82
      // 3569: bipush 0
      // 356a: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 356d: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setServiceOverrideIndicator (Ljava/lang/Boolean;)V
      // 3570: aload 82
      // 3572: bipush 1
      // 3573: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 3576: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setRateOverrideIndicator (Ljava/lang/Boolean;)V
      // 3579: aload 82
      // 357b: new java/math/BigDecimal
      // 357e: dup
      // 357f: ldc_w "-4574206052561536877.1207090808799719976"
      // 3582: invokespecial java/math/BigDecimal.<init> (Ljava/lang/String;)V
      // 3585: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setAmountIncludingMarkup (Ljava/math/BigDecimal;)V
      // 3588: aload 82
      // 358a: ldc_w "CurrencyCode650267745"
      // 358d: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setCurrencyCode (Ljava/lang/String;)V
      // 3590: aload 82
      // 3592: new java/math/BigInteger
      // 3595: dup
      // 3596: ldc_w "71876528813090217701543858012046225413"
      // 3599: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 359c: invokevirtual org/opentravel/ota/_2003/_05/TotalType.setDecimalPlaces (Ljava/math/BigInteger;)V
      // 359f: aload 52
      // 35a1: aload 82
      // 35a3: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setTotal (Lorg/opentravel/ota/_2003/_05/TotalType;)V
      // 35a6: new org/opentravel/ota/_2003/_05/HotelReservationIDsType
      // 35a9: dup
      // 35aa: invokespecial org/opentravel/ota/_2003/_05/HotelReservationIDsType.<init> ()V
      // 35ad: astore 85
      // 35af: new java/util/ArrayList
      // 35b2: dup
      // 35b3: invokespecial java/util/ArrayList.<init> ()V
      // 35b6: astore 86
      // 35b8: aload 85
      // 35ba: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationIDsType.getHotelReservationID ()Ljava/util/List;
      // 35bd: aload 86
      // 35bf: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 35c4: pop
      // 35c5: aload 52
      // 35c7: aload 85
      // 35c9: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setHotelReservationIDs (Lorg/opentravel/ota/_2003/_05/HotelReservationIDsType;)V
      // 35cc: new org/opentravel/ota/_2003/_05/RoutingHopType
      // 35cf: dup
      // 35d0: invokespecial org/opentravel/ota/_2003/_05/RoutingHopType.<init> ()V
      // 35d3: astore 87
      // 35d5: new java/util/ArrayList
      // 35d8: dup
      // 35d9: invokespecial java/util/ArrayList.<init> ()V
      // 35dc: astore 88
      // 35de: aload 87
      // 35e0: invokevirtual org/opentravel/ota/_2003/_05/RoutingHopType.getRoutingHop ()Ljava/util/List;
      // 35e3: aload 88
      // 35e5: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 35ea: pop
      // 35eb: aload 52
      // 35ed: aload 87
      // 35ef: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setRoutingHops (Lorg/opentravel/ota/_2003/_05/RoutingHopType;)V
      // 35f2: new org/opentravel/ota/_2003/_05/ProfilesType
      // 35f5: dup
      // 35f6: invokespecial org/opentravel/ota/_2003/_05/ProfilesType.<init> ()V
      // 35f9: astore 89
      // 35fb: new java/util/ArrayList
      // 35fe: dup
      // 35ff: invokespecial java/util/ArrayList.<init> ()V
      // 3602: astore 90
      // 3604: aload 89
      // 3606: invokevirtual org/opentravel/ota/_2003/_05/ProfilesType.getProfileInfo ()Ljava/util/List;
      // 3609: aload 90
      // 360b: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3610: pop
      // 3611: aload 52
      // 3613: aload 89
      // 3615: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setProfiles (Lorg/opentravel/ota/_2003/_05/ProfilesType;)V
      // 3618: new org/opentravel/ota/_2003/_05/BookingRulesType
      // 361b: dup
      // 361c: invokespecial org/opentravel/ota/_2003/_05/BookingRulesType.<init> ()V
      // 361f: astore 91
      // 3621: new java/util/ArrayList
      // 3624: dup
      // 3625: invokespecial java/util/ArrayList.<init> ()V
      // 3628: astore 92
      // 362a: aload 91
      // 362c: invokevirtual org/opentravel/ota/_2003/_05/BookingRulesType.getBookingRule ()Ljava/util/List;
      // 362f: aload 92
      // 3631: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3636: pop
      // 3637: aload 52
      // 3639: aload 91
      // 363b: invokevirtual org/opentravel/ota/_2003/_05/ResGlobalInfoType.setBookingRules (Lorg/opentravel/ota/_2003/_05/BookingRulesType;)V
      // 363e: aload 41
      // 3640: aload 52
      // 3642: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setResGlobalInfo (Lorg/opentravel/ota/_2003/_05/ResGlobalInfoType;)V
      // 3645: new org/opentravel/ota/_2003/_05/WrittenConfInstType
      // 3648: dup
      // 3649: invokespecial org/opentravel/ota/_2003/_05/WrittenConfInstType.<init> ()V
      // 364c: astore 93
      // 364e: new org/opentravel/ota/_2003/_05/ParagraphType
      // 3651: dup
      // 3652: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 3655: astore 94
      // 3657: new java/util/ArrayList
      // 365a: dup
      // 365b: invokespecial java/util/ArrayList.<init> ()V
      // 365e: astore 95
      // 3660: aload 94
      // 3662: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 3665: aload 95
      // 3667: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 366c: pop
      // 366d: aload 94
      // 366f: ldc_w "Name-274910786"
      // 3672: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 3675: aload 94
      // 3677: new java/math/BigInteger
      // 367a: dup
      // 367b: ldc_w "-51908955760811306432632045176123781677"
      // 367e: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 3681: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 3684: aload 94
      // 3686: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 3689: ldc_w "2020-03-24T09:54:33.684+08:00"
      // 368c: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 368f: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 3692: aload 94
      // 3694: ldc_w "CreatorID-493819559"
      // 3697: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 369a: aload 94
      // 369c: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 369f: ldc_w "2020-03-24T09:54:33.685+08:00"
      // 36a2: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 36a5: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 36a8: aload 94
      // 36aa: ldc_w "LastModifierID-690471687"
      // 36ad: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 36b0: aload 94
      // 36b2: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 36b5: ldc_w "2020-03-24T09:54:33.685+08:00"
      // 36b8: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 36bb: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 36be: aload 94
      // 36c0: ldc_w "Language-41814154"
      // 36c3: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 36c6: aload 93
      // 36c8: aload 94
      // 36ca: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setSupplementalData (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 36cd: new org/opentravel/ota/_2003/_05/EmailType
      // 36d0: dup
      // 36d1: invokespecial org/opentravel/ota/_2003/_05/EmailType.<init> ()V
      // 36d4: astore 96
      // 36d6: aload 96
      // 36d8: ldc_w "Value-1229967615"
      // 36db: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setValue (Ljava/lang/String;)V
      // 36de: aload 96
      // 36e0: ldc_w "EmailType1887433210"
      // 36e3: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setEmailType (Ljava/lang/String;)V
      // 36e6: aload 96
      // 36e8: ldc_w "RPH415318509"
      // 36eb: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRPH (Ljava/lang/String;)V
      // 36ee: aload 96
      // 36f0: ldc_w "Remark1914308808"
      // 36f3: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRemark (Ljava/lang/String;)V
      // 36f6: aload 96
      // 36f8: bipush 0
      // 36f9: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 36fc: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setDefaultInd (Ljava/lang/Boolean;)V
      // 36ff: aload 96
      // 3701: ldc_w "ShareSynchInd-2116422691"
      // 3704: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareSynchInd (Ljava/lang/String;)V
      // 3707: aload 96
      // 3709: ldc_w "ShareMarketInd-758501599"
      // 370c: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareMarketInd (Ljava/lang/String;)V
      // 370f: aload 93
      // 3711: aload 96
      // 3713: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setEmail (Lorg/opentravel/ota/_2003/_05/EmailType;)V
      // 3716: aload 93
      // 3718: ldc_w "LanguageID181381620"
      // 371b: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setLanguageID (Ljava/lang/String;)V
      // 371e: aload 93
      // 3720: ldc_w "AddresseeName98166537"
      // 3723: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddresseeName (Ljava/lang/String;)V
      // 3726: aload 93
      // 3728: ldc_w "Address-303175897"
      // 372b: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddress (Ljava/lang/String;)V
      // 372e: aload 93
      // 3730: ldc_w "Telephone-1099852308"
      // 3733: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setTelephone (Ljava/lang/String;)V
      // 3736: aload 93
      // 3738: bipush 1
      // 3739: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 373c: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setConfirmInd (Ljava/lang/Boolean;)V
      // 373f: aload 41
      // 3741: aload 93
      // 3743: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setWrittenConfInst (Lorg/opentravel/ota/_2003/_05/WrittenConfInstType;)V
      // 3746: new org/opentravel/ota/_2003/_05/HotelReservationType$Queue
      // 3749: dup
      // 374a: invokespecial org/opentravel/ota/_2003/_05/HotelReservationType$Queue.<init> ()V
      // 374d: astore 97
      // 374f: aload 97
      // 3751: ldc_w "PseudoCityCode-1652691587"
      // 3754: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setPseudoCityCode (Ljava/lang/String;)V
      // 3757: aload 97
      // 3759: ldc_w "QueueNumber1091228949"
      // 375c: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setQueueNumber (Ljava/lang/String;)V
      // 375f: aload 97
      // 3761: ldc_w "QueueCategory-528010738"
      // 3764: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setQueueCategory (Ljava/lang/String;)V
      // 3767: aload 97
      // 3769: ldc_w "SystemCode-1381998794"
      // 376c: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setSystemCode (Ljava/lang/String;)V
      // 376f: aload 97
      // 3771: ldc_w "QueueID704417031"
      // 3774: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationType$Queue.setQueueID (Ljava/lang/String;)V
      // 3777: aload 41
      // 3779: aload 97
      // 377b: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setQueue (Lorg/opentravel/ota/_2003/_05/HotelReservationType$Queue;)V
      // 377e: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 3781: dup
      // 3782: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 3785: astore 98
      // 3787: new java/util/ArrayList
      // 378a: dup
      // 378b: invokespecial java/util/ArrayList.<init> ()V
      // 378e: astore 99
      // 3790: aload 98
      // 3792: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 3795: aload 99
      // 3797: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 379c: pop
      // 379d: aload 41
      // 379f: aload 98
      // 37a1: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 37a4: aload 41
      // 37a6: bipush 1
      // 37a7: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 37aa: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRoomStayReservation (Ljava/lang/Boolean;)V
      // 37ad: aload 41
      // 37af: ldc_w "ResStatus-296910385"
      // 37b2: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setResStatus (Ljava/lang/String;)V
      // 37b5: aload 41
      // 37b7: bipush 1
      // 37b8: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 37bb: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setForcedSellIndicator (Ljava/lang/Boolean;)V
      // 37be: aload 41
      // 37c0: bipush 1
      // 37c1: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 37c4: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setServiceOverrideIndicator (Ljava/lang/Boolean;)V
      // 37c7: aload 41
      // 37c9: bipush 0
      // 37ca: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 37cd: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRateOverrideIndicator (Ljava/lang/Boolean;)V
      // 37d0: aload 41
      // 37d2: bipush 0
      // 37d3: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 37d6: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setWalkInIndicator (Ljava/lang/Boolean;)V
      // 37d9: aload 41
      // 37db: bipush 0
      // 37dc: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 37df: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRoomNumberLockedIndicator (Ljava/lang/Boolean;)V
      // 37e2: aload 41
      // 37e4: ldc_w "OriginalDeliveryMethodCode1929764144"
      // 37e7: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setOriginalDeliveryMethodCode (Ljava/lang/String;)V
      // 37ea: aload 41
      // 37ec: bipush 0
      // 37ed: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 37f0: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setPassiveIndicator (Ljava/lang/Boolean;)V
      // 37f3: aload 41
      // 37f5: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 37f8: ldc_w "2020-03-24T09:54:33.686+08:00"
      // 37fb: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 37fe: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 3801: aload 41
      // 3803: ldc_w "CreatorID-1466086752"
      // 3806: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setCreatorID (Ljava/lang/String;)V
      // 3809: aload 41
      // 380b: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 380e: ldc_w "2020-03-24T09:54:33.686+08:00"
      // 3811: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 3814: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 3817: aload 41
      // 3819: ldc_w "LastModifierID-1060924570"
      // 381c: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setLastModifierID (Ljava/lang/String;)V
      // 381f: aload 41
      // 3821: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 3824: ldc_w "2020-03-24T09:54:33.687+08:00"
      // 3827: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 382a: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 382d: new org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms
      // 3830: dup
      // 3831: invokespecial org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms.<init> ()V
      // 3834: astore 100
      // 3836: new java/util/ArrayList
      // 3839: dup
      // 383a: invokespecial java/util/ArrayList.<init> ()V
      // 383d: astore 101
      // 383f: aload 100
      // 3841: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms.getRebateProgram ()Ljava/util/List;
      // 3844: aload 101
      // 3846: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 384b: pop
      // 384c: aload 41
      // 384e: aload 100
      // 3850: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation.setRebatePrograms (Lorg/opentravel/ota/_2003/_05/HotelReservationsType$HotelReservation$RebatePrograms;)V
      // 3853: aload 40
      // 3855: aload 41
      // 3857: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 385c: pop
      // 385d: aload 39
      // 385f: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.getHotelReservation ()Ljava/util/List;
      // 3862: aload 40
      // 3864: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3869: pop
      // 386a: new org/opentravel/ota/_2003/_05/RoutingHopType
      // 386d: dup
      // 386e: invokespecial org/opentravel/ota/_2003/_05/RoutingHopType.<init> ()V
      // 3871: astore 102
      // 3873: new java/util/ArrayList
      // 3876: dup
      // 3877: invokespecial java/util/ArrayList.<init> ()V
      // 387a: astore 103
      // 387c: aload 102
      // 387e: invokevirtual org/opentravel/ota/_2003/_05/RoutingHopType.getRoutingHop ()Ljava/util/List;
      // 3881: aload 103
      // 3883: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 3888: pop
      // 3889: aload 39
      // 388b: aload 102
      // 388d: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setRoutingHops (Lorg/opentravel/ota/_2003/_05/RoutingHopType;)V
      // 3890: new org/opentravel/ota/_2003/_05/WrittenConfInstType
      // 3893: dup
      // 3894: invokespecial org/opentravel/ota/_2003/_05/WrittenConfInstType.<init> ()V
      // 3897: astore 104
      // 3899: new org/opentravel/ota/_2003/_05/ParagraphType
      // 389c: dup
      // 389d: invokespecial org/opentravel/ota/_2003/_05/ParagraphType.<init> ()V
      // 38a0: astore 105
      // 38a2: new java/util/ArrayList
      // 38a5: dup
      // 38a6: invokespecial java/util/ArrayList.<init> ()V
      // 38a9: astore 106
      // 38ab: aload 105
      // 38ad: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.getTextOrImageOrURL ()Ljava/util/List;
      // 38b0: aload 106
      // 38b2: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 38b7: pop
      // 38b8: aload 105
      // 38ba: ldc_w "Name-157140852"
      // 38bd: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setName (Ljava/lang/String;)V
      // 38c0: aload 105
      // 38c2: new java/math/BigInteger
      // 38c5: dup
      // 38c6: ldc_w "3375285657207055801139247036071262526"
      // 38c9: invokespecial java/math/BigInteger.<init> (Ljava/lang/String;)V
      // 38cc: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setParagraphNumber (Ljava/math/BigInteger;)V
      // 38cf: aload 105
      // 38d1: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 38d4: ldc_w "2020-03-24T09:54:33.687+08:00"
      // 38d7: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 38da: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreateDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 38dd: aload 105
      // 38df: ldc_w "CreatorID-1994530700"
      // 38e2: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setCreatorID (Ljava/lang/String;)V
      // 38e5: aload 105
      // 38e7: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 38ea: ldc_w "2020-03-24T09:54:33.688+08:00"
      // 38ed: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 38f0: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifyDateTime (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 38f3: aload 105
      // 38f5: ldc_w "LastModifierID501858445"
      // 38f8: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLastModifierID (Ljava/lang/String;)V
      // 38fb: aload 105
      // 38fd: invokestatic javax/xml/datatype/DatatypeFactory.newInstance ()Ljavax/xml/datatype/DatatypeFactory;
      // 3900: ldc_w "2020-03-24T09:54:33.688+08:00"
      // 3903: invokevirtual javax/xml/datatype/DatatypeFactory.newXMLGregorianCalendar (Ljava/lang/String;)Ljavax/xml/datatype/XMLGregorianCalendar;
      // 3906: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setPurgeDate (Ljavax/xml/datatype/XMLGregorianCalendar;)V
      // 3909: aload 105
      // 390b: ldc_w "Language452698092"
      // 390e: invokevirtual org/opentravel/ota/_2003/_05/ParagraphType.setLanguage (Ljava/lang/String;)V
      // 3911: aload 104
      // 3913: aload 105
      // 3915: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setSupplementalData (Lorg/opentravel/ota/_2003/_05/ParagraphType;)V
      // 3918: new org/opentravel/ota/_2003/_05/EmailType
      // 391b: dup
      // 391c: invokespecial org/opentravel/ota/_2003/_05/EmailType.<init> ()V
      // 391f: astore 107
      // 3921: aload 107
      // 3923: ldc_w "Value-1852309466"
      // 3926: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setValue (Ljava/lang/String;)V
      // 3929: aload 107
      // 392b: ldc_w "EmailType1358476368"
      // 392e: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setEmailType (Ljava/lang/String;)V
      // 3931: aload 107
      // 3933: ldc_w "RPH-1669243147"
      // 3936: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRPH (Ljava/lang/String;)V
      // 3939: aload 107
      // 393b: ldc_w "Remark668914386"
      // 393e: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setRemark (Ljava/lang/String;)V
      // 3941: aload 107
      // 3943: bipush 1
      // 3944: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 3947: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setDefaultInd (Ljava/lang/Boolean;)V
      // 394a: aload 107
      // 394c: ldc_w "ShareSynchInd412644493"
      // 394f: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareSynchInd (Ljava/lang/String;)V
      // 3952: aload 107
      // 3954: ldc_w "ShareMarketInd1168965481"
      // 3957: invokevirtual org/opentravel/ota/_2003/_05/EmailType.setShareMarketInd (Ljava/lang/String;)V
      // 395a: aload 104
      // 395c: aload 107
      // 395e: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setEmail (Lorg/opentravel/ota/_2003/_05/EmailType;)V
      // 3961: aload 104
      // 3963: ldc_w "LanguageID-1877801294"
      // 3966: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setLanguageID (Ljava/lang/String;)V
      // 3969: aload 104
      // 396b: ldc_w "AddresseeName698409545"
      // 396e: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddresseeName (Ljava/lang/String;)V
      // 3971: aload 104
      // 3973: ldc_w "Address1402100595"
      // 3976: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setAddress (Ljava/lang/String;)V
      // 3979: aload 104
      // 397b: ldc_w "Telephone-2006566163"
      // 397e: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setTelephone (Ljava/lang/String;)V
      // 3981: aload 104
      // 3983: bipush 0
      // 3984: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 3987: invokevirtual org/opentravel/ota/_2003/_05/WrittenConfInstType.setConfirmInd (Ljava/lang/Boolean;)V
      // 398a: aload 39
      // 398c: aload 104
      // 398e: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setWrittenConfInst (Lorg/opentravel/ota/_2003/_05/WrittenConfInstType;)V
      // 3991: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 3994: dup
      // 3995: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 3998: astore 108
      // 399a: new java/util/ArrayList
      // 399d: dup
      // 399e: invokespecial java/util/ArrayList.<init> ()V
      // 39a1: astore 109
      // 39a3: aload 108
      // 39a5: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 39a8: aload 109
      // 39aa: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 39af: pop
      // 39b0: aload 39
      // 39b2: aload 108
      // 39b4: invokevirtual org/opentravel/ota/_2003/_05/HotelReservationsType.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 39b7: aload 4
      // 39b9: aload 39
      // 39bb: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setHotelReservations (Lorg/opentravel/ota/_2003/_05/HotelReservationsType;)V
      // 39be: new org/opentravel/ota/_2003/_05/TPAExtensionsType
      // 39c1: dup
      // 39c2: invokespecial org/opentravel/ota/_2003/_05/TPAExtensionsType.<init> ()V
      // 39c5: astore 110
      // 39c7: new java/util/ArrayList
      // 39ca: dup
      // 39cb: invokespecial java/util/ArrayList.<init> ()V
      // 39ce: astore 111
      // 39d0: aconst_null
      // 39d1: astore 112
      // 39d3: aload 111
      // 39d5: aload 112
      // 39d7: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 39dc: pop
      // 39dd: aload 110
      // 39df: invokevirtual org/opentravel/ota/_2003/_05/TPAExtensionsType.getAny ()Ljava/util/List;
      // 39e2: aload 111
      // 39e4: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 39e9: pop
      // 39ea: aload 4
      // 39ec: aload 110
      // 39ee: invokevirtual org/htng/_2011b/HTNGHotelStayUpdateNotifRQ.setTPAExtensions (Lorg/opentravel/ota/_2003/_05/TPAExtensionsType;)V
      // 39f1: aload 3
      // 39f2: aload 4
      // 39f4: invokeinterface org/htng/_2011b/StayNotification.stayUpdated (Lorg/htng/_2011b/HTNGHotelStayUpdateNotifRQ;)Lorg/htng/_2011b/HTNGResponseBaseType; 2
      // 39f9: astore 113
      // 39fb: getstatic java/lang/System.out Ljava/io/PrintStream;
      // 39fe: new java/lang/StringBuilder
      // 3a01: dup
      // 3a02: invokespecial java/lang/StringBuilder.<init> ()V
      // 3a05: ldc_w "stayUpdated.result="
      // 3a08: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3a0b: aload 113
      // 3a0d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 3a10: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3a13: invokevirtual java/io/PrintStream.println (Ljava/lang/String;)V
      // 3a16: bipush 0
      // 3a17: invokestatic java/lang/System.exit (I)V
      // 3a1a: return
      // try (22 -> 37): 38 java/net/MalformedURLException
   }
}
