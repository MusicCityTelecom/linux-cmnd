// IL code: Gateway
.class private auto ansi '<Module>'
{
} // end of class <Module>

.class public auto ansi serializable beforefieldinit Philips.PSG.Gateway.FileSet
	extends [mscorlib]System.Object
{
	// Nested Types
	.class nested public auto ansi sealed FileSetRequirement
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement Optional = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement Required = int32(1)

	} // end of class FileSetRequirement

	.class nested public auto ansi sealed FileSetID
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID Undefined = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID EasyTransportStream = int32(1)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID PrimeChannelTable = int32(2)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID PrimeNVM = int32(3)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID PrimeTheme = int32(4)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID PrimeVSec = int32(5)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID PrimeStandby = int32(6)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID PrimeUpgrade = int32(7)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID Prime2K10TransportStream = int32(8)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID Easy2K10DsTransportStream = int32(9)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID Easy2K10DlTransportStream = int32(10)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID Prime2K11TransportStream = int32(11)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID Media2K11TransportStream = int32(12)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID Easy2K11Clone = int32(13)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetID Easy2K11Firmware = int32(14)

	} // end of class FileSetID

	.class nested public auto ansi sealed FileSetUsage
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetUsage UseFirstFile = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetUsage UsePrefixFolder = int32(1)
		.field public static literal valuetype Philips.PSG.Gateway.FileSet/FileSetUsage UseInterimFolder = int32(2)

	} // end of class FileSetUsage


	// Fields
	.field private static string DEFAULT_FOLDER
	.field private string _prefixFolder
	.field private class [mscorlib]System.Collections.Generic.List`1<string> _files
	.field private valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement _requirement
	.field private valuetype Philips.PSG.Gateway.FileSet/FileSetID _ID
	.field private string _interimFolder
	.field private valuetype Philips.PSG.Gateway.FileSet/FileSetUsage _usage
	.field private bool _includedInBuild
	.field private bool '<IsValid>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)

	// Methods
	.method public hidebysig specialname 
		instance string get_PrefixFolder () cil managed 
	{
		// Method begins at RVA 0x2050
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.FileSet::_prefixFolder
		IL_0006: ret
	} // end of method FileSet::get_PrefixFolder

	.method public hidebysig specialname 
		instance void set_PrefixFolder (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x2058
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brtrue.s IL_000f

		IL_0003: ldarg.0
		IL_0004: ldsfld string Philips.PSG.Gateway.FileSet::DEFAULT_FOLDER
		IL_0009: stfld string Philips.PSG.Gateway.FileSet::_prefixFolder
		IL_000e: ret

		IL_000f: ldarg.0
		IL_0010: ldarg.1
		IL_0011: stfld string Philips.PSG.Gateway.FileSet::_prefixFolder
		IL_0016: ret
	} // end of method FileSet::set_PrefixFolder

	.method public hidebysig specialname 
		instance string[] get_Files () cil managed 
	{
		// Method begins at RVA 0x2070
		// Header size: 1
		// Code size: 12 (0xc)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<string> Philips.PSG.Gateway.FileSet::_files
		IL_0006: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<string>::ToArray()
		IL_000b: ret
	} // end of method FileSet::get_Files

	.method public hidebysig specialname 
		instance void set_Files (
			string[] 'value'
		) cil managed 
	{
		// Method begins at RVA 0x207d
		// Header size: 1
		// Code size: 27 (0x1b)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<string> Philips.PSG.Gateway.FileSet::_files
		IL_0006: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Clear()
		IL_000b: ldarg.1
		IL_000c: brfalse.s IL_001a

		IL_000e: ldarg.0
		IL_000f: ldfld class [mscorlib]System.Collections.Generic.List`1<string> Philips.PSG.Gateway.FileSet::_files
		IL_0014: ldarg.1
		IL_0015: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::AddRange(class [mscorlib]System.Collections.Generic.IEnumerable`1<!0>)

		IL_001a: ret
	} // end of method FileSet::set_Files

	.method public hidebysig specialname 
		instance string[] get_PrefixedFiles () cil managed 
	{
		// Method begins at RVA 0x209c
		// Header size: 12
		// Code size: 126 (0x7e)
		.maxstack 4
		.locals init (
			[0] class [mscorlib]System.Collections.Generic.List`1<string>,
			[1] valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>,
			[2] string
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<string> Philips.PSG.Gateway.FileSet::_files
		IL_0006: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<string>::get_Count()
		IL_000b: newobj instance void class [mscorlib]System.Collections.Generic.List`1<string>::.ctor(int32)
		IL_0010: stloc.0
		IL_0011: ldarg.0
		IL_0012: ldfld string Philips.PSG.Gateway.FileSet::_prefixFolder
		IL_0017: ldsfld string Philips.PSG.Gateway.FileSet::DEFAULT_FOLDER
		IL_001c: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0021: brfalse.s IL_0031

		IL_0023: ldloc.0
		IL_0024: ldarg.0
		IL_0025: ldfld class [mscorlib]System.Collections.Generic.List`1<string> Philips.PSG.Gateway.FileSet::_files
		IL_002a: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::AddRange(class [mscorlib]System.Collections.Generic.IEnumerable`1<!0>)
		IL_002f: br.s IL_0077

		IL_0031: ldarg.0
		IL_0032: ldfld class [mscorlib]System.Collections.Generic.List`1<string> Philips.PSG.Gateway.FileSet::_files
		IL_0037: callvirt instance valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<!0> class [mscorlib]System.Collections.Generic.List`1<string>::GetEnumerator()
		IL_003c: stloc.1
		.try
		{
			IL_003d: br.s IL_005e
			// loop start (head: IL_005e)
				IL_003f: ldloca.s 1
				IL_0041: call instance !0 valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>::get_Current()
				IL_0046: stloc.2
				IL_0047: ldloc.0
				IL_0048: ldarg.0
				IL_0049: ldfld string Philips.PSG.Gateway.FileSet::_prefixFolder
				IL_004e: ldstr "\\"
				IL_0053: ldloc.2
				IL_0054: call string [mscorlib]System.String::Concat(string, string, string)
				IL_0059: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)

				IL_005e: ldloca.s 1
				IL_0060: call instance bool valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>::MoveNext()
				IL_0065: brtrue.s IL_003f
			// end loop

			IL_0067: leave.s IL_0077
		} // end .try
		finally
		{
			IL_0069: ldloca.s 1
			IL_006b: constrained. valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>
			IL_0071: callvirt instance void [mscorlib]System.IDisposable::Dispose()
			IL_0076: endfinally
		} // end handler

		IL_0077: ldloc.0
		IL_0078: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<string>::ToArray()
		IL_007d: ret
	} // end of method FileSet::get_PrefixedFiles

	.method public hidebysig specialname 
		instance bool get_IsValid () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x2138
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld bool Philips.PSG.Gateway.FileSet::'<IsValid>k__BackingField'
		IL_0006: ret
	} // end of method FileSet::get_IsValid

	.method assembly hidebysig specialname 
		instance void set_IsValid (
			bool 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x2140
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld bool Philips.PSG.Gateway.FileSet::'<IsValid>k__BackingField'
		IL_0007: ret
	} // end of method FileSet::set_IsValid

	.method public hidebysig specialname 
		instance int32 get_NumberOfFiles () cil managed 
	{
		// Method begins at RVA 0x2149
		// Header size: 1
		// Code size: 12 (0xc)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<string> Philips.PSG.Gateway.FileSet::_files
		IL_0006: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<string>::get_Count()
		IL_000b: ret
	} // end of method FileSet::get_NumberOfFiles

	.method public hidebysig specialname 
		instance valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement get_Requirement () cil managed 
	{
		// Method begins at RVA 0x2156
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement Philips.PSG.Gateway.FileSet::_requirement
		IL_0006: ret
	} // end of method FileSet::get_Requirement

	.method public hidebysig specialname 
		instance void set_Requirement (
			valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement 'value'
		) cil managed 
	{
		// Method begins at RVA 0x215e
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement Philips.PSG.Gateway.FileSet::_requirement
		IL_0007: ret
	} // end of method FileSet::set_Requirement

	.method public hidebysig specialname 
		instance valuetype Philips.PSG.Gateway.FileSet/FileSetID get_ID () cil managed 
	{
		// Method begins at RVA 0x2167
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.FileSet/FileSetID Philips.PSG.Gateway.FileSet::_ID
		IL_0006: ret
	} // end of method FileSet::get_ID

	.method public hidebysig specialname 
		instance void set_ID (
			valuetype Philips.PSG.Gateway.FileSet/FileSetID 'value'
		) cil managed 
	{
		// Method begins at RVA 0x216f
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld valuetype Philips.PSG.Gateway.FileSet/FileSetID Philips.PSG.Gateway.FileSet::_ID
		IL_0007: ret
	} // end of method FileSet::set_ID

	.method public hidebysig specialname 
		instance string get_InterimFolder () cil managed 
	{
		// Method begins at RVA 0x2178
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.FileSet::_interimFolder
		IL_0006: ret
	} // end of method FileSet::get_InterimFolder

	.method public hidebysig specialname 
		instance void set_InterimFolder (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x2180
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brtrue.s IL_000f

		IL_0003: ldarg.0
		IL_0004: ldsfld string Philips.PSG.Gateway.FileSet::DEFAULT_FOLDER
		IL_0009: stfld string Philips.PSG.Gateway.FileSet::_interimFolder
		IL_000e: ret

		IL_000f: ldarg.0
		IL_0010: ldarg.1
		IL_0011: stfld string Philips.PSG.Gateway.FileSet::_interimFolder
		IL_0016: ret
	} // end of method FileSet::set_InterimFolder

	.method public hidebysig specialname 
		instance valuetype Philips.PSG.Gateway.FileSet/FileSetUsage get_Usage () cil managed 
	{
		// Method begins at RVA 0x2198
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.FileSet/FileSetUsage Philips.PSG.Gateway.FileSet::_usage
		IL_0006: ret
	} // end of method FileSet::get_Usage

	.method public hidebysig specialname 
		instance void set_Usage (
			valuetype Philips.PSG.Gateway.FileSet/FileSetUsage 'value'
		) cil managed 
	{
		// Method begins at RVA 0x21a0
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld valuetype Philips.PSG.Gateway.FileSet/FileSetUsage Philips.PSG.Gateway.FileSet::_usage
		IL_0007: ret
	} // end of method FileSet::set_Usage

	.method public hidebysig specialname 
		instance bool get_IncludedInBuild () cil managed 
	{
		// Method begins at RVA 0x21a9
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld bool Philips.PSG.Gateway.FileSet::_includedInBuild
		IL_0006: ret
	} // end of method FileSet::get_IncludedInBuild

	.method public hidebysig specialname 
		instance void set_IncludedInBuild (
			bool 'value'
		) cil managed 
	{
		// Method begins at RVA 0x21b1
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld bool Philips.PSG.Gateway.FileSet::_includedInBuild
		IL_0007: ret
	} // end of method FileSet::set_IncludedInBuild

	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x21bc
		// Header size: 12
		// Code size: 68 (0x44)
		.maxstack 2

		IL_0000: ldarg.0
		IL_0001: call instance void [mscorlib]System.Object::.ctor()
		IL_0006: ldarg.0
		IL_0007: ldsfld string Philips.PSG.Gateway.FileSet::DEFAULT_FOLDER
		IL_000c: stfld string Philips.PSG.Gateway.FileSet::_prefixFolder
		IL_0011: ldarg.0
		IL_0012: newobj instance void class [mscorlib]System.Collections.Generic.List`1<string>::.ctor()
		IL_0017: stfld class [mscorlib]System.Collections.Generic.List`1<string> Philips.PSG.Gateway.FileSet::_files
		IL_001c: ldarg.0
		IL_001d: ldc.i4.1
		IL_001e: stfld valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement Philips.PSG.Gateway.FileSet::_requirement
		IL_0023: ldarg.0
		IL_0024: ldc.i4.0
		IL_0025: stfld valuetype Philips.PSG.Gateway.FileSet/FileSetID Philips.PSG.Gateway.FileSet::_ID
		IL_002a: ldarg.0
		IL_002b: ldsfld string Philips.PSG.Gateway.FileSet::DEFAULT_FOLDER
		IL_0030: stfld string Philips.PSG.Gateway.FileSet::_interimFolder
		IL_0035: ldarg.0
		IL_0036: ldc.i4.0
		IL_0037: stfld valuetype Philips.PSG.Gateway.FileSet/FileSetUsage Philips.PSG.Gateway.FileSet::_usage
		IL_003c: ldarg.0
		IL_003d: ldc.i4.0
		IL_003e: stfld bool Philips.PSG.Gateway.FileSet::_includedInBuild
		IL_0043: ret
	} // end of method FileSet::.ctor

	.method public hidebysig 
		instance string PrepareUsageArgument (
			string baseFolder
		) cil managed 
	{
		// Method begins at RVA 0x220c
		// Header size: 12
		// Code size: 345 (0x159)
		.maxstack 4
		.locals init (
			[0] string,
			[1] valuetype Philips.PSG.Gateway.FileSet/FileSetUsage,
			[2] class [mscorlib]System.IO.FileInfo[],
			[3] string,
			[4] string[],
			[5] int32,
			[6] string
		)

		IL_0000: ldsfld string [mscorlib]System.String::Empty
		IL_0005: stloc.0
		IL_0006: ldarg.0
		IL_0007: ldfld valuetype Philips.PSG.Gateway.FileSet/FileSetUsage Philips.PSG.Gateway.FileSet::_usage
		IL_000c: stloc.1
		IL_000d: ldloc.1
		IL_000e: switch (IL_0024, IL_0068, IL_009c)

		IL_001f: br IL_0157

		IL_0024: ldarg.0
		IL_0025: ldfld class [mscorlib]System.Collections.Generic.List`1<string> Philips.PSG.Gateway.FileSet::_files
		IL_002a: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<string>::get_Count()
		IL_002f: ldc.i4.1
		IL_0030: bge.s IL_003d

		IL_0032: ldstr "UseFirstFile requires at least one file entry"
		IL_0037: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_003c: throw

		IL_003d: ldarg.1
		IL_003e: newobj instance void [mscorlib]System.IO.DirectoryInfo::.ctor(string)
		IL_0043: ldarg.0
		IL_0044: call instance string[] Philips.PSG.Gateway.FileSet::get_PrefixedFiles()
		IL_0049: ldc.i4.0
		IL_004a: ldelem.ref
		IL_004b: callvirt instance class [mscorlib]System.IO.FileInfo[] [mscorlib]System.IO.DirectoryInfo::GetFiles(string)
		IL_0050: stloc.2
		IL_0051: ldloc.2
		IL_0052: ldlen
		IL_0053: conv.i4
		IL_0054: ldc.i4.1
		IL_0055: bne.un IL_0157

		IL_005a: ldloc.2
		IL_005b: ldc.i4.0
		IL_005c: ldelem.ref
		IL_005d: callvirt instance string [mscorlib]System.IO.FileSystemInfo::get_FullName()
		IL_0062: stloc.0
		IL_0063: br IL_0157

		IL_0068: ldarg.0
		IL_0069: ldfld string Philips.PSG.Gateway.FileSet::_prefixFolder
		IL_006e: ldsfld string Philips.PSG.Gateway.FileSet::DEFAULT_FOLDER
		IL_0073: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0078: brfalse.s IL_0085

		IL_007a: ldstr "UsePrefixFolder requires a valid path"
		IL_007f: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_0084: throw

		IL_0085: ldarg.1
		IL_0086: ldstr "\\"
		IL_008b: ldarg.0
		IL_008c: ldfld string Philips.PSG.Gateway.FileSet::_prefixFolder
		IL_0091: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0096: stloc.0
		IL_0097: br IL_0157

		IL_009c: ldarg.0
		IL_009d: ldfld string Philips.PSG.Gateway.FileSet::_interimFolder
		IL_00a2: ldsfld string Philips.PSG.Gateway.FileSet::DEFAULT_FOLDER
		IL_00a7: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_00ac: brfalse.s IL_00b9

		IL_00ae: ldstr "UserInterimFolder requires a valid path"
		IL_00b3: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_00b8: throw

		IL_00b9: ldarg.1
		IL_00ba: ldstr "\\"
		IL_00bf: ldarg.0
		IL_00c0: ldfld string Philips.PSG.Gateway.FileSet::_interimFolder
		IL_00c5: call string [mscorlib]System.String::Concat(string, string, string)
		IL_00ca: stloc.3
		IL_00cb: ldloc.3
		IL_00cc: call bool [mscorlib]System.IO.Directory::Exists(string)
		IL_00d1: brfalse.s IL_00e4

		IL_00d3: ldstr "Interim folder already exists: '{0}'"
		IL_00d8: ldloc.3
		IL_00d9: call string [mscorlib]System.String::Format(string, object)
		IL_00de: newobj instance void [mscorlib]System.IO.IOException::.ctor(string)
		IL_00e3: throw

		IL_00e4: ldloc.3
		IL_00e5: call class [mscorlib]System.IO.DirectoryInfo [mscorlib]System.IO.Directory::CreateDirectory(string)
		IL_00ea: pop
		IL_00eb: ldarg.0
		IL_00ec: ldfld string Philips.PSG.Gateway.FileSet::_prefixFolder
		IL_00f1: ldsfld string Philips.PSG.Gateway.FileSet::DEFAULT_FOLDER
		IL_00f6: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_00fb: brtrue.s IL_0114

		IL_00fd: ldloc.3
		IL_00fe: ldstr "\\"
		IL_0103: ldarg.0
		IL_0104: ldfld string Philips.PSG.Gateway.FileSet::_prefixFolder
		IL_0109: call string [mscorlib]System.String::Concat(string, string, string)
		IL_010e: call class [mscorlib]System.IO.DirectoryInfo [mscorlib]System.IO.Directory::CreateDirectory(string)
		IL_0113: pop

		IL_0114: ldarg.0
		IL_0115: call instance string[] Philips.PSG.Gateway.FileSet::get_PrefixedFiles()
		IL_011a: stloc.s 4
		IL_011c: ldc.i4.0
		IL_011d: stloc.s 5
		IL_011f: br.s IL_014d
		// loop start (head: IL_014d)
			IL_0121: ldloc.s 4
			IL_0123: ldloc.s 5
			IL_0125: ldelem.ref
			IL_0126: stloc.s 6
			IL_0128: ldarg.1
			IL_0129: ldstr "\\"
			IL_012e: ldloc.s 6
			IL_0130: call string [mscorlib]System.String::Concat(string, string, string)
			IL_0135: ldloc.3
			IL_0136: ldstr "\\"
			IL_013b: ldloc.s 6
			IL_013d: call string [mscorlib]System.String::Concat(string, string, string)
			IL_0142: call void [mscorlib]System.IO.File::Copy(string, string)
			IL_0147: ldloc.s 5
			IL_0149: ldc.i4.1
			IL_014a: add
			IL_014b: stloc.s 5

			IL_014d: ldloc.s 5
			IL_014f: ldloc.s 4
			IL_0151: ldlen
			IL_0152: conv.i4
			IL_0153: blt.s IL_0121
		// end loop

		IL_0155: ldloc.3
		IL_0156: stloc.0

		IL_0157: ldloc.0
		IL_0158: ret
	} // end of method FileSet::PrepareUsageArgument

	.method public hidebysig 
		instance void ReleaseUsageArgument (
			string baseFolder
		) cil managed 
	{
		// Method begins at RVA 0x2374
		// Header size: 12
		// Code size: 60 (0x3c)
		.maxstack 3
		.locals init (
			[0] valuetype Philips.PSG.Gateway.FileSet/FileSetUsage,
			[1] string
		)

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.FileSet/FileSetUsage Philips.PSG.Gateway.FileSet::_usage
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: switch (IL_003b, IL_003b, IL_001a)

		IL_0019: ret

		IL_001a: ldarg.1
		IL_001b: ldstr "\\"
		IL_0020: ldarg.0
		IL_0021: ldfld string Philips.PSG.Gateway.FileSet::_interimFolder
		IL_0026: call string [mscorlib]System.String::Concat(string, string, string)
		IL_002b: stloc.1
		IL_002c: ldloc.1
		IL_002d: call bool [mscorlib]System.IO.Directory::Exists(string)
		IL_0032: brfalse.s IL_003b

		IL_0034: ldloc.1
		IL_0035: ldc.i4.1
		IL_0036: call void [mscorlib]System.IO.Directory::Delete(string, bool)

		IL_003b: ret
	} // end of method FileSet::ReleaseUsageArgument

	.method public hidebysig 
		instance valuetype Philips.PSG.Gateway.TSGen/TS_FileVersionDetails GetVersionDetails () cil managed 
	{
		// Method begins at RVA 0x23bc
		// Header size: 12
		// Code size: 129 (0x81)
		.maxstack 5
		.locals init (
			[0] valuetype Philips.PSG.Gateway.TSGen/TS_FileVersionDetails,
			[1] string[]
		)

		IL_0000: ldloca.s 0
		IL_0002: initobj Philips.PSG.Gateway.TSGen/TS_FileVersionDetails
		IL_0008: ldarg.0
		IL_0009: call instance string Philips.PSG.Gateway.FileSet::get_PrefixFolder()
		IL_000e: brfalse.s IL_007f

		IL_0010: ldarg.0
		IL_0011: call instance string Philips.PSG.Gateway.FileSet::get_PrefixFolder()
		IL_0016: ldc.i4.1
		IL_0017: newarr [mscorlib]System.Char
		IL_001c: dup
		IL_001d: ldc.i4.0
		IL_001e: ldc.i4.s 92
		IL_0020: stelem.i2
		IL_0021: callvirt instance string[] [mscorlib]System.String::Split(char[])
		IL_0026: stloc.1
		IL_0027: ldloc.1
		IL_0028: ldlen
		IL_0029: conv.i4
		IL_002a: ldc.i4.4
		IL_002b: beq.s IL_002f

		IL_002d: ldloc.0
		IL_002e: ret

		IL_002f: ldloca.s 0
		IL_0031: ldstr "0x"
		IL_0036: ldloc.1
		IL_0037: ldc.i4.0
		IL_0038: ldelem.ref
		IL_0039: call string [mscorlib]System.String::Concat(string, string)
		IL_003e: stfld string Philips.PSG.Gateway.TSGen/TS_FileVersionDetails::hardwareModel
		IL_0043: ldloca.s 0
		IL_0045: ldstr "0x"
		IL_004a: ldloc.1
		IL_004b: ldc.i4.1
		IL_004c: ldelem.ref
		IL_004d: call string [mscorlib]System.String::Concat(string, string)
		IL_0052: stfld string Philips.PSG.Gateway.TSGen/TS_FileVersionDetails::hardwareVersion
		IL_0057: ldloca.s 0
		IL_0059: ldstr "0x"
		IL_005e: ldloc.1
		IL_005f: ldc.i4.2
		IL_0060: ldelem.ref
		IL_0061: call string [mscorlib]System.String::Concat(string, string)
		IL_0066: stfld string Philips.PSG.Gateway.TSGen/TS_FileVersionDetails::softwareModel
		IL_006b: ldloca.s 0
		IL_006d: ldstr "0x"
		IL_0072: ldloc.1
		IL_0073: ldc.i4.3
		IL_0074: ldelem.ref
		IL_0075: call string [mscorlib]System.String::Concat(string, string)
		IL_007a: stfld string Philips.PSG.Gateway.TSGen/TS_FileVersionDetails::softwareVersion

		IL_007f: ldloc.0
		IL_0080: ret
	} // end of method FileSet::GetVersionDetails

	.method private hidebysig specialname rtspecialname static 
		void .cctor () cil managed 
	{
		// Method begins at RVA 0x2449
		// Header size: 1
		// Code size: 11 (0xb)
		.maxstack 8

		IL_0000: ldstr ""
		IL_0005: stsfld string Philips.PSG.Gateway.FileSet::DEFAULT_FOLDER
		IL_000a: ret
	} // end of method FileSet::.cctor

	// Properties
	.property instance string PrefixFolder()
	{
		.get instance string Philips.PSG.Gateway.FileSet::get_PrefixFolder()
		.set instance void Philips.PSG.Gateway.FileSet::set_PrefixFolder(string)
	}
	.property instance string[] Files()
	{
		.get instance string[] Philips.PSG.Gateway.FileSet::get_Files()
		.set instance void Philips.PSG.Gateway.FileSet::set_Files(string[])
	}
	.property instance string[] PrefixedFiles()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance string[] Philips.PSG.Gateway.FileSet::get_PrefixedFiles()
	}
	.property instance bool IsValid()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance bool Philips.PSG.Gateway.FileSet::get_IsValid()
		.set instance void Philips.PSG.Gateway.FileSet::set_IsValid(bool)
	}
	.property instance int32 NumberOfFiles()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.FileSet::get_NumberOfFiles()
	}
	.property instance valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement Requirement()
	{
		.get instance valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement Philips.PSG.Gateway.FileSet::get_Requirement()
		.set instance void Philips.PSG.Gateway.FileSet::set_Requirement(valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement)
	}
	.property instance valuetype Philips.PSG.Gateway.FileSet/FileSetID ID()
	{
		.get instance valuetype Philips.PSG.Gateway.FileSet/FileSetID Philips.PSG.Gateway.FileSet::get_ID()
		.set instance void Philips.PSG.Gateway.FileSet::set_ID(valuetype Philips.PSG.Gateway.FileSet/FileSetID)
	}
	.property instance string InterimFolder()
	{
		.get instance string Philips.PSG.Gateway.FileSet::get_InterimFolder()
		.set instance void Philips.PSG.Gateway.FileSet::set_InterimFolder(string)
	}
	.property instance valuetype Philips.PSG.Gateway.FileSet/FileSetUsage Usage()
	{
		.get instance valuetype Philips.PSG.Gateway.FileSet/FileSetUsage Philips.PSG.Gateway.FileSet::get_Usage()
		.set instance void Philips.PSG.Gateway.FileSet::set_Usage(valuetype Philips.PSG.Gateway.FileSet/FileSetUsage)
	}
	.property instance bool IncludedInBuild()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance bool Philips.PSG.Gateway.FileSet::get_IncludedInBuild()
		.set instance void Philips.PSG.Gateway.FileSet::set_IncludedInBuild(bool)
	}

} // end of class Philips.PSG.Gateway.FileSet

.class public auto ansi beforefieldinit Philips.PSG.Gateway.ConfigurationDialog
	extends [System.Windows.Forms]System.Windows.Forms.Form
{
	// Fields
	.field private class [System]System.ComponentModel.IContainer components
	.field private class [System.Windows.Forms]System.Windows.Forms.Button cancelButton
	.field private class [System.Windows.Forms]System.Windows.Forms.Button okButton
	.field private class [System.Windows.Forms]System.Windows.Forms.Label buildRootFolderLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.TextBox buildRootFolderTextBox
	.field private class [System.Windows.Forms]System.Windows.Forms.Button buildRootFolderBrowseButton
	.field private class [System.Windows.Forms]System.Windows.Forms.Button workingRootFolderBrowseButton
	.field private class [System.Windows.Forms]System.Windows.Forms.TextBox workingRootFolderTextBox
	.field private class [System.Windows.Forms]System.Windows.Forms.Label workingRootFolderLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.Label constellationLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.ComboBox constellationComboBox
	.field private class [System.Windows.Forms]System.Windows.Forms.Label frequencyLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.NumericUpDown frequencyUpDown
	.field private class [System.Windows.Forms]System.Windows.Forms.Label frequencyUnitLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.Label frequencyWarningLabel

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x2455
		// Header size: 1
		// Code size: 19 (0x13)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Form::.ctor()
		IL_0006: ldarg.0
		IL_0007: call instance void Philips.PSG.Gateway.ConfigurationDialog::InitializeComponent()
		IL_000c: ldarg.0
		IL_000d: call instance void Philips.PSG.Gateway.ConfigurationDialog::InitializeCustom()
		IL_0012: ret
	} // end of method ConfigurationDialog::.ctor

	.method private hidebysig 
		instance void InitializeCustom () cil managed 
	{
		// Method begins at RVA 0x246c
		// Header size: 12
		// Code size: 304 (0x130)
		.maxstack 5

		IL_0000: ldarg.0
		IL_0001: call string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationTitle()
		IL_0006: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_000b: ldarg.0
		IL_000c: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_0011: call string Philips.PSG.Gateway.Resources::get_BTN_OK()
		IL_0016: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_001b: ldarg.0
		IL_001c: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_0021: call string Philips.PSG.Gateway.Resources::get_BTN_Cancel()
		IL_0026: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_002b: ldarg.0
		IL_002c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderLabel
		IL_0031: call string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationBuildRootFolderLabel()
		IL_0036: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_003b: ldarg.0
		IL_003c: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton
		IL_0041: call string Philips.PSG.Gateway.Resources::get_BTN_Browse()
		IL_0046: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_004b: ldarg.0
		IL_004c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderLabel
		IL_0051: call string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationWorkingRootFolderLabel()
		IL_0056: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_005b: ldarg.0
		IL_005c: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton
		IL_0061: call string Philips.PSG.Gateway.Resources::get_BTN_Browse()
		IL_0066: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_006b: ldarg.0
		IL_006c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyLabel
		IL_0071: call string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationFrequencyLabel()
		IL_0076: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_007b: ldarg.0
		IL_007c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyUnitLabel
		IL_0081: call string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationFrequencyUnit()
		IL_0086: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_008b: ldarg.0
		IL_008c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_0091: call string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationNotDefaultOutputFrequencyLabel()
		IL_0096: ldc.i4 7060
		IL_009b: box [mscorlib]System.Int32
		IL_00a0: call string [mscorlib]System.String::Format(string, object)
		IL_00a5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_00aa: ldarg.0
		IL_00ab: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::constellationLabel
		IL_00b0: call string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationConstellationLabel()
		IL_00b5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_00ba: ldarg.0
		IL_00bb: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_00c0: ldc.i4 450
		IL_00c5: newobj instance void [mscorlib]System.Decimal::.ctor(int32)
		IL_00ca: callvirt instance void [System.Windows.Forms]System.Windows.Forms.NumericUpDown::set_Minimum(valuetype [mscorlib]System.Decimal)
		IL_00cf: ldarg.0
		IL_00d0: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_00d5: ldc.i4 8640
		IL_00da: newobj instance void [mscorlib]System.Decimal::.ctor(int32)
		IL_00df: callvirt instance void [System.Windows.Forms]System.Windows.Forms.NumericUpDown::set_Maximum(valuetype [mscorlib]System.Decimal)
		IL_00e4: ldarg.0
		IL_00e5: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_00ea: ldc.i4.8
		IL_00eb: newobj instance void [mscorlib]System.Decimal::.ctor(int32)
		IL_00f0: callvirt instance void [System.Windows.Forms]System.Windows.Forms.NumericUpDown::set_Increment(valuetype [mscorlib]System.Decimal)
		IL_00f5: ldarg.0
		IL_00f6: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_00fb: ldc.i4.0
		IL_00fc: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Visible(bool)
		IL_0101: ldarg.0
		IL_0102: ldfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_0107: callvirt instance class [System.Windows.Forms]System.Windows.Forms.ComboBox/ObjectCollection [System.Windows.Forms]System.Windows.Forms.ComboBox::get_Items()
		IL_010c: ldc.i4.3
		IL_010d: newarr [mscorlib]System.Object
		IL_0112: dup
		IL_0113: ldc.i4.0
		IL_0114: ldsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QPSK_STRING
		IL_0119: stelem.ref
		IL_011a: dup
		IL_011b: ldc.i4.1
		IL_011c: ldsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QAM16_STRING
		IL_0121: stelem.ref
		IL_0122: dup
		IL_0123: ldc.i4.2
		IL_0124: ldsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QAM64_STRING
		IL_0129: stelem.ref
		IL_012a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ComboBox/ObjectCollection::AddRange(object[])
		IL_012f: ret
	} // end of method ConfigurationDialog::InitializeCustom

	.method private hidebysig 
		instance void DisplayConfigData (
			class Philips.PSG.Gateway.Config config
		) cil managed 
	{
		// Method begins at RVA 0x25a8
		// Header size: 12
		// Code size: 74 (0x4a)
		.maxstack 2

		IL_0000: ldarg.0
		IL_0001: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_0006: ldarg.1
		IL_0007: callvirt instance string Philips.PSG.Gateway.Config::get_BuildRootFolder()
		IL_000c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0011: ldarg.0
		IL_0012: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_0017: ldarg.1
		IL_0018: callvirt instance string Philips.PSG.Gateway.Config::get_WorkingRootFolder()
		IL_001d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0022: ldarg.0
		IL_0023: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_0028: ldarg.1
		IL_0029: callvirt instance int32 Philips.PSG.Gateway.Config::get_ModulationFrequency()
		IL_002e: call valuetype [mscorlib]System.Decimal [mscorlib]System.Decimal::op_Implicit(int32)
		IL_0033: callvirt instance void [System.Windows.Forms]System.Windows.Forms.NumericUpDown::set_Value(valuetype [mscorlib]System.Decimal)
		IL_0038: ldarg.0
		IL_0039: ldfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_003e: ldarg.1
		IL_003f: callvirt instance string Philips.PSG.Gateway.Config::get_ConstellationAsString()
		IL_0044: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ComboBox::set_SelectedItem(object)
		IL_0049: ret
	} // end of method ConfigurationDialog::DisplayConfigData

	.method private hidebysig 
		instance bool GatherConfigData (
			class Philips.PSG.Gateway.Config config
		) cil managed 
	{
		// Method begins at RVA 0x2600
		// Header size: 12
		// Code size: 149 (0x95)
		.maxstack 2
		.locals init (
			[0] bool,
			[1] string,
			[2] int32
		)

		IL_0000: ldc.i4.0
		IL_0001: stloc.0
		IL_0002: ldarg.0
		IL_0003: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_0008: callvirt instance string [System.Windows.Forms]System.Windows.Forms.Control::get_Text()
		IL_000d: stloc.1
		IL_000e: ldarg.1
		IL_000f: callvirt instance string Philips.PSG.Gateway.Config::get_BuildRootFolder()
		IL_0014: ldloc.1
		IL_0015: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_001a: brtrue.s IL_0025

		IL_001c: ldarg.1
		IL_001d: ldloc.1
		IL_001e: callvirt instance void Philips.PSG.Gateway.Config::set_BuildRootFolder(string)
		IL_0023: ldc.i4.1
		IL_0024: stloc.0

		IL_0025: ldarg.0
		IL_0026: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_002b: callvirt instance string [System.Windows.Forms]System.Windows.Forms.Control::get_Text()
		IL_0030: stloc.1
		IL_0031: ldarg.1
		IL_0032: callvirt instance string Philips.PSG.Gateway.Config::get_WorkingRootFolder()
		IL_0037: ldloc.1
		IL_0038: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_003d: brtrue.s IL_0048

		IL_003f: ldarg.1
		IL_0040: ldloc.1
		IL_0041: callvirt instance void Philips.PSG.Gateway.Config::set_WorkingRootFolder(string)
		IL_0046: ldc.i4.1
		IL_0047: stloc.0

		IL_0048: ldarg.0
		IL_0049: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_004e: callvirt instance valuetype [mscorlib]System.Decimal [System.Windows.Forms]System.Windows.Forms.NumericUpDown::get_Value()
		IL_0053: call int32 [mscorlib]System.Decimal::op_Explicit(valuetype [mscorlib]System.Decimal)
		IL_0058: stloc.2
		IL_0059: ldarg.1
		IL_005a: callvirt instance int32 Philips.PSG.Gateway.Config::get_ModulationFrequency()
		IL_005f: ldloc.2
		IL_0060: beq.s IL_006b

		IL_0062: ldarg.1
		IL_0063: ldloc.2
		IL_0064: callvirt instance void Philips.PSG.Gateway.Config::set_ModulationFrequency(int32)
		IL_0069: ldc.i4.1
		IL_006a: stloc.0

		IL_006b: ldarg.0
		IL_006c: ldfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_0071: callvirt instance object [System.Windows.Forms]System.Windows.Forms.ComboBox::get_SelectedItem()
		IL_0076: castclass [mscorlib]System.String
		IL_007b: stloc.1
		IL_007c: ldarg.1
		IL_007d: callvirt instance string Philips.PSG.Gateway.Config::get_ConstellationAsString()
		IL_0082: ldloc.1
		IL_0083: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0088: brtrue.s IL_0093

		IL_008a: ldarg.1
		IL_008b: ldloc.1
		IL_008c: callvirt instance void Philips.PSG.Gateway.Config::set_ConstellationAsString(string)
		IL_0091: ldc.i4.1
		IL_0092: stloc.0

		IL_0093: ldloc.0
		IL_0094: ret
	} // end of method ConfigurationDialog::GatherConfigData

	.method public hidebysig 
		instance bool ShowDialog (
			class [System.Windows.Forms]System.Windows.Forms.IWin32Window owner,
			class Philips.PSG.Gateway.Config config
		) cil managed 
	{
		// Method begins at RVA 0x26a1
		// Header size: 1
		// Code size: 27 (0x1b)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.2
		IL_0002: call instance void Philips.PSG.Gateway.ConfigurationDialog::DisplayConfigData(class Philips.PSG.Gateway.Config)
		IL_0007: ldarg.0
		IL_0008: ldarg.1
		IL_0009: call instance valuetype [System.Windows.Forms]System.Windows.Forms.DialogResult [System.Windows.Forms]System.Windows.Forms.Form::ShowDialog(class [System.Windows.Forms]System.Windows.Forms.IWin32Window)
		IL_000e: ldc.i4.1
		IL_000f: bne.un.s IL_0019

		IL_0011: ldarg.0
		IL_0012: ldarg.2
		IL_0013: call instance bool Philips.PSG.Gateway.ConfigurationDialog::GatherConfigData(class Philips.PSG.Gateway.Config)
		IL_0018: ret

		IL_0019: ldc.i4.0
		IL_001a: ret
	} // end of method ConfigurationDialog::ShowDialog

	.method private hidebysig 
		instance bool ValidateOutputChannelSetting () cil managed 
	{
		// Method begins at RVA 0x26bd
		// Header size: 1
		// Code size: 55 (0x37)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_0006: callvirt instance valuetype [mscorlib]System.Decimal [System.Windows.Forms]System.Windows.Forms.NumericUpDown::get_Value()
		IL_000b: ldc.i4 7060
		IL_0010: newobj instance void [mscorlib]System.Decimal::.ctor(int32)
		IL_0015: call bool [mscorlib]System.Decimal::op_Equality(valuetype [mscorlib]System.Decimal, valuetype [mscorlib]System.Decimal)
		IL_001a: brfalse.s IL_001e

		IL_001c: ldc.i4.1
		IL_001d: ret

		IL_001e: call string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationConfirmOutputChannelMessage()
		IL_0023: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
		IL_0028: ldc.i4.4
		IL_0029: ldc.i4.s 32
		IL_002b: call valuetype [System.Windows.Forms]System.Windows.Forms.DialogResult [System.Windows.Forms]System.Windows.Forms.MessageBox::Show(string, string, valuetype [System.Windows.Forms]System.Windows.Forms.MessageBoxButtons, valuetype [System.Windows.Forms]System.Windows.Forms.MessageBoxIcon)
		IL_0030: ldc.i4.6
		IL_0031: bne.un.s IL_0035

		IL_0033: ldc.i4.1
		IL_0034: ret

		IL_0035: ldc.i4.0
		IL_0036: ret
	} // end of method ConfigurationDialog::ValidateOutputChannelSetting

	.method private hidebysig 
		instance void okButton_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x26f5
		// Header size: 1
		// Code size: 16 (0x10)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance bool Philips.PSG.Gateway.ConfigurationDialog::ValidateOutputChannelSetting()
		IL_0006: brfalse.s IL_000f

		IL_0008: ldarg.0
		IL_0009: ldc.i4.1
		IL_000a: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_DialogResult(valuetype [System.Windows.Forms]System.Windows.Forms.DialogResult)

		IL_000f: ret
	} // end of method ConfigurationDialog::okButton_Click

	.method private hidebysig 
		instance bool AskUserForFolder (
			string& folder,
			string message
		) cil managed 
	{
		// Method begins at RVA 0x2708
		// Header size: 12
		// Code size: 58 (0x3a)
		.maxstack 2
		.locals init (
			[0] class [System.Windows.Forms]System.Windows.Forms.FolderBrowserDialog
		)

		IL_0000: newobj instance void [System.Windows.Forms]System.Windows.Forms.FolderBrowserDialog::.ctor()
		IL_0005: stloc.0
		IL_0006: ldloc.0
		IL_0007: ldc.i4.0
		IL_0008: callvirt instance void [System.Windows.Forms]System.Windows.Forms.FolderBrowserDialog::set_ShowNewFolderButton(bool)
		IL_000d: ldloc.0
		IL_000e: ldc.i4.s 17
		IL_0010: callvirt instance void [System.Windows.Forms]System.Windows.Forms.FolderBrowserDialog::set_RootFolder(valuetype [mscorlib]System.Environment/SpecialFolder)
		IL_0015: ldloc.0
		IL_0016: ldarg.1
		IL_0017: ldind.ref
		IL_0018: callvirt instance void [System.Windows.Forms]System.Windows.Forms.FolderBrowserDialog::set_SelectedPath(string)
		IL_001d: ldloc.0
		IL_001e: ldarg.2
		IL_001f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.FolderBrowserDialog::set_Description(string)
		IL_0024: ldloc.0
		IL_0025: ldarg.0
		IL_0026: callvirt instance valuetype [System.Windows.Forms]System.Windows.Forms.DialogResult [System.Windows.Forms]System.Windows.Forms.CommonDialog::ShowDialog(class [System.Windows.Forms]System.Windows.Forms.IWin32Window)
		IL_002b: ldc.i4.1
		IL_002c: bne.un.s IL_0038

		IL_002e: ldarg.1
		IL_002f: ldloc.0
		IL_0030: callvirt instance string [System.Windows.Forms]System.Windows.Forms.FolderBrowserDialog::get_SelectedPath()
		IL_0035: stind.ref
		IL_0036: ldc.i4.1
		IL_0037: ret

		IL_0038: ldc.i4.0
		IL_0039: ret
	} // end of method ConfigurationDialog::AskUserForFolder

	.method private hidebysig 
		instance void buildRootFolderBrowseButton_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x2750
		// Header size: 12
		// Code size: 50 (0x32)
		.maxstack 4
		.locals init (
			[0] string
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_0006: callvirt instance string [System.Windows.Forms]System.Windows.Forms.Control::get_Text()
		IL_000b: stloc.0
		IL_000c: ldarg.0
		IL_000d: ldloca.s 0
		IL_000f: call string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationSelectBuildRootFolderMessage()
		IL_0014: call string Philips.PSG.Gateway.Settings::get_DefaultBuildRootFolder()
		IL_0019: call string [mscorlib]System.String::Format(string, object)
		IL_001e: call instance bool Philips.PSG.Gateway.ConfigurationDialog::AskUserForFolder(string&, string)
		IL_0023: brfalse.s IL_0031

		IL_0025: ldarg.0
		IL_0026: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_002b: ldloc.0
		IL_002c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)

		IL_0031: ret
	} // end of method ConfigurationDialog::buildRootFolderBrowseButton_Click

	.method private hidebysig 
		instance void workingRootFolderBrowseButton_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x2790
		// Header size: 12
		// Code size: 50 (0x32)
		.maxstack 4
		.locals init (
			[0] string
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_0006: callvirt instance string [System.Windows.Forms]System.Windows.Forms.Control::get_Text()
		IL_000b: stloc.0
		IL_000c: ldarg.0
		IL_000d: ldloca.s 0
		IL_000f: call string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationSelectWorkingRootFolderMessage()
		IL_0014: call string Philips.PSG.Gateway.Settings::get_DefaultWorkingRootFolder()
		IL_0019: call string [mscorlib]System.String::Format(string, object)
		IL_001e: call instance bool Philips.PSG.Gateway.ConfigurationDialog::AskUserForFolder(string&, string)
		IL_0023: brfalse.s IL_0031

		IL_0025: ldarg.0
		IL_0026: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_002b: ldloc.0
		IL_002c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)

		IL_0031: ret
	} // end of method ConfigurationDialog::workingRootFolderBrowseButton_Click

	.method private hidebysig 
		instance void frequencyUpDown_ValueChanged (
			object sender,
			class [mscorlib]System.EventArgs e
		) cil managed 
	{
		// Method begins at RVA 0x27d0
		// Header size: 12
		// Code size: 40 (0x28)
		.maxstack 3
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_0006: callvirt instance valuetype [mscorlib]System.Decimal [System.Windows.Forms]System.Windows.Forms.NumericUpDown::get_Value()
		IL_000b: call int32 [mscorlib]System.Decimal::op_Explicit(valuetype [mscorlib]System.Decimal)
		IL_0010: stloc.0
		IL_0011: ldarg.0
		IL_0012: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_0017: ldloc.0
		IL_0018: ldc.i4 7060
		IL_001d: ceq
		IL_001f: ldc.i4.0
		IL_0020: ceq
		IL_0022: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Visible(bool)
		IL_0027: ret
	} // end of method ConfigurationDialog::frequencyUpDown_ValueChanged

	.method family hidebysig virtual 
		instance void Dispose (
			bool disposing
		) cil managed 
	{
		// Method begins at RVA 0x2804
		// Header size: 1
		// Code size: 30 (0x1e)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brfalse.s IL_0016

		IL_0003: ldarg.0
		IL_0004: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.ConfigurationDialog::components
		IL_0009: brfalse.s IL_0016

		IL_000b: ldarg.0
		IL_000c: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.ConfigurationDialog::components
		IL_0011: callvirt instance void [mscorlib]System.IDisposable::Dispose()

		IL_0016: ldarg.0
		IL_0017: ldarg.1
		IL_0018: call instance void [System.Windows.Forms]System.Windows.Forms.Form::Dispose(bool)
		IL_001d: ret
	} // end of method ConfigurationDialog::Dispose

	.method private hidebysig 
		instance void InitializeComponent () cil managed 
	{
		// Method begins at RVA 0x2824
		// Header size: 12
		// Code size: 2069 (0x815)
		.maxstack 3

		IL_0000: ldarg.0
		IL_0001: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_0006: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_000b: ldarg.0
		IL_000c: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_0011: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_0016: ldarg.0
		IL_0017: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_001c: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderLabel
		IL_0021: ldarg.0
		IL_0022: newobj instance void [System.Windows.Forms]System.Windows.Forms.TextBox::.ctor()
		IL_0027: stfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_002c: ldarg.0
		IL_002d: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_0032: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton
		IL_0037: ldarg.0
		IL_0038: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_003d: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton
		IL_0042: ldarg.0
		IL_0043: newobj instance void [System.Windows.Forms]System.Windows.Forms.TextBox::.ctor()
		IL_0048: stfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_004d: ldarg.0
		IL_004e: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0053: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderLabel
		IL_0058: ldarg.0
		IL_0059: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_005e: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::constellationLabel
		IL_0063: ldarg.0
		IL_0064: newobj instance void [System.Windows.Forms]System.Windows.Forms.ComboBox::.ctor()
		IL_0069: stfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_006e: ldarg.0
		IL_006f: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0074: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyLabel
		IL_0079: ldarg.0
		IL_007a: newobj instance void [System.Windows.Forms]System.Windows.Forms.NumericUpDown::.ctor()
		IL_007f: stfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_0084: ldarg.0
		IL_0085: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_008a: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyUnitLabel
		IL_008f: ldarg.0
		IL_0090: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0095: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_009a: ldarg.0
		IL_009b: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_00a0: callvirt instance void [System]System.ComponentModel.ISupportInitialize::BeginInit()
		IL_00a5: ldarg.0
		IL_00a6: call instance void [System.Windows.Forms]System.Windows.Forms.Control::SuspendLayout()
		IL_00ab: ldarg.0
		IL_00ac: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_00b1: ldc.i4.s 10
		IL_00b3: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Anchor(valuetype [System.Windows.Forms]System.Windows.Forms.AnchorStyles)
		IL_00b8: ldarg.0
		IL_00b9: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_00be: ldc.i4.2
		IL_00bf: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Button::set_DialogResult(valuetype [System.Windows.Forms]System.Windows.Forms.DialogResult)
		IL_00c4: ldarg.0
		IL_00c5: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_00ca: ldc.i4 228
		IL_00cf: ldc.i4 213
		IL_00d4: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_00d9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_00de: ldarg.0
		IL_00df: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_00e4: ldstr "cancelButton"
		IL_00e9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_00ee: ldarg.0
		IL_00ef: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_00f4: ldc.i4.s 75
		IL_00f6: ldc.i4.s 23
		IL_00f8: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_00fd: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0102: ldarg.0
		IL_0103: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_0108: ldc.i4.0
		IL_0109: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_010e: ldarg.0
		IL_010f: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_0114: ldstr "Cancel"
		IL_0119: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_011e: ldarg.0
		IL_011f: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_0124: ldc.i4.1
		IL_0125: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ButtonBase::set_UseVisualStyleBackColor(bool)
		IL_012a: ldarg.0
		IL_012b: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_0130: ldc.i4.s 10
		IL_0132: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Anchor(valuetype [System.Windows.Forms]System.Windows.Forms.AnchorStyles)
		IL_0137: ldarg.0
		IL_0138: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_013d: ldc.i4 147
		IL_0142: ldc.i4 213
		IL_0147: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_014c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0151: ldarg.0
		IL_0152: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_0157: ldstr "okButton"
		IL_015c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0161: ldarg.0
		IL_0162: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_0167: ldc.i4.s 75
		IL_0169: ldc.i4.s 23
		IL_016b: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0170: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0175: ldarg.0
		IL_0176: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_017b: ldc.i4.1
		IL_017c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0181: ldarg.0
		IL_0182: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_0187: ldstr "OK"
		IL_018c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0191: ldarg.0
		IL_0192: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_0197: ldc.i4.1
		IL_0198: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ButtonBase::set_UseVisualStyleBackColor(bool)
		IL_019d: ldarg.0
		IL_019e: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_01a3: ldarg.0
		IL_01a4: ldftn instance void Philips.PSG.Gateway.ConfigurationDialog::okButton_Click(object, class [mscorlib]System.EventArgs)
		IL_01aa: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_01af: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::add_Click(class [mscorlib]System.EventHandler)
		IL_01b4: ldarg.0
		IL_01b5: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderLabel
		IL_01ba: ldc.i4.1
		IL_01bb: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_AutoSize(bool)
		IL_01c0: ldarg.0
		IL_01c1: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderLabel
		IL_01c6: ldc.i4.s 12
		IL_01c8: ldc.i4.s 17
		IL_01ca: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_01cf: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_01d4: ldarg.0
		IL_01d5: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderLabel
		IL_01da: ldstr "buildRootFolderLabel"
		IL_01df: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_01e4: ldarg.0
		IL_01e5: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderLabel
		IL_01ea: ldc.i4.s 82
		IL_01ec: ldc.i4.s 13
		IL_01ee: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_01f3: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_01f8: ldarg.0
		IL_01f9: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderLabel
		IL_01fe: ldc.i4.2
		IL_01ff: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0204: ldarg.0
		IL_0205: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderLabel
		IL_020a: ldstr "BuildRootFolder"
		IL_020f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0214: ldarg.0
		IL_0215: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_021a: call valuetype [System.Drawing]System.Drawing.Color [System.Drawing]System.Drawing.Color::get_White()
		IL_021f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_BackColor(valuetype [System.Drawing]System.Drawing.Color)
		IL_0224: ldarg.0
		IL_0225: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_022a: ldc.i4.s 12
		IL_022c: ldc.i4.s 35
		IL_022e: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0233: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0238: ldarg.0
		IL_0239: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_023e: ldstr "buildRootFolderTextBox"
		IL_0243: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0248: ldarg.0
		IL_0249: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_024e: ldc.i4.1
		IL_024f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.TextBoxBase::set_ReadOnly(bool)
		IL_0254: ldarg.0
		IL_0255: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_025a: ldc.i4 209
		IL_025f: ldc.i4.s 20
		IL_0261: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0266: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_026b: ldarg.0
		IL_026c: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_0271: ldc.i4.3
		IL_0272: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0277: ldarg.0
		IL_0278: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton
		IL_027d: ldc.i4 228
		IL_0282: ldc.i4.s 33
		IL_0284: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0289: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_028e: ldarg.0
		IL_028f: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton
		IL_0294: ldstr "buildRootFolderBrowseButton"
		IL_0299: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_029e: ldarg.0
		IL_029f: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton
		IL_02a4: ldc.i4.s 75
		IL_02a6: ldc.i4.s 23
		IL_02a8: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_02ad: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_02b2: ldarg.0
		IL_02b3: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton
		IL_02b8: ldc.i4.4
		IL_02b9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_02be: ldarg.0
		IL_02bf: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton
		IL_02c4: ldstr "Browse"
		IL_02c9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_02ce: ldarg.0
		IL_02cf: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton
		IL_02d4: ldc.i4.1
		IL_02d5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ButtonBase::set_UseVisualStyleBackColor(bool)
		IL_02da: ldarg.0
		IL_02db: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton
		IL_02e0: ldarg.0
		IL_02e1: ldftn instance void Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton_Click(object, class [mscorlib]System.EventArgs)
		IL_02e7: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_02ec: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::add_Click(class [mscorlib]System.EventHandler)
		IL_02f1: ldarg.0
		IL_02f2: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton
		IL_02f7: ldc.i4 228
		IL_02fc: ldc.i4.s 83
		IL_02fe: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0303: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0308: ldarg.0
		IL_0309: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton
		IL_030e: ldstr "workingRootFolderBrowseButton"
		IL_0313: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0318: ldarg.0
		IL_0319: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton
		IL_031e: ldc.i4.s 75
		IL_0320: ldc.i4.s 23
		IL_0322: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0327: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_032c: ldarg.0
		IL_032d: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton
		IL_0332: ldc.i4.7
		IL_0333: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0338: ldarg.0
		IL_0339: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton
		IL_033e: ldstr "Browse"
		IL_0343: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0348: ldarg.0
		IL_0349: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton
		IL_034e: ldc.i4.1
		IL_034f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ButtonBase::set_UseVisualStyleBackColor(bool)
		IL_0354: ldarg.0
		IL_0355: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton
		IL_035a: ldarg.0
		IL_035b: ldftn instance void Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton_Click(object, class [mscorlib]System.EventArgs)
		IL_0361: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_0366: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::add_Click(class [mscorlib]System.EventHandler)
		IL_036b: ldarg.0
		IL_036c: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_0371: call valuetype [System.Drawing]System.Drawing.Color [System.Drawing]System.Drawing.Color::get_White()
		IL_0376: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_BackColor(valuetype [System.Drawing]System.Drawing.Color)
		IL_037b: ldarg.0
		IL_037c: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_0381: ldc.i4.s 12
		IL_0383: ldc.i4.s 85
		IL_0385: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_038a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_038f: ldarg.0
		IL_0390: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_0395: ldstr "workingRootFolderTextBox"
		IL_039a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_039f: ldarg.0
		IL_03a0: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_03a5: ldc.i4.1
		IL_03a6: callvirt instance void [System.Windows.Forms]System.Windows.Forms.TextBoxBase::set_ReadOnly(bool)
		IL_03ab: ldarg.0
		IL_03ac: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_03b1: ldc.i4 209
		IL_03b6: ldc.i4.s 20
		IL_03b8: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_03bd: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_03c2: ldarg.0
		IL_03c3: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_03c8: ldc.i4.6
		IL_03c9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_03ce: ldarg.0
		IL_03cf: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderLabel
		IL_03d4: ldc.i4.1
		IL_03d5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_AutoSize(bool)
		IL_03da: ldarg.0
		IL_03db: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderLabel
		IL_03e0: ldc.i4.s 12
		IL_03e2: ldc.i4.s 67
		IL_03e4: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_03e9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_03ee: ldarg.0
		IL_03ef: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderLabel
		IL_03f4: ldstr "workingRootFolderLabel"
		IL_03f9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_03fe: ldarg.0
		IL_03ff: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderLabel
		IL_0404: ldc.i4.s 99
		IL_0406: ldc.i4.s 13
		IL_0408: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_040d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0412: ldarg.0
		IL_0413: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderLabel
		IL_0418: ldc.i4.5
		IL_0419: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_041e: ldarg.0
		IL_041f: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderLabel
		IL_0424: ldstr "WorkingRootFolder"
		IL_0429: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_042e: ldarg.0
		IL_042f: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::constellationLabel
		IL_0434: ldc.i4.s 12
		IL_0436: ldc.i4 169
		IL_043b: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0440: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0445: ldarg.0
		IL_0446: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::constellationLabel
		IL_044b: ldstr "constellationLabel"
		IL_0450: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0455: ldarg.0
		IL_0456: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::constellationLabel
		IL_045b: ldc.i4.s 85
		IL_045d: ldc.i4.s 13
		IL_045f: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0464: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0469: ldarg.0
		IL_046a: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::constellationLabel
		IL_046f: ldc.i4.s 11
		IL_0471: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0476: ldarg.0
		IL_0477: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::constellationLabel
		IL_047c: ldstr "Constellation"
		IL_0481: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0486: ldarg.0
		IL_0487: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::constellationLabel
		IL_048c: ldc.i4.4
		IL_048d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Label::set_TextAlign(valuetype [System.Drawing]System.Drawing.ContentAlignment)
		IL_0492: ldarg.0
		IL_0493: ldfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_0498: ldc.i4.2
		IL_0499: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ComboBox::set_DropDownStyle(valuetype [System.Windows.Forms]System.Windows.Forms.ComboBoxStyle)
		IL_049e: ldarg.0
		IL_049f: ldfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_04a4: ldc.i4.1
		IL_04a5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ListControl::set_FormattingEnabled(bool)
		IL_04aa: ldarg.0
		IL_04ab: ldfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_04b0: ldc.i4.s 105
		IL_04b2: ldc.i4 165
		IL_04b7: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_04bc: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_04c1: ldarg.0
		IL_04c2: ldfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_04c7: ldstr "constellationComboBox"
		IL_04cc: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_04d1: ldarg.0
		IL_04d2: ldfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_04d7: ldc.i4.s 90
		IL_04d9: ldc.i4.s 21
		IL_04db: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_04e0: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_04e5: ldarg.0
		IL_04e6: ldfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_04eb: ldc.i4.s 12
		IL_04ed: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_04f2: ldarg.0
		IL_04f3: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyLabel
		IL_04f8: ldc.i4.s 12
		IL_04fa: ldc.i4 128
		IL_04ff: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0504: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0509: ldarg.0
		IL_050a: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyLabel
		IL_050f: ldstr "frequencyLabel"
		IL_0514: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0519: ldarg.0
		IL_051a: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyLabel
		IL_051f: ldc.i4.s 85
		IL_0521: ldc.i4.s 13
		IL_0523: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0528: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_052d: ldarg.0
		IL_052e: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyLabel
		IL_0533: ldc.i4.s 13
		IL_0535: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_053a: ldarg.0
		IL_053b: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyLabel
		IL_0540: ldstr "Frequency"
		IL_0545: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_054a: ldarg.0
		IL_054b: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyLabel
		IL_0550: ldc.i4.4
		IL_0551: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Label::set_TextAlign(valuetype [System.Drawing]System.Drawing.ContentAlignment)
		IL_0556: ldarg.0
		IL_0557: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_055c: call valuetype [System.Drawing]System.Drawing.Color [System.Drawing]System.Drawing.Color::get_White()
		IL_0561: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_BackColor(valuetype [System.Drawing]System.Drawing.Color)
		IL_0566: ldarg.0
		IL_0567: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_056c: ldc.i4.s 105
		IL_056e: ldc.i4 128
		IL_0573: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0578: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_057d: ldarg.0
		IL_057e: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_0583: ldstr "frequencyUpDown"
		IL_0588: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_058d: ldarg.0
		IL_058e: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_0593: ldc.i4.1
		IL_0594: callvirt instance void [System.Windows.Forms]System.Windows.Forms.UpDownBase::set_ReadOnly(bool)
		IL_0599: ldarg.0
		IL_059a: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_059f: ldc.i4.s 49
		IL_05a1: ldc.i4.s 20
		IL_05a3: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_05a8: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_05ad: ldarg.0
		IL_05ae: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_05b3: ldc.i4.s 15
		IL_05b5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_05ba: ldarg.0
		IL_05bb: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_05c0: ldarg.0
		IL_05c1: ldftn instance void Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown_ValueChanged(object, class [mscorlib]System.EventArgs)
		IL_05c7: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_05cc: callvirt instance void [System.Windows.Forms]System.Windows.Forms.NumericUpDown::add_ValueChanged(class [mscorlib]System.EventHandler)
		IL_05d1: ldarg.0
		IL_05d2: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyUnitLabel
		IL_05d7: ldc.i4.1
		IL_05d8: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_AutoSize(bool)
		IL_05dd: ldarg.0
		IL_05de: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyUnitLabel
		IL_05e3: ldc.i4 158
		IL_05e8: ldc.i4 131
		IL_05ed: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_05f2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_05f7: ldarg.0
		IL_05f8: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyUnitLabel
		IL_05fd: ldstr "frequencyUnitLabel"
		IL_0602: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0607: ldarg.0
		IL_0608: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyUnitLabel
		IL_060d: ldc.i4.s 29
		IL_060f: ldc.i4.s 13
		IL_0611: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0616: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_061b: ldarg.0
		IL_061c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyUnitLabel
		IL_0621: ldc.i4.s 16
		IL_0623: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0628: ldarg.0
		IL_0629: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyUnitLabel
		IL_062e: ldstr "MHz"
		IL_0633: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0638: ldarg.0
		IL_0639: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_063e: call valuetype [System.Drawing]System.Drawing.Color [System.Drawing]System.Drawing.Color::get_Red()
		IL_0643: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_ForeColor(valuetype [System.Drawing]System.Drawing.Color)
		IL_0648: ldarg.0
		IL_0649: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_064e: ldc.i4 187
		IL_0653: ldc.i4 131
		IL_0658: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_065d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0662: ldarg.0
		IL_0663: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_0668: ldstr "frequencyWarningLabel"
		IL_066d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0672: ldarg.0
		IL_0673: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_0678: ldc.i4 129
		IL_067d: ldc.i4.s 13
		IL_067f: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0684: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0689: ldarg.0
		IL_068a: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_068f: ldc.i4.s 17
		IL_0691: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0696: ldarg.0
		IL_0697: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_069c: ldstr "Frequency Warning"
		IL_06a1: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_06a6: ldarg.0
		IL_06a7: ldc.i4.0
		IL_06a8: call instance void [System.Windows.Forms]System.Windows.Forms.ContainerControl::set_AutoScaleMode(valuetype [System.Windows.Forms]System.Windows.Forms.AutoScaleMode)
		IL_06ad: ldarg.0
		IL_06ae: ldarg.0
		IL_06af: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_06b4: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_CancelButton(class [System.Windows.Forms]System.Windows.Forms.IButtonControl)
		IL_06b9: ldarg.0
		IL_06ba: ldc.i4 315
		IL_06bf: ldc.i4 260
		IL_06c4: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_06c9: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ClientSize(valuetype [System.Drawing]System.Drawing.Size)
		IL_06ce: ldarg.0
		IL_06cf: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_06d4: ldarg.0
		IL_06d5: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyWarningLabel
		IL_06da: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_06df: ldarg.0
		IL_06e0: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_06e5: ldarg.0
		IL_06e6: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyUnitLabel
		IL_06eb: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_06f0: ldarg.0
		IL_06f1: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_06f6: ldarg.0
		IL_06f7: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_06fc: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0701: ldarg.0
		IL_0702: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0707: ldarg.0
		IL_0708: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::frequencyLabel
		IL_070d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0712: ldarg.0
		IL_0713: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0718: ldarg.0
		IL_0719: ldfld class [System.Windows.Forms]System.Windows.Forms.ComboBox Philips.PSG.Gateway.ConfigurationDialog::constellationComboBox
		IL_071e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0723: ldarg.0
		IL_0724: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0729: ldarg.0
		IL_072a: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::constellationLabel
		IL_072f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0734: ldarg.0
		IL_0735: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_073a: ldarg.0
		IL_073b: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderBrowseButton
		IL_0740: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0745: ldarg.0
		IL_0746: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_074b: ldarg.0
		IL_074c: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderTextBox
		IL_0751: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0756: ldarg.0
		IL_0757: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_075c: ldarg.0
		IL_075d: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::workingRootFolderLabel
		IL_0762: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0767: ldarg.0
		IL_0768: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_076d: ldarg.0
		IL_076e: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderBrowseButton
		IL_0773: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0778: ldarg.0
		IL_0779: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_077e: ldarg.0
		IL_077f: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderTextBox
		IL_0784: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0789: ldarg.0
		IL_078a: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_078f: ldarg.0
		IL_0790: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.ConfigurationDialog::buildRootFolderLabel
		IL_0795: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_079a: ldarg.0
		IL_079b: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_07a0: ldarg.0
		IL_07a1: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::okButton
		IL_07a6: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_07ab: ldarg.0
		IL_07ac: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_07b1: ldarg.0
		IL_07b2: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.ConfigurationDialog::cancelButton
		IL_07b7: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_07bc: ldarg.0
		IL_07bd: ldc.i4.3
		IL_07be: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_FormBorderStyle(valuetype [System.Windows.Forms]System.Windows.Forms.FormBorderStyle)
		IL_07c3: ldarg.0
		IL_07c4: ldc.i4.0
		IL_07c5: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_MaximizeBox(bool)
		IL_07ca: ldarg.0
		IL_07cb: ldc.i4.0
		IL_07cc: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_MinimizeBox(bool)
		IL_07d1: ldarg.0
		IL_07d2: ldstr "ConfigurationDialog"
		IL_07d7: call instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_07dc: ldarg.0
		IL_07dd: ldc.i4.0
		IL_07de: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ShowIcon(bool)
		IL_07e3: ldarg.0
		IL_07e4: ldc.i4.0
		IL_07e5: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ShowInTaskbar(bool)
		IL_07ea: ldarg.0
		IL_07eb: ldc.i4.4
		IL_07ec: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_StartPosition(valuetype [System.Windows.Forms]System.Windows.Forms.FormStartPosition)
		IL_07f1: ldarg.0
		IL_07f2: ldstr "ConfigurationDialog"
		IL_07f7: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_07fc: ldarg.0
		IL_07fd: ldfld class [System.Windows.Forms]System.Windows.Forms.NumericUpDown Philips.PSG.Gateway.ConfigurationDialog::frequencyUpDown
		IL_0802: callvirt instance void [System]System.ComponentModel.ISupportInitialize::EndInit()
		IL_0807: ldarg.0
		IL_0808: ldc.i4.0
		IL_0809: call instance void [System.Windows.Forms]System.Windows.Forms.Control::ResumeLayout(bool)
		IL_080e: ldarg.0
		IL_080f: call instance void [System.Windows.Forms]System.Windows.Forms.Control::PerformLayout()
		IL_0814: ret
	} // end of method ConfigurationDialog::InitializeComponent

} // end of class Philips.PSG.Gateway.ConfigurationDialog

.class public auto ansi beforefieldinit Philips.PSG.Gateway.EditEventDialog
	extends [System.Windows.Forms]System.Windows.Forms.Form
{
	// Fields
	.field private class [System]System.ComponentModel.IContainer components
	.field private class [System.Windows.Forms]System.Windows.Forms.Button okButton
	.field private class [System.Windows.Forms]System.Windows.Forms.Button cancelButton
	.field private class [System.Windows.Forms]System.Windows.Forms.Label tvModelLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.Label playbackFileLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.TextBox tvModelTextBox
	.field private class [System.Windows.Forms]System.Windows.Forms.TextBox playbackFileTextBox
	.field private class [System.Windows.Forms]System.Windows.Forms.Label startTimeLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.Label endTimeLabel
	.field private class Philips.PSG.Gateway.TimeEntry startTimeEntry
	.field private class Philips.PSG.Gateway.TimeEntry endTimeEntry
	.field private class [System.Windows.Forms]System.Windows.Forms.Label validationErrorLabel

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x3045
		// Header size: 1
		// Code size: 19 (0x13)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Form::.ctor()
		IL_0006: ldarg.0
		IL_0007: call instance void Philips.PSG.Gateway.EditEventDialog::InitializeComponent()
		IL_000c: ldarg.0
		IL_000d: call instance void Philips.PSG.Gateway.EditEventDialog::InitializeCustom()
		IL_0012: ret
	} // end of method EditEventDialog::.ctor

	.method private hidebysig 
		instance void InitializeCustom () cil managed 
	{
		// Method begins at RVA 0x305c
		// Header size: 12
		// Code size: 108 (0x6c)
		.maxstack 2

		IL_0000: ldarg.0
		IL_0001: call string Philips.PSG.Gateway.Resources::get_DLG_EditEventTitle()
		IL_0006: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_000b: ldarg.0
		IL_000c: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::okButton
		IL_0011: call string Philips.PSG.Gateway.Resources::get_BTN_OK()
		IL_0016: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_001b: ldarg.0
		IL_001c: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_0021: call string Philips.PSG.Gateway.Resources::get_BTN_Cancel()
		IL_0026: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_002b: ldarg.0
		IL_002c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::tvModelLabel
		IL_0031: call string Philips.PSG.Gateway.Resources::get_DLG_EditTvModelLabel()
		IL_0036: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_003b: ldarg.0
		IL_003c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::playbackFileLabel
		IL_0041: call string Philips.PSG.Gateway.Resources::get_DLG_EditPlaybackFileLabel()
		IL_0046: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_004b: ldarg.0
		IL_004c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::startTimeLabel
		IL_0051: call string Philips.PSG.Gateway.Resources::get_DLG_EditStartTimeLabel()
		IL_0056: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_005b: ldarg.0
		IL_005c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::endTimeLabel
		IL_0061: call string Philips.PSG.Gateway.Resources::get_DLG_EditEndTimeLabel()
		IL_0066: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_006b: ret
	} // end of method EditEventDialog::InitializeCustom

	.method private hidebysig 
		instance void DisplayEventData (
			class Philips.PSG.Gateway.ScheduleEvent scheduleEvent
		) cil managed 
	{
		// Method begins at RVA 0x30d4
		// Header size: 12
		// Code size: 74 (0x4a)
		.maxstack 2

		IL_0000: ldarg.0
		IL_0001: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::tvModelTextBox
		IL_0006: ldarg.1
		IL_0007: callvirt instance string Philips.PSG.Gateway.ScheduleEvent::get_TargetTvModel()
		IL_000c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0011: ldarg.0
		IL_0012: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::playbackFileTextBox
		IL_0017: ldarg.1
		IL_0018: callvirt instance string Philips.PSG.Gateway.ScheduleEvent::get_TransportStreamFile()
		IL_001d: call string [mscorlib]System.IO.Path::GetFileName(string)
		IL_0022: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0027: ldarg.0
		IL_0028: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_002d: ldarg.1
		IL_002e: callvirt instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::get_StartTime()
		IL_0033: callvirt instance void Philips.PSG.Gateway.TimeEntry::set_Value(valuetype [mscorlib]System.TimeSpan)
		IL_0038: ldarg.0
		IL_0039: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_003e: ldarg.1
		IL_003f: callvirt instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::get_EndTime()
		IL_0044: callvirt instance void Philips.PSG.Gateway.TimeEntry::set_Value(valuetype [mscorlib]System.TimeSpan)
		IL_0049: ret
	} // end of method EditEventDialog::DisplayEventData

	.method private hidebysig 
		instance bool GatherEventData (
			class Philips.PSG.Gateway.ScheduleEvent scheduleEvent
		) cil managed 
	{
		// Method begins at RVA 0x312c
		// Header size: 12
		// Code size: 80 (0x50)
		.maxstack 2
		.locals init (
			[0] bool,
			[1] valuetype [mscorlib]System.TimeSpan,
			[2] valuetype [mscorlib]System.TimeSpan
		)

		IL_0000: ldc.i4.0
		IL_0001: stloc.0
		IL_0002: ldarg.0
		IL_0003: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_0008: callvirt instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.TimeEntry::get_Value()
		IL_000d: stloc.1
		IL_000e: ldarg.1
		IL_000f: callvirt instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::get_StartTime()
		IL_0014: stloc.2
		IL_0015: ldloca.s 2
		IL_0017: ldloc.1
		IL_0018: call instance bool [mscorlib]System.TimeSpan::Equals(valuetype [mscorlib]System.TimeSpan)
		IL_001d: brtrue.s IL_0028

		IL_001f: ldarg.1
		IL_0020: ldloc.1
		IL_0021: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTime(valuetype [mscorlib]System.TimeSpan)
		IL_0026: ldc.i4.1
		IL_0027: stloc.0

		IL_0028: ldarg.0
		IL_0029: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_002e: callvirt instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.TimeEntry::get_Value()
		IL_0033: stloc.1
		IL_0034: ldarg.1
		IL_0035: callvirt instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::get_EndTime()
		IL_003a: stloc.2
		IL_003b: ldloca.s 2
		IL_003d: ldloc.1
		IL_003e: call instance bool [mscorlib]System.TimeSpan::Equals(valuetype [mscorlib]System.TimeSpan)
		IL_0043: brtrue.s IL_004e

		IL_0045: ldarg.1
		IL_0046: ldloc.1
		IL_0047: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTime(valuetype [mscorlib]System.TimeSpan)
		IL_004c: ldc.i4.1
		IL_004d: stloc.0

		IL_004e: ldloc.0
		IL_004f: ret
	} // end of method EditEventDialog::GatherEventData

	.method private hidebysig 
		instance bool ValidateEdits () cil managed 
	{
		// Method begins at RVA 0x3188
		// Header size: 12
		// Code size: 65 (0x41)
		.maxstack 2
		.locals init (
			[0] valuetype [mscorlib]System.TimeSpan,
			[1] valuetype [mscorlib]System.TimeSpan
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_0006: callvirt instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.TimeEntry::get_Value()
		IL_000b: stloc.0
		IL_000c: ldarg.0
		IL_000d: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_0012: callvirt instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.TimeEntry::get_Value()
		IL_0017: stloc.1
		IL_0018: ldloca.s 0
		IL_001a: ldloc.1
		IL_001b: call instance int32 [mscorlib]System.TimeSpan::CompareTo(valuetype [mscorlib]System.TimeSpan)
		IL_0020: ldc.i4.0
		IL_0021: blt.s IL_003f

		IL_0023: call class [System]System.Media.SystemSound [System]System.Media.SystemSounds::get_Beep()
		IL_0028: callvirt instance void [System]System.Media.SystemSound::Play()
		IL_002d: ldarg.0
		IL_002e: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::validationErrorLabel
		IL_0033: call string Philips.PSG.Gateway.Resources::get_DLG_EditInvalidTimesMessage()
		IL_0038: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_003d: ldc.i4.0
		IL_003e: ret

		IL_003f: ldc.i4.1
		IL_0040: ret
	} // end of method EditEventDialog::ValidateEdits

	.method public hidebysig 
		instance bool ShowDialog (
			class [System.Windows.Forms]System.Windows.Forms.IWin32Window owner,
			class Philips.PSG.Gateway.ScheduleEvent scheduleEvent
		) cil managed 
	{
		// Method begins at RVA 0x31d5
		// Header size: 1
		// Code size: 43 (0x2b)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::validationErrorLabel
		IL_0006: ldsfld string [mscorlib]System.String::Empty
		IL_000b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0010: ldarg.0
		IL_0011: ldarg.2
		IL_0012: call instance void Philips.PSG.Gateway.EditEventDialog::DisplayEventData(class Philips.PSG.Gateway.ScheduleEvent)
		IL_0017: ldarg.0
		IL_0018: ldarg.1
		IL_0019: call instance valuetype [System.Windows.Forms]System.Windows.Forms.DialogResult [System.Windows.Forms]System.Windows.Forms.Form::ShowDialog(class [System.Windows.Forms]System.Windows.Forms.IWin32Window)
		IL_001e: ldc.i4.1
		IL_001f: bne.un.s IL_0029

		IL_0021: ldarg.0
		IL_0022: ldarg.2
		IL_0023: call instance bool Philips.PSG.Gateway.EditEventDialog::GatherEventData(class Philips.PSG.Gateway.ScheduleEvent)
		IL_0028: ret

		IL_0029: ldc.i4.0
		IL_002a: ret
	} // end of method EditEventDialog::ShowDialog

	.method private hidebysig 
		instance void okButton_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x3201
		// Header size: 1
		// Code size: 16 (0x10)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance bool Philips.PSG.Gateway.EditEventDialog::ValidateEdits()
		IL_0006: brfalse.s IL_000f

		IL_0008: ldarg.0
		IL_0009: ldc.i4.1
		IL_000a: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_DialogResult(valuetype [System.Windows.Forms]System.Windows.Forms.DialogResult)

		IL_000f: ret
	} // end of method EditEventDialog::okButton_Click

	.method family hidebysig virtual 
		instance void Dispose (
			bool disposing
		) cil managed 
	{
		// Method begins at RVA 0x3212
		// Header size: 1
		// Code size: 30 (0x1e)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brfalse.s IL_0016

		IL_0003: ldarg.0
		IL_0004: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.EditEventDialog::components
		IL_0009: brfalse.s IL_0016

		IL_000b: ldarg.0
		IL_000c: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.EditEventDialog::components
		IL_0011: callvirt instance void [mscorlib]System.IDisposable::Dispose()

		IL_0016: ldarg.0
		IL_0017: ldarg.1
		IL_0018: call instance void [System.Windows.Forms]System.Windows.Forms.Form::Dispose(bool)
		IL_001d: ret
	} // end of method EditEventDialog::Dispose

	.method private hidebysig 
		instance void InitializeComponent () cil managed 
	{
		// Method begins at RVA 0x3234
		// Header size: 12
		// Code size: 1658 (0x67a)
		.maxstack 3

		IL_0000: ldarg.0
		IL_0001: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_0006: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::okButton
		IL_000b: ldarg.0
		IL_000c: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_0011: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_0016: ldarg.0
		IL_0017: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_001c: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::tvModelLabel
		IL_0021: ldarg.0
		IL_0022: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0027: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::playbackFileLabel
		IL_002c: ldarg.0
		IL_002d: newobj instance void [System.Windows.Forms]System.Windows.Forms.TextBox::.ctor()
		IL_0032: stfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::tvModelTextBox
		IL_0037: ldarg.0
		IL_0038: newobj instance void [System.Windows.Forms]System.Windows.Forms.TextBox::.ctor()
		IL_003d: stfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::playbackFileTextBox
		IL_0042: ldarg.0
		IL_0043: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0048: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::startTimeLabel
		IL_004d: ldarg.0
		IL_004e: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0053: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::endTimeLabel
		IL_0058: ldarg.0
		IL_0059: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_005e: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::validationErrorLabel
		IL_0063: ldarg.0
		IL_0064: newobj instance void Philips.PSG.Gateway.TimeEntry::.ctor()
		IL_0069: stfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_006e: ldarg.0
		IL_006f: newobj instance void Philips.PSG.Gateway.TimeEntry::.ctor()
		IL_0074: stfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_0079: ldarg.0
		IL_007a: call instance void [System.Windows.Forms]System.Windows.Forms.Control::SuspendLayout()
		IL_007f: ldarg.0
		IL_0080: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::okButton
		IL_0085: ldc.i4.s 124
		IL_0087: ldc.i4 194
		IL_008c: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0091: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0096: ldarg.0
		IL_0097: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::okButton
		IL_009c: ldstr "okButton"
		IL_00a1: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_00a6: ldarg.0
		IL_00a7: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::okButton
		IL_00ac: ldc.i4.s 75
		IL_00ae: ldc.i4.s 23
		IL_00b0: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_00b5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_00ba: ldarg.0
		IL_00bb: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::okButton
		IL_00c0: ldc.i4.0
		IL_00c1: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_00c6: ldarg.0
		IL_00c7: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::okButton
		IL_00cc: ldstr "OK"
		IL_00d1: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_00d6: ldarg.0
		IL_00d7: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::okButton
		IL_00dc: ldc.i4.1
		IL_00dd: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ButtonBase::set_UseVisualStyleBackColor(bool)
		IL_00e2: ldarg.0
		IL_00e3: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::okButton
		IL_00e8: ldarg.0
		IL_00e9: ldftn instance void Philips.PSG.Gateway.EditEventDialog::okButton_Click(object, class [mscorlib]System.EventArgs)
		IL_00ef: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_00f4: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::add_Click(class [mscorlib]System.EventHandler)
		IL_00f9: ldarg.0
		IL_00fa: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_00ff: ldc.i4.2
		IL_0100: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Button::set_DialogResult(valuetype [System.Windows.Forms]System.Windows.Forms.DialogResult)
		IL_0105: ldarg.0
		IL_0106: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_010b: ldc.i4 205
		IL_0110: ldc.i4 194
		IL_0115: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_011a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_011f: ldarg.0
		IL_0120: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_0125: ldstr "cancelButton"
		IL_012a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_012f: ldarg.0
		IL_0130: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_0135: ldc.i4.s 75
		IL_0137: ldc.i4.s 23
		IL_0139: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_013e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0143: ldarg.0
		IL_0144: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_0149: ldc.i4.1
		IL_014a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_014f: ldarg.0
		IL_0150: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_0155: ldstr "Cancel"
		IL_015a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_015f: ldarg.0
		IL_0160: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_0165: ldc.i4.1
		IL_0166: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ButtonBase::set_UseVisualStyleBackColor(bool)
		IL_016b: ldarg.0
		IL_016c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::tvModelLabel
		IL_0171: ldc.i4.s 12
		IL_0173: ldc.i4.s 30
		IL_0175: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_017a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_017f: ldarg.0
		IL_0180: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::tvModelLabel
		IL_0185: ldstr "tvModelLabel"
		IL_018a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_018f: ldarg.0
		IL_0190: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::tvModelLabel
		IL_0195: ldc.i4.s 100
		IL_0197: ldc.i4.s 13
		IL_0199: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_019e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_01a3: ldarg.0
		IL_01a4: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::tvModelLabel
		IL_01a9: ldc.i4.2
		IL_01aa: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_01af: ldarg.0
		IL_01b0: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::tvModelLabel
		IL_01b5: ldstr "TvModelLabel"
		IL_01ba: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_01bf: ldarg.0
		IL_01c0: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::tvModelLabel
		IL_01c5: ldc.i4.s 64
		IL_01c7: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Label::set_TextAlign(valuetype [System.Drawing]System.Drawing.ContentAlignment)
		IL_01cc: ldarg.0
		IL_01cd: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::playbackFileLabel
		IL_01d2: ldc.i4.s 12
		IL_01d4: ldc.i4.s 60
		IL_01d6: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_01db: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_01e0: ldarg.0
		IL_01e1: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::playbackFileLabel
		IL_01e6: ldstr "playbackFileLabel"
		IL_01eb: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_01f0: ldarg.0
		IL_01f1: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::playbackFileLabel
		IL_01f6: ldc.i4.s 100
		IL_01f8: ldc.i4.s 13
		IL_01fa: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_01ff: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0204: ldarg.0
		IL_0205: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::playbackFileLabel
		IL_020a: ldc.i4.4
		IL_020b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0210: ldarg.0
		IL_0211: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::playbackFileLabel
		IL_0216: ldstr "PlaybackFileLabel"
		IL_021b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0220: ldarg.0
		IL_0221: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::playbackFileLabel
		IL_0226: ldc.i4.s 64
		IL_0228: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Label::set_TextAlign(valuetype [System.Drawing]System.Drawing.ContentAlignment)
		IL_022d: ldarg.0
		IL_022e: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::tvModelTextBox
		IL_0233: ldc.i4.s 120
		IL_0235: ldc.i4.s 27
		IL_0237: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_023c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0241: ldarg.0
		IL_0242: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::tvModelTextBox
		IL_0247: ldstr "tvModelTextBox"
		IL_024c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0251: ldarg.0
		IL_0252: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::tvModelTextBox
		IL_0257: ldc.i4.1
		IL_0258: callvirt instance void [System.Windows.Forms]System.Windows.Forms.TextBoxBase::set_ReadOnly(bool)
		IL_025d: ldarg.0
		IL_025e: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::tvModelTextBox
		IL_0263: ldc.i4.s 120
		IL_0265: ldc.i4.s 20
		IL_0267: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_026c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0271: ldarg.0
		IL_0272: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::tvModelTextBox
		IL_0277: ldc.i4.6
		IL_0278: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_027d: ldarg.0
		IL_027e: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::playbackFileTextBox
		IL_0283: ldc.i4.s 120
		IL_0285: ldc.i4.s 57
		IL_0287: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_028c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0291: ldarg.0
		IL_0292: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::playbackFileTextBox
		IL_0297: ldstr "playbackFileTextBox"
		IL_029c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_02a1: ldarg.0
		IL_02a2: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::playbackFileTextBox
		IL_02a7: ldc.i4.1
		IL_02a8: callvirt instance void [System.Windows.Forms]System.Windows.Forms.TextBoxBase::set_ReadOnly(bool)
		IL_02ad: ldarg.0
		IL_02ae: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::playbackFileTextBox
		IL_02b3: ldc.i4.s 120
		IL_02b5: ldc.i4.s 20
		IL_02b7: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_02bc: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_02c1: ldarg.0
		IL_02c2: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::playbackFileTextBox
		IL_02c7: ldc.i4.7
		IL_02c8: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_02cd: ldarg.0
		IL_02ce: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::startTimeLabel
		IL_02d3: ldc.i4.s 12
		IL_02d5: ldc.i4.s 90
		IL_02d7: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_02dc: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_02e1: ldarg.0
		IL_02e2: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::startTimeLabel
		IL_02e7: ldstr "startTimeLabel"
		IL_02ec: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_02f1: ldarg.0
		IL_02f2: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::startTimeLabel
		IL_02f7: ldc.i4.s 100
		IL_02f9: ldc.i4.s 13
		IL_02fb: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0300: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0305: ldarg.0
		IL_0306: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::startTimeLabel
		IL_030b: ldc.i4.8
		IL_030c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0311: ldarg.0
		IL_0312: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::startTimeLabel
		IL_0317: ldstr "StartTimeLabel"
		IL_031c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0321: ldarg.0
		IL_0322: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::startTimeLabel
		IL_0327: ldc.i4.s 64
		IL_0329: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Label::set_TextAlign(valuetype [System.Drawing]System.Drawing.ContentAlignment)
		IL_032e: ldarg.0
		IL_032f: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::endTimeLabel
		IL_0334: ldc.i4.s 12
		IL_0336: ldc.i4.s 120
		IL_0338: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_033d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0342: ldarg.0
		IL_0343: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::endTimeLabel
		IL_0348: ldstr "endTimeLabel"
		IL_034d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0352: ldarg.0
		IL_0353: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::endTimeLabel
		IL_0358: ldc.i4.s 100
		IL_035a: ldc.i4.s 13
		IL_035c: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0361: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0366: ldarg.0
		IL_0367: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::endTimeLabel
		IL_036c: ldc.i4.s 9
		IL_036e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0373: ldarg.0
		IL_0374: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::endTimeLabel
		IL_0379: ldstr "EndTimeLabel"
		IL_037e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0383: ldarg.0
		IL_0384: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::endTimeLabel
		IL_0389: ldc.i4.s 64
		IL_038b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Label::set_TextAlign(valuetype [System.Drawing]System.Drawing.ContentAlignment)
		IL_0390: ldarg.0
		IL_0391: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::validationErrorLabel
		IL_0396: call valuetype [System.Drawing]System.Drawing.Color [System.Drawing]System.Drawing.Color::get_Red()
		IL_039b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_ForeColor(valuetype [System.Drawing]System.Drawing.Color)
		IL_03a0: ldarg.0
		IL_03a1: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::validationErrorLabel
		IL_03a6: ldc.i4.s 15
		IL_03a8: ldc.i4 152
		IL_03ad: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_03b2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_03b7: ldarg.0
		IL_03b8: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::validationErrorLabel
		IL_03bd: ldstr "validationErrorLabel"
		IL_03c2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_03c7: ldarg.0
		IL_03c8: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::validationErrorLabel
		IL_03cd: ldc.i4 265
		IL_03d2: ldc.i4.s 30
		IL_03d4: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_03d9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_03de: ldarg.0
		IL_03df: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::validationErrorLabel
		IL_03e4: ldc.i4.s 12
		IL_03e6: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_03eb: ldarg.0
		IL_03ec: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::validationErrorLabel
		IL_03f1: ldstr "ValidationErrorLabel"
		IL_03f6: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_03fb: ldarg.0
		IL_03fc: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_0401: ldc.i4.2
		IL_0402: callvirt instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_InsertKeyMode(valuetype [System.Windows.Forms]System.Windows.Forms.InsertKeyMode)
		IL_0407: ldarg.0
		IL_0408: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_040d: ldc.i4.s 120
		IL_040f: ldc.i4.s 117
		IL_0411: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0416: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_041b: ldarg.0
		IL_041c: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_0421: ldstr "00:00"
		IL_0426: callvirt instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_Mask(string)
		IL_042b: ldarg.0
		IL_042c: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_0431: ldstr "endTimeEntry"
		IL_0436: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_043b: ldarg.0
		IL_043c: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_0441: ldc.i4.s 48
		IL_0443: callvirt instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_PromptChar(char)
		IL_0448: ldarg.0
		IL_0449: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_044e: ldc.i4.0
		IL_044f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_ResetOnSpace(bool)
		IL_0454: ldarg.0
		IL_0455: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_045a: ldc.i4.0
		IL_045b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.TextBoxBase::set_ShortcutsEnabled(bool)
		IL_0460: ldarg.0
		IL_0461: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_0466: ldc.i4.s 40
		IL_0468: ldc.i4.s 20
		IL_046a: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_046f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0474: ldarg.0
		IL_0475: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_047a: ldc.i4.s 11
		IL_047c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0481: ldarg.0
		IL_0482: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_0487: ldc.i4.3
		IL_0488: callvirt instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_TextMaskFormat(valuetype [System.Windows.Forms]System.Windows.Forms.MaskFormat)
		IL_048d: ldarg.0
		IL_048e: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_0493: ldstr "00:00:00"
		IL_0498: call valuetype [mscorlib]System.TimeSpan [mscorlib]System.TimeSpan::Parse(string)
		IL_049d: callvirt instance void Philips.PSG.Gateway.TimeEntry::set_Value(valuetype [mscorlib]System.TimeSpan)
		IL_04a2: ldarg.0
		IL_04a3: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_04a8: ldc.i4.2
		IL_04a9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_InsertKeyMode(valuetype [System.Windows.Forms]System.Windows.Forms.InsertKeyMode)
		IL_04ae: ldarg.0
		IL_04af: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_04b4: ldc.i4.s 120
		IL_04b6: ldc.i4.s 87
		IL_04b8: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_04bd: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_04c2: ldarg.0
		IL_04c3: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_04c8: ldstr "00:00"
		IL_04cd: callvirt instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_Mask(string)
		IL_04d2: ldarg.0
		IL_04d3: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_04d8: ldstr "startTimeEntry"
		IL_04dd: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_04e2: ldarg.0
		IL_04e3: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_04e8: ldc.i4.s 48
		IL_04ea: callvirt instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_PromptChar(char)
		IL_04ef: ldarg.0
		IL_04f0: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_04f5: ldc.i4.0
		IL_04f6: callvirt instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_ResetOnSpace(bool)
		IL_04fb: ldarg.0
		IL_04fc: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_0501: ldc.i4.0
		IL_0502: callvirt instance void [System.Windows.Forms]System.Windows.Forms.TextBoxBase::set_ShortcutsEnabled(bool)
		IL_0507: ldarg.0
		IL_0508: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_050d: ldc.i4.s 40
		IL_050f: ldc.i4.s 20
		IL_0511: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0516: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_051b: ldarg.0
		IL_051c: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_0521: ldc.i4.s 10
		IL_0523: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0528: ldarg.0
		IL_0529: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_052e: ldc.i4.3
		IL_052f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_TextMaskFormat(valuetype [System.Windows.Forms]System.Windows.Forms.MaskFormat)
		IL_0534: ldarg.0
		IL_0535: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_053a: ldstr "00:00:00"
		IL_053f: call valuetype [mscorlib]System.TimeSpan [mscorlib]System.TimeSpan::Parse(string)
		IL_0544: callvirt instance void Philips.PSG.Gateway.TimeEntry::set_Value(valuetype [mscorlib]System.TimeSpan)
		IL_0549: ldarg.0
		IL_054a: ldc.i4.0
		IL_054b: call instance void [System.Windows.Forms]System.Windows.Forms.ContainerControl::set_AutoScaleMode(valuetype [System.Windows.Forms]System.Windows.Forms.AutoScaleMode)
		IL_0550: ldarg.0
		IL_0551: ldarg.0
		IL_0552: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_0557: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_CancelButton(class [System.Windows.Forms]System.Windows.Forms.IButtonControl)
		IL_055c: ldarg.0
		IL_055d: ldc.i4 292
		IL_0562: ldc.i4 229
		IL_0567: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_056c: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ClientSize(valuetype [System.Drawing]System.Drawing.Size)
		IL_0571: ldarg.0
		IL_0572: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0577: ldarg.0
		IL_0578: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::validationErrorLabel
		IL_057d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0582: ldarg.0
		IL_0583: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0588: ldarg.0
		IL_0589: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::endTimeEntry
		IL_058e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0593: ldarg.0
		IL_0594: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0599: ldarg.0
		IL_059a: ldfld class Philips.PSG.Gateway.TimeEntry Philips.PSG.Gateway.EditEventDialog::startTimeEntry
		IL_059f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_05a4: ldarg.0
		IL_05a5: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_05aa: ldarg.0
		IL_05ab: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::endTimeLabel
		IL_05b0: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_05b5: ldarg.0
		IL_05b6: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_05bb: ldarg.0
		IL_05bc: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::startTimeLabel
		IL_05c1: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_05c6: ldarg.0
		IL_05c7: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_05cc: ldarg.0
		IL_05cd: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::playbackFileTextBox
		IL_05d2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_05d7: ldarg.0
		IL_05d8: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_05dd: ldarg.0
		IL_05de: ldfld class [System.Windows.Forms]System.Windows.Forms.TextBox Philips.PSG.Gateway.EditEventDialog::tvModelTextBox
		IL_05e3: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_05e8: ldarg.0
		IL_05e9: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_05ee: ldarg.0
		IL_05ef: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::playbackFileLabel
		IL_05f4: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_05f9: ldarg.0
		IL_05fa: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_05ff: ldarg.0
		IL_0600: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.EditEventDialog::tvModelLabel
		IL_0605: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_060a: ldarg.0
		IL_060b: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0610: ldarg.0
		IL_0611: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::cancelButton
		IL_0616: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_061b: ldarg.0
		IL_061c: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0621: ldarg.0
		IL_0622: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.EditEventDialog::okButton
		IL_0627: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_062c: ldarg.0
		IL_062d: ldc.i4.3
		IL_062e: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_FormBorderStyle(valuetype [System.Windows.Forms]System.Windows.Forms.FormBorderStyle)
		IL_0633: ldarg.0
		IL_0634: ldc.i4.0
		IL_0635: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_MaximizeBox(bool)
		IL_063a: ldarg.0
		IL_063b: ldc.i4.0
		IL_063c: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_MinimizeBox(bool)
		IL_0641: ldarg.0
		IL_0642: ldstr "EditEventDialog"
		IL_0647: call instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_064c: ldarg.0
		IL_064d: ldc.i4.0
		IL_064e: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ShowIcon(bool)
		IL_0653: ldarg.0
		IL_0654: ldc.i4.0
		IL_0655: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ShowInTaskbar(bool)
		IL_065a: ldarg.0
		IL_065b: ldc.i4.4
		IL_065c: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_StartPosition(valuetype [System.Windows.Forms]System.Windows.Forms.FormStartPosition)
		IL_0661: ldarg.0
		IL_0662: ldstr "EditEventDialog"
		IL_0667: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_066c: ldarg.0
		IL_066d: ldc.i4.0
		IL_066e: call instance void [System.Windows.Forms]System.Windows.Forms.Control::ResumeLayout(bool)
		IL_0673: ldarg.0
		IL_0674: call instance void [System.Windows.Forms]System.Windows.Forms.Control::PerformLayout()
		IL_0679: ret
	} // end of method EditEventDialog::InitializeComponent

} // end of class Philips.PSG.Gateway.EditEventDialog

.class public auto ansi beforefieldinit Philips.PSG.Gateway.BuildProgressDialog
	extends [System.Windows.Forms]System.Windows.Forms.Form
{
	// Nested Types
	.class nested private auto ansi sealed Invoke_SetTvModel
		extends [mscorlib]System.MulticastDelegate
	{
		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor (
				object 'object',
				native int 'method'
			) runtime managed 
		{
		} // end of method Invoke_SetTvModel::.ctor

		.method public hidebysig newslot virtual 
			instance void Invoke (
				string text
			) runtime managed 
		{
		} // end of method Invoke_SetTvModel::Invoke

		.method public hidebysig newslot virtual 
			instance class [mscorlib]System.IAsyncResult BeginInvoke (
				string text,
				class [mscorlib]System.AsyncCallback callback,
				object 'object'
			) runtime managed 
		{
		} // end of method Invoke_SetTvModel::BeginInvoke

		.method public hidebysig newslot virtual 
			instance void EndInvoke (
				class [mscorlib]System.IAsyncResult result
			) runtime managed 
		{
		} // end of method Invoke_SetTvModel::EndInvoke

	} // end of class Invoke_SetTvModel

	.class nested private auto ansi sealed Invoke_SetAction
		extends [mscorlib]System.MulticastDelegate
	{
		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor (
				object 'object',
				native int 'method'
			) runtime managed 
		{
		} // end of method Invoke_SetAction::.ctor

		.method public hidebysig newslot virtual 
			instance void Invoke (
				string text
			) runtime managed 
		{
		} // end of method Invoke_SetAction::Invoke

		.method public hidebysig newslot virtual 
			instance class [mscorlib]System.IAsyncResult BeginInvoke (
				string text,
				class [mscorlib]System.AsyncCallback callback,
				object 'object'
			) runtime managed 
		{
		} // end of method Invoke_SetAction::BeginInvoke

		.method public hidebysig newslot virtual 
			instance void EndInvoke (
				class [mscorlib]System.IAsyncResult result
			) runtime managed 
		{
		} // end of method Invoke_SetAction::EndInvoke

	} // end of class Invoke_SetAction

	.class nested private auto ansi sealed Invoke_SetResult
		extends [mscorlib]System.MulticastDelegate
	{
		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor (
				object 'object',
				native int 'method'
			) runtime managed 
		{
		} // end of method Invoke_SetResult::.ctor

		.method public hidebysig newslot virtual 
			instance void Invoke (
				string text,
				bool viewLogButtonVisible
			) runtime managed 
		{
		} // end of method Invoke_SetResult::Invoke

		.method public hidebysig newslot virtual 
			instance class [mscorlib]System.IAsyncResult BeginInvoke (
				string text,
				bool viewLogButtonVisible,
				class [mscorlib]System.AsyncCallback callback,
				object 'object'
			) runtime managed 
		{
		} // end of method Invoke_SetResult::BeginInvoke

		.method public hidebysig newslot virtual 
			instance void EndInvoke (
				class [mscorlib]System.IAsyncResult result
			) runtime managed 
		{
		} // end of method Invoke_SetResult::EndInvoke

	} // end of class Invoke_SetResult


	// Fields
	.field private class [System]System.ComponentModel.IContainer components
	.field private class [System.Windows.Forms]System.Windows.Forms.Panel buildingControlsBox
	.field private class [System.Windows.Forms]System.Windows.Forms.Label actionLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.Label tvModelLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.ProgressBar progressBar
	.field private class [System.Windows.Forms]System.Windows.Forms.Panel completedControlsBox
	.field private class [System.Windows.Forms]System.Windows.Forms.Label resultLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.Button okButton
	.field private class [System.Windows.Forms]System.Windows.Forms.Button viewLogButton

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x38ba
		// Header size: 1
		// Code size: 19 (0x13)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Form::.ctor()
		IL_0006: ldarg.0
		IL_0007: call instance void Philips.PSG.Gateway.BuildProgressDialog::InitializeComponent()
		IL_000c: ldarg.0
		IL_000d: call instance void Philips.PSG.Gateway.BuildProgressDialog::InitializeCustom()
		IL_0012: ret
	} // end of method BuildProgressDialog::.ctor

	.method private hidebysig 
		instance void InitializeCustom () cil managed 
	{
		// Method begins at RVA 0x38ce
		// Header size: 1
		// Code size: 50 (0x32)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::okButton
		IL_0006: call string Philips.PSG.Gateway.Resources::get_BTN_OK()
		IL_000b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0010: ldarg.0
		IL_0011: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_0016: call string Philips.PSG.Gateway.Resources::get_BTN_ViewBuildLog()
		IL_001b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0020: ldarg.0
		IL_0021: call string Philips.PSG.Gateway.Resources::get_DLG_BuildProgressTitle()
		IL_0026: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_002b: ldarg.0
		IL_002c: call instance void Philips.PSG.Gateway.BuildProgressDialog::Reset()
		IL_0031: ret
	} // end of method BuildProgressDialog::InitializeCustom

	.method public hidebysig 
		instance void Reset () cil managed 
	{
		// Method begins at RVA 0x3901
		// Header size: 1
		// Code size: 55 (0x37)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::tvModelLabel
		IL_0006: ldsfld string [mscorlib]System.String::Empty
		IL_000b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0010: ldarg.0
		IL_0011: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::actionLabel
		IL_0016: ldsfld string [mscorlib]System.String::Empty
		IL_001b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0020: ldarg.0
		IL_0021: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::resultLabel
		IL_0026: ldsfld string [mscorlib]System.String::Empty
		IL_002b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0030: ldarg.0
		IL_0031: call instance void Philips.PSG.Gateway.BuildProgressDialog::SetBuildingDisplay()
		IL_0036: ret
	} // end of method BuildProgressDialog::Reset

	.method public hidebysig 
		instance void SetTvModel (
			string text
		) cil managed 
	{
		// Method begins at RVA 0x3939
		// Header size: 1
		// Code size: 57 (0x39)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance bool [System.Windows.Forms]System.Windows.Forms.Control::get_InvokeRequired()
		IL_0006: brfalse.s IL_0026

		IL_0008: ldarg.0
		IL_0009: ldarg.0
		IL_000a: ldftn instance void Philips.PSG.Gateway.BuildProgressDialog::SetTvModel(string)
		IL_0010: newobj instance void Philips.PSG.Gateway.BuildProgressDialog/Invoke_SetTvModel::.ctor(object, native int)
		IL_0015: ldc.i4.1
		IL_0016: newarr [mscorlib]System.Object
		IL_001b: dup
		IL_001c: ldc.i4.0
		IL_001d: ldarg.1
		IL_001e: stelem.ref
		IL_001f: call instance object [System.Windows.Forms]System.Windows.Forms.Control::Invoke(class [mscorlib]System.Delegate, object[])
		IL_0024: pop
		IL_0025: ret

		IL_0026: ldarg.0
		IL_0027: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::tvModelLabel
		IL_002c: ldarg.1
		IL_002d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0032: ldarg.0
		IL_0033: call instance void Philips.PSG.Gateway.BuildProgressDialog::SetBuildingDisplay()
		IL_0038: ret
	} // end of method BuildProgressDialog::SetTvModel

	.method public hidebysig 
		instance void SetAction (
			string text
		) cil managed 
	{
		// Method begins at RVA 0x3973
		// Header size: 1
		// Code size: 51 (0x33)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance bool [System.Windows.Forms]System.Windows.Forms.Control::get_InvokeRequired()
		IL_0006: brfalse.s IL_0026

		IL_0008: ldarg.0
		IL_0009: ldarg.0
		IL_000a: ldftn instance void Philips.PSG.Gateway.BuildProgressDialog::SetAction(string)
		IL_0010: newobj instance void Philips.PSG.Gateway.BuildProgressDialog/Invoke_SetAction::.ctor(object, native int)
		IL_0015: ldc.i4.1
		IL_0016: newarr [mscorlib]System.Object
		IL_001b: dup
		IL_001c: ldc.i4.0
		IL_001d: ldarg.1
		IL_001e: stelem.ref
		IL_001f: call instance object [System.Windows.Forms]System.Windows.Forms.Control::Invoke(class [mscorlib]System.Delegate, object[])
		IL_0024: pop
		IL_0025: ret

		IL_0026: ldarg.0
		IL_0027: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::actionLabel
		IL_002c: ldarg.1
		IL_002d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0032: ret
	} // end of method BuildProgressDialog::SetAction

	.method public hidebysig 
		instance void SetResult (
			string text,
			bool viewLogButtonVisible
		) cil managed 
	{
		// Method begins at RVA 0x39a8
		// Header size: 12
		// Code size: 67 (0x43)
		.maxstack 6

		IL_0000: ldarg.0
		IL_0001: call instance bool [System.Windows.Forms]System.Windows.Forms.Control::get_InvokeRequired()
		IL_0006: brfalse.s IL_002f

		IL_0008: ldarg.0
		IL_0009: ldarg.0
		IL_000a: ldftn instance void Philips.PSG.Gateway.BuildProgressDialog::SetResult(string, bool)
		IL_0010: newobj instance void Philips.PSG.Gateway.BuildProgressDialog/Invoke_SetResult::.ctor(object, native int)
		IL_0015: ldc.i4.2
		IL_0016: newarr [mscorlib]System.Object
		IL_001b: dup
		IL_001c: ldc.i4.0
		IL_001d: ldarg.1
		IL_001e: stelem.ref
		IL_001f: dup
		IL_0020: ldc.i4.1
		IL_0021: ldarg.2
		IL_0022: box [mscorlib]System.Boolean
		IL_0027: stelem.ref
		IL_0028: call instance object [System.Windows.Forms]System.Windows.Forms.Control::Invoke(class [mscorlib]System.Delegate, object[])
		IL_002d: pop
		IL_002e: ret

		IL_002f: ldarg.0
		IL_0030: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::resultLabel
		IL_0035: ldarg.1
		IL_0036: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_003b: ldarg.0
		IL_003c: ldarg.2
		IL_003d: call instance void Philips.PSG.Gateway.BuildProgressDialog::SetCompletedDisplay(bool)
		IL_0042: ret
	} // end of method BuildProgressDialog::SetResult

	.method private hidebysig 
		instance void SetBuildingDisplay () cil managed 
	{
		// Method begins at RVA 0x39f7
		// Header size: 1
		// Code size: 9 (0x9)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldc.i4.1
		IL_0002: ldc.i4.0
		IL_0003: call instance void Philips.PSG.Gateway.BuildProgressDialog::SetDisplay(bool, bool)
		IL_0008: ret
	} // end of method BuildProgressDialog::SetBuildingDisplay

	.method private hidebysig 
		instance void SetCompletedDisplay (
			bool viewLogButtonVisible
		) cil managed 
	{
		// Method begins at RVA 0x3a01
		// Header size: 1
		// Code size: 9 (0x9)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldc.i4.0
		IL_0002: ldarg.1
		IL_0003: call instance void Philips.PSG.Gateway.BuildProgressDialog::SetDisplay(bool, bool)
		IL_0008: ret
	} // end of method BuildProgressDialog::SetCompletedDisplay

	.method private hidebysig 
		instance void SetDisplay (
			bool building,
			bool viewLogButtonVisible
		) cil managed 
	{
		// Method begins at RVA 0x3a0c
		// Header size: 12
		// Code size: 107 (0x6b)
		.maxstack 2

		IL_0000: ldarg.1
		IL_0001: brfalse.s IL_001c

		IL_0003: ldarg.0
		IL_0004: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_0009: ldc.i4.1
		IL_000a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Visible(bool)
		IL_000f: ldarg.0
		IL_0010: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_0015: ldc.i4.0
		IL_0016: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Visible(bool)
		IL_001b: ret

		IL_001c: ldarg.0
		IL_001d: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_0022: ldc.i4.1
		IL_0023: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Visible(bool)
		IL_0028: ldarg.0
		IL_0029: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_002e: ldc.i4.0
		IL_002f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Visible(bool)
		IL_0034: ldarg.0
		IL_0035: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_003a: ldarg.2
		IL_003b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Visible(bool)
		IL_0040: ldarg.2
		IL_0041: brfalse.s IL_0064

		IL_0043: ldarg.0
		IL_0044: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_0049: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_004e: callvirt instance string Philips.PSG.Gateway.Config::get_BuildLogFile()
		IL_0053: call bool [mscorlib]System.IO.File::Exists(string)
		IL_0058: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Enabled(bool)
		IL_005d: ldarg.0
		IL_005e: call instance void [System.Windows.Forms]System.Windows.Forms.Form::Close()
		IL_0063: ret

		IL_0064: ldarg.0
		IL_0065: call instance void [System.Windows.Forms]System.Windows.Forms.Form::Close()
		IL_006a: ret
	} // end of method BuildProgressDialog::SetDisplay

	.method private hidebysig 
		instance void okButton_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x3a83
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Form::Close()
		IL_0006: ret
	} // end of method BuildProgressDialog::okButton_Click

	.method private hidebysig 
		instance void viewLogButton_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x3a8c
		// Header size: 12
		// Code size: 22 (0x16)
		.maxstack 1

		.try
		{
			IL_0000: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
			IL_0005: callvirt instance string Philips.PSG.Gateway.Config::get_BuildLogFile()
			IL_000a: call class [System]System.Diagnostics.Process [System]System.Diagnostics.Process::Start(string)
			IL_000f: pop
			IL_0010: leave.s IL_0015
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_0012: pop
			IL_0013: leave.s IL_0015
		} // end handler

		IL_0015: ret
	} // end of method BuildProgressDialog::viewLogButton_Click

	.method family hidebysig virtual 
		instance void Dispose (
			bool disposing
		) cil managed 
	{
		// Method begins at RVA 0x3ac0
		// Header size: 1
		// Code size: 30 (0x1e)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brfalse.s IL_0016

		IL_0003: ldarg.0
		IL_0004: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.BuildProgressDialog::components
		IL_0009: brfalse.s IL_0016

		IL_000b: ldarg.0
		IL_000c: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.BuildProgressDialog::components
		IL_0011: callvirt instance void [mscorlib]System.IDisposable::Dispose()

		IL_0016: ldarg.0
		IL_0017: ldarg.1
		IL_0018: call instance void [System.Windows.Forms]System.Windows.Forms.Form::Dispose(bool)
		IL_001d: ret
	} // end of method BuildProgressDialog::Dispose

	.method private hidebysig 
		instance void InitializeComponent () cil managed 
	{
		// Method begins at RVA 0x3ae0
		// Header size: 12
		// Code size: 1175 (0x497)
		.maxstack 3

		IL_0000: ldarg.0
		IL_0001: newobj instance void [System.Windows.Forms]System.Windows.Forms.Panel::.ctor()
		IL_0006: stfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_000b: ldarg.0
		IL_000c: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0011: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::actionLabel
		IL_0016: ldarg.0
		IL_0017: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_001c: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::tvModelLabel
		IL_0021: ldarg.0
		IL_0022: newobj instance void [System.Windows.Forms]System.Windows.Forms.ProgressBar::.ctor()
		IL_0027: stfld class [System.Windows.Forms]System.Windows.Forms.ProgressBar Philips.PSG.Gateway.BuildProgressDialog::progressBar
		IL_002c: ldarg.0
		IL_002d: newobj instance void [System.Windows.Forms]System.Windows.Forms.Panel::.ctor()
		IL_0032: stfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_0037: ldarg.0
		IL_0038: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_003d: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_0042: ldarg.0
		IL_0043: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_0048: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::okButton
		IL_004d: ldarg.0
		IL_004e: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0053: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::resultLabel
		IL_0058: ldarg.0
		IL_0059: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_005e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::SuspendLayout()
		IL_0063: ldarg.0
		IL_0064: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_0069: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::SuspendLayout()
		IL_006e: ldarg.0
		IL_006f: call instance void [System.Windows.Forms]System.Windows.Forms.Control::SuspendLayout()
		IL_0074: ldarg.0
		IL_0075: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_007a: callvirt instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_007f: ldarg.0
		IL_0080: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::actionLabel
		IL_0085: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_008a: ldarg.0
		IL_008b: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_0090: callvirt instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0095: ldarg.0
		IL_0096: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::tvModelLabel
		IL_009b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_00a0: ldarg.0
		IL_00a1: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_00a6: callvirt instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_00ab: ldarg.0
		IL_00ac: ldfld class [System.Windows.Forms]System.Windows.Forms.ProgressBar Philips.PSG.Gateway.BuildProgressDialog::progressBar
		IL_00b1: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_00b6: ldarg.0
		IL_00b7: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_00bc: ldc.i4.s 13
		IL_00be: ldc.i4.s 13
		IL_00c0: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_00c5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_00ca: ldarg.0
		IL_00cb: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_00d0: ldstr "buildingControlsBox"
		IL_00d5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_00da: ldarg.0
		IL_00db: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_00e0: ldc.i4 267
		IL_00e5: ldc.i4 137
		IL_00ea: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_00ef: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_00f4: ldarg.0
		IL_00f5: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_00fa: ldc.i4.0
		IL_00fb: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0100: ldarg.0
		IL_0101: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::actionLabel
		IL_0106: ldc.i4.1
		IL_0107: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_AutoSize(bool)
		IL_010c: ldarg.0
		IL_010d: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::actionLabel
		IL_0112: ldc.i4.s 13
		IL_0114: ldc.i4.s 81
		IL_0116: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_011b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0120: ldarg.0
		IL_0121: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::actionLabel
		IL_0126: ldstr "actionLabel"
		IL_012b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0130: ldarg.0
		IL_0131: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::actionLabel
		IL_0136: ldc.i4.s 37
		IL_0138: ldc.i4.s 13
		IL_013a: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_013f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0144: ldarg.0
		IL_0145: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::actionLabel
		IL_014a: ldc.i4.2
		IL_014b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0150: ldarg.0
		IL_0151: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::actionLabel
		IL_0156: ldstr "Action"
		IL_015b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0160: ldarg.0
		IL_0161: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::tvModelLabel
		IL_0166: ldc.i4.1
		IL_0167: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_AutoSize(bool)
		IL_016c: ldarg.0
		IL_016d: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::tvModelLabel
		IL_0172: ldc.i4.s 13
		IL_0174: ldc.i4.s 13
		IL_0176: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_017b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0180: ldarg.0
		IL_0181: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::tvModelLabel
		IL_0186: ldstr "tvModelLabel"
		IL_018b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0190: ldarg.0
		IL_0191: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::tvModelLabel
		IL_0196: ldc.i4.s 49
		IL_0198: ldc.i4.s 13
		IL_019a: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_019f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_01a4: ldarg.0
		IL_01a5: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::tvModelLabel
		IL_01aa: ldc.i4.1
		IL_01ab: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_01b0: ldarg.0
		IL_01b1: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::tvModelLabel
		IL_01b6: ldstr "TvModel"
		IL_01bb: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_01c0: ldarg.0
		IL_01c1: ldfld class [System.Windows.Forms]System.Windows.Forms.ProgressBar Philips.PSG.Gateway.BuildProgressDialog::progressBar
		IL_01c6: ldc.i4.s 13
		IL_01c8: ldc.i4.s 43
		IL_01ca: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_01cf: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_01d4: ldarg.0
		IL_01d5: ldfld class [System.Windows.Forms]System.Windows.Forms.ProgressBar Philips.PSG.Gateway.BuildProgressDialog::progressBar
		IL_01da: ldstr "progressBar"
		IL_01df: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_01e4: ldarg.0
		IL_01e5: ldfld class [System.Windows.Forms]System.Windows.Forms.ProgressBar Philips.PSG.Gateway.BuildProgressDialog::progressBar
		IL_01ea: ldc.i4 241
		IL_01ef: ldc.i4.s 23
		IL_01f1: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_01f6: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_01fb: ldarg.0
		IL_01fc: ldfld class [System.Windows.Forms]System.Windows.Forms.ProgressBar Philips.PSG.Gateway.BuildProgressDialog::progressBar
		IL_0201: ldc.i4.2
		IL_0202: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ProgressBar::set_Style(valuetype [System.Windows.Forms]System.Windows.Forms.ProgressBarStyle)
		IL_0207: ldarg.0
		IL_0208: ldfld class [System.Windows.Forms]System.Windows.Forms.ProgressBar Philips.PSG.Gateway.BuildProgressDialog::progressBar
		IL_020d: ldc.i4.0
		IL_020e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0213: ldarg.0
		IL_0214: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_0219: callvirt instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_021e: ldarg.0
		IL_021f: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_0224: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0229: ldarg.0
		IL_022a: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_022f: callvirt instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0234: ldarg.0
		IL_0235: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::okButton
		IL_023a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_023f: ldarg.0
		IL_0240: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_0245: callvirt instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_024a: ldarg.0
		IL_024b: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::resultLabel
		IL_0250: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0255: ldarg.0
		IL_0256: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_025b: ldc.i4.s 13
		IL_025d: ldc.i4.s 13
		IL_025f: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0264: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0269: ldarg.0
		IL_026a: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_026f: ldstr "completedControlsBox"
		IL_0274: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0279: ldarg.0
		IL_027a: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_027f: ldc.i4 267
		IL_0284: ldc.i4 137
		IL_0289: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_028e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0293: ldarg.0
		IL_0294: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_0299: ldc.i4.1
		IL_029a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_029f: ldarg.0
		IL_02a0: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_02a5: ldc.i4.s 107
		IL_02a7: ldc.i4.s 110
		IL_02a9: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_02ae: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_02b3: ldarg.0
		IL_02b4: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_02b9: ldstr "viewLogButton"
		IL_02be: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_02c3: ldarg.0
		IL_02c4: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_02c9: ldc.i4.s 75
		IL_02cb: ldc.i4.s 23
		IL_02cd: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_02d2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_02d7: ldarg.0
		IL_02d8: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_02dd: ldc.i4.2
		IL_02de: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_02e3: ldarg.0
		IL_02e4: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_02e9: ldstr "ViewLog"
		IL_02ee: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_02f3: ldarg.0
		IL_02f4: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_02f9: ldc.i4.1
		IL_02fa: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ButtonBase::set_UseVisualStyleBackColor(bool)
		IL_02ff: ldarg.0
		IL_0300: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::viewLogButton
		IL_0305: ldarg.0
		IL_0306: ldftn instance void Philips.PSG.Gateway.BuildProgressDialog::viewLogButton_Click(object, class [mscorlib]System.EventArgs)
		IL_030c: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_0311: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::add_Click(class [mscorlib]System.EventHandler)
		IL_0316: ldarg.0
		IL_0317: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::okButton
		IL_031c: ldc.i4 188
		IL_0321: ldc.i4.s 110
		IL_0323: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0328: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_032d: ldarg.0
		IL_032e: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::okButton
		IL_0333: ldstr "okButton"
		IL_0338: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_033d: ldarg.0
		IL_033e: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::okButton
		IL_0343: ldc.i4.s 75
		IL_0345: ldc.i4.s 23
		IL_0347: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_034c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0351: ldarg.0
		IL_0352: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::okButton
		IL_0357: ldc.i4.1
		IL_0358: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_035d: ldarg.0
		IL_035e: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::okButton
		IL_0363: ldstr "OK"
		IL_0368: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_036d: ldarg.0
		IL_036e: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::okButton
		IL_0373: ldc.i4.1
		IL_0374: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ButtonBase::set_UseVisualStyleBackColor(bool)
		IL_0379: ldarg.0
		IL_037a: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.BuildProgressDialog::okButton
		IL_037f: ldarg.0
		IL_0380: ldftn instance void Philips.PSG.Gateway.BuildProgressDialog::okButton_Click(object, class [mscorlib]System.EventArgs)
		IL_0386: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_038b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::add_Click(class [mscorlib]System.EventHandler)
		IL_0390: ldarg.0
		IL_0391: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::resultLabel
		IL_0396: ldc.i4.s 13
		IL_0398: ldc.i4.s 13
		IL_039a: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_039f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_03a4: ldarg.0
		IL_03a5: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::resultLabel
		IL_03aa: ldstr "resultLabel"
		IL_03af: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_03b4: ldarg.0
		IL_03b5: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::resultLabel
		IL_03ba: ldc.i4 240
		IL_03bf: ldc.i4.s 81
		IL_03c1: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_03c6: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_03cb: ldarg.0
		IL_03cc: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::resultLabel
		IL_03d1: ldc.i4.0
		IL_03d2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_03d7: ldarg.0
		IL_03d8: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.BuildProgressDialog::resultLabel
		IL_03dd: ldstr "Result"
		IL_03e2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_03e7: ldarg.0
		IL_03e8: ldc.i4.0
		IL_03e9: call instance void [System.Windows.Forms]System.Windows.Forms.ContainerControl::set_AutoScaleMode(valuetype [System.Windows.Forms]System.Windows.Forms.AutoScaleMode)
		IL_03ee: ldarg.0
		IL_03ef: ldc.i4 292
		IL_03f4: ldc.i4 161
		IL_03f9: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_03fe: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ClientSize(valuetype [System.Drawing]System.Drawing.Size)
		IL_0403: ldarg.0
		IL_0404: ldc.i4.0
		IL_0405: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ControlBox(bool)
		IL_040a: ldarg.0
		IL_040b: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0410: ldarg.0
		IL_0411: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_0416: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_041b: ldarg.0
		IL_041c: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0421: ldarg.0
		IL_0422: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_0427: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_042c: ldarg.0
		IL_042d: ldc.i4.3
		IL_042e: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_FormBorderStyle(valuetype [System.Windows.Forms]System.Windows.Forms.FormBorderStyle)
		IL_0433: ldarg.0
		IL_0434: ldc.i4.0
		IL_0435: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_MaximizeBox(bool)
		IL_043a: ldarg.0
		IL_043b: ldc.i4.0
		IL_043c: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_MinimizeBox(bool)
		IL_0441: ldarg.0
		IL_0442: ldstr "BuildProgressDialog"
		IL_0447: call instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_044c: ldarg.0
		IL_044d: ldc.i4.0
		IL_044e: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ShowIcon(bool)
		IL_0453: ldarg.0
		IL_0454: ldc.i4.0
		IL_0455: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ShowInTaskbar(bool)
		IL_045a: ldarg.0
		IL_045b: ldc.i4.4
		IL_045c: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_StartPosition(valuetype [System.Windows.Forms]System.Windows.Forms.FormStartPosition)
		IL_0461: ldarg.0
		IL_0462: ldstr "BuildProgressDialog"
		IL_0467: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_046c: ldarg.0
		IL_046d: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_0472: ldc.i4.0
		IL_0473: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::ResumeLayout(bool)
		IL_0478: ldarg.0
		IL_0479: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::buildingControlsBox
		IL_047e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::PerformLayout()
		IL_0483: ldarg.0
		IL_0484: ldfld class [System.Windows.Forms]System.Windows.Forms.Panel Philips.PSG.Gateway.BuildProgressDialog::completedControlsBox
		IL_0489: ldc.i4.0
		IL_048a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::ResumeLayout(bool)
		IL_048f: ldarg.0
		IL_0490: ldc.i4.0
		IL_0491: call instance void [System.Windows.Forms]System.Windows.Forms.Control::ResumeLayout(bool)
		IL_0496: ret
	} // end of method BuildProgressDialog::InitializeComponent

} // end of class Philips.PSG.Gateway.BuildProgressDialog

.class public auto ansi serializable beforefieldinit Philips.PSG.Gateway.Config
	extends [mscorlib]System.Object
{
	// Fields
	.field public static literal int32 OUTPUT_CHANNEL_MIN = int32(0)
	.field public static literal int32 OUTPUT_CHANNEL_MAX = int32(68)
	.field public static literal int32 OUTPUT_CHANNEL_DEFAULT = int32(50)
	.field public static literal int32 OUTPUT_CHANNEL_BANDWIDTH = int32(8)
	.field public static literal int32 OUTPUT_CHANNEL_MIN_FREQUENCY = int32(450)
	.field public static literal int32 OUTPUT_CHANNEL_MAX_FREQUENCY = int32(8640)
	.field public static literal int32 OUTPUT_CHANNEL_DEFAULT_FREQUENCY = int32(7060)
	.field public static string TRANSMISSION_MODE_2K_STRING
	.field public static string TRANSMISSION_MODE_4K_STRING
	.field public static string TRANSMISSION_MODE_8K_STRING
	.field public static string GUARD_INTERVAL_1_4_STRING
	.field public static string GUARD_INTERVAL_1_8_STRING
	.field public static string GUARD_INTERVAL_1_16_STRING
	.field public static string GUARD_INTERVAL_1_32_STRING
	.field public static string CONSTELLATION_QPSK_STRING
	.field public static string CONSTELLATION_QAM16_STRING
	.field public static string CONSTELLATION_QAM64_STRING
	.field public static string CODE_RATE_1_2_STRING
	.field public static string CODE_RATE_2_3_STRING
	.field public static string CODE_RATE_3_4_STRING
	.field public static string CODE_RATE_4_5_STRING
	.field public static string CODE_RATE_5_6_STRING
	.field public static string CODE_RATE_6_7_STRING
	.field public static string CODE_RATE_7_8_STRING
	.field public static string MODULATION_TYPE_DVBT_STRING
	.field public static string DEVICE_CAPABILITY_FLAG_DVBT_STRING
	.field public static string DEVICE_CAPABILITY_FLAG_UHF_STRING
	.field public static string DEVICE_STREAM_TYPE_TS_MOD
	.field public static string TX_MODE_188_STRING
	.field public static string TX_MODE_ADD16_STRING
	.field public static string TX_MODE_130_STRING
	.field public static string TX_MODE_192_STRING
	.field public static string TX_MODE_204_STRING
	.field public static string TX_MODE_MIN16_STRING
	.field public static string TX_MODE_RAW_STRING
	.field public static string STUFF_MODE_ON_STRING
	.field public static string STUFF_MODE_OFF_STRING
	.field public static string BANDWIDTH_5MHZ_STRING
	.field public static string BANDWIDTH_6MHZ_STRING
	.field public static string BANDWIDTH_7MHZ_STRING
	.field public static string BANDWIDTH_8MHZ_STRING
	.field public string TsModelName
	.field private string _buildRootFolder
	.field private string _workingRootFolder
	.field private class [DTAPINET]DTAPINET.DtCaps _deviceCapabilityFlags
	.field private int32 _transmissionMode
	.field private int32 _guardInterval
	.field private int32 _constellation
	.field private int32 _codeRate
	.field private int32 _modulationType
	.field private int32 _deviceStreamType
	.field private int32 _txMode
	.field private int32 _stuffMode
	.field private int32 _bandwidth
	.field private int32 _outputChannel
	.field private int32 _modulationFrequency
	.field private float64 _outputLevel
	.field private string '<OriginalNetworkId>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<ProgNum>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<Pid>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<Oui>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<TxnId>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<DwnldId>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<NetworkId>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<TransportStreamId>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<PcrPid>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private uint16 '<Onid>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<RoomNumber>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<SsbIdentifier>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private string '<ChannelTableIdentifier>k__BackingField'
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x3f84
		// Header size: 12
		// Code size: 163 (0xa3)
		.maxstack 3

		IL_0000: ldarg.0
		IL_0001: ldstr "none"
		IL_0006: stfld string Philips.PSG.Gateway.Config::TsModelName
		IL_000b: ldarg.0
		IL_000c: call string Philips.PSG.Gateway.Settings::get_DefaultBuildRootFolder()
		IL_0011: stfld string Philips.PSG.Gateway.Config::_buildRootFolder
		IL_0016: ldarg.0
		IL_0017: call string Philips.PSG.Gateway.Settings::get_DefaultWorkingRootFolder()
		IL_001c: stfld string Philips.PSG.Gateway.Config::_workingRootFolder
		IL_0021: ldarg.0
		IL_0022: ldsfld class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst) [DTAPINET]DTAPINET.DTAPI::CAP_TX_DVBT
		IL_0027: ldsfld class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst) [DTAPINET]DTAPINET.DTAPI::CAP_UHF
		IL_002c: call class [DTAPINET]DTAPINET.DtCaps [DTAPINET]DTAPINET.DtCaps::op_BitwiseOr(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
		IL_0031: stfld class [DTAPINET]DTAPINET.DtCaps Philips.PSG.Gateway.Config::_deviceCapabilityFlags
		IL_0036: ldarg.0
		IL_0037: ldc.i4 65536
		IL_003c: stfld int32 Philips.PSG.Gateway.Config::_transmissionMode
		IL_0041: ldarg.0
		IL_0042: ldc.i4 256
		IL_0047: stfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_004c: ldarg.0
		IL_004d: ldc.i4.s 48
		IL_004f: stfld int32 Philips.PSG.Gateway.Config::_constellation
		IL_0054: ldarg.0
		IL_0055: ldc.i4.6
		IL_0056: stfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_005b: ldarg.0
		IL_005c: ldc.i4.s 9
		IL_005e: stfld int32 Philips.PSG.Gateway.Config::_modulationType
		IL_0063: ldarg.0
		IL_0064: ldc.i4.s 16
		IL_0066: stfld int32 Philips.PSG.Gateway.Config::_deviceStreamType
		IL_006b: ldarg.0
		IL_006c: ldc.i4.s 17
		IL_006e: stfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_0073: ldarg.0
		IL_0074: ldc.i4.4
		IL_0075: stfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_007a: ldarg.0
		IL_007b: ldc.i4.s 50
		IL_007d: stfld int32 Philips.PSG.Gateway.Config::_outputChannel
		IL_0082: ldarg.0
		IL_0083: ldc.i4 7060
		IL_0088: stfld int32 Philips.PSG.Gateway.Config::_modulationFrequency
		IL_008d: ldarg.0
		IL_008e: ldc.r8 -27.5
		IL_0097: stfld float64 Philips.PSG.Gateway.Config::_outputLevel
		IL_009c: ldarg.0
		IL_009d: call instance void [mscorlib]System.Object::.ctor()
		IL_00a2: ret
	} // end of method Config::.ctor

	.method public hidebysig specialname 
		instance string get_BuildRootFolder () cil managed 
	{
		// Method begins at RVA 0x4033
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::_buildRootFolder
		IL_0006: ret
	} // end of method Config::get_BuildRootFolder

	.method public hidebysig specialname 
		instance void set_BuildRootFolder (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x403b
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brtrue.s IL_000f

		IL_0003: ldarg.0
		IL_0004: ldsfld string [mscorlib]System.String::Empty
		IL_0009: stfld string Philips.PSG.Gateway.Config::_buildRootFolder
		IL_000e: ret

		IL_000f: ldarg.0
		IL_0010: ldarg.1
		IL_0011: stfld string Philips.PSG.Gateway.Config::_buildRootFolder
		IL_0016: ret
	} // end of method Config::set_BuildRootFolder

	.method public hidebysig specialname 
		instance string get_WorkingRootFolder () cil managed 
	{
		// Method begins at RVA 0x4053
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::_workingRootFolder
		IL_0006: ret
	} // end of method Config::get_WorkingRootFolder

	.method public hidebysig specialname 
		instance void set_WorkingRootFolder (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x405b
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brtrue.s IL_000f

		IL_0003: ldarg.0
		IL_0004: ldsfld string [mscorlib]System.String::Empty
		IL_0009: stfld string Philips.PSG.Gateway.Config::_workingRootFolder
		IL_000e: ret

		IL_000f: ldarg.0
		IL_0010: ldarg.1
		IL_0011: stfld string Philips.PSG.Gateway.Config::_workingRootFolder
		IL_0016: ret
	} // end of method Config::set_WorkingRootFolder

	.method public hidebysig specialname 
		instance string get_ScheduleDataFolder () cil managed 
	{
		// Method begins at RVA 0x4073
		// Header size: 1
		// Code size: 22 (0x16)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::_workingRootFolder
		IL_0006: ldstr "\\"
		IL_000b: call string Philips.PSG.Gateway.Settings::get_ScheduleDataFolderName()
		IL_0010: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0015: ret
	} // end of method Config::get_ScheduleDataFolder

	.method public hidebysig specialname 
		instance string get_OutputDataFolder () cil managed 
	{
		// Method begins at RVA 0x408a
		// Header size: 1
		// Code size: 22 (0x16)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::_workingRootFolder
		IL_0006: ldstr "\\"
		IL_000b: call string Philips.PSG.Gateway.Settings::get_OutputDataFolderName()
		IL_0010: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0015: ret
	} // end of method Config::get_OutputDataFolder

	.method public hidebysig specialname 
		instance string get_PreviousScheduleDataFolder () cil managed 
	{
		// Method begins at RVA 0x40a1
		// Header size: 1
		// Code size: 22 (0x16)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::_workingRootFolder
		IL_0006: ldstr "\\"
		IL_000b: call string Philips.PSG.Gateway.Settings::get_PreviousScheduleDataFolderName()
		IL_0010: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0015: ret
	} // end of method Config::get_PreviousScheduleDataFolder

	.method public hidebysig specialname 
		instance string get_PreviousOutputDataFolder () cil managed 
	{
		// Method begins at RVA 0x40b8
		// Header size: 1
		// Code size: 22 (0x16)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::_workingRootFolder
		IL_0006: ldstr "\\"
		IL_000b: call string Philips.PSG.Gateway.Settings::get_PreviousOutputDataFolderName()
		IL_0010: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0015: ret
	} // end of method Config::get_PreviousOutputDataFolder

	.method public hidebysig specialname 
		instance string get_ScheduleDataFile () cil managed 
	{
		// Method begins at RVA 0x40cf
		// Header size: 1
		// Code size: 22 (0x16)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance string Philips.PSG.Gateway.Config::get_ScheduleDataFolder()
		IL_0006: ldstr "\\"
		IL_000b: call string Philips.PSG.Gateway.Settings::get_ScheduleDataFileName()
		IL_0010: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0015: ret
	} // end of method Config::get_ScheduleDataFile

	.method public hidebysig specialname 
		instance string get_MultiplexedTsFile () cil managed 
	{
		// Method begins at RVA 0x40e6
		// Header size: 1
		// Code size: 22 (0x16)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance string Philips.PSG.Gateway.Config::get_OutputDataFolder()
		IL_0006: ldstr "\\"
		IL_000b: call string Philips.PSG.Gateway.Settings::get_MultiplexedTsFileName()
		IL_0010: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0015: ret
	} // end of method Config::get_MultiplexedTsFile

	.method public hidebysig specialname 
		instance string get_ScheduleRunFile () cil managed 
	{
		// Method begins at RVA 0x40fd
		// Header size: 1
		// Code size: 22 (0x16)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance string Philips.PSG.Gateway.Config::get_ScheduleDataFolder()
		IL_0006: ldstr "\\"
		IL_000b: call string Philips.PSG.Gateway.Settings::get_ScheduleRunFileName()
		IL_0010: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0015: ret
	} // end of method Config::get_ScheduleRunFile

	.method public hidebysig specialname 
		instance string get_TsPlayFile () cil managed 
	{
		// Method begins at RVA 0x4114
		// Header size: 1
		// Code size: 22 (0x16)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance string Philips.PSG.Gateway.Config::get_OutputDataFolder()
		IL_0006: ldstr "\\"
		IL_000b: call string Philips.PSG.Gateway.Settings::get_TsPlayFileName()
		IL_0010: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0015: ret
	} // end of method Config::get_TsPlayFile

	.method public hidebysig specialname 
		instance string get_BuildLogFile () cil managed 
	{
		// Method begins at RVA 0x412b
		// Header size: 1
		// Code size: 22 (0x16)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance string Philips.PSG.Gateway.Config::get_ScheduleDataFolder()
		IL_0006: ldstr "\\"
		IL_000b: call string Philips.PSG.Gateway.Settings::get_BuildLogFileName()
		IL_0010: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0015: ret
	} // end of method Config::get_BuildLogFile

	.method public hidebysig specialname 
		instance int32 get_TransmissionMode () cil managed 
	{
		// Method begins at RVA 0x4142
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_transmissionMode
		IL_0006: ret
	} // end of method Config::get_TransmissionMode

	.method public hidebysig specialname 
		instance void set_TransmissionMode (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x414a
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld int32 Philips.PSG.Gateway.Config::_transmissionMode
		IL_0007: ret
	} // end of method Config::set_TransmissionMode

	.method public hidebysig specialname 
		instance uint16 get_TransmissionModeTs () cil managed 
	{
		// Method begins at RVA 0x4154
		// Header size: 12
		// Code size: 66 (0x42)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_transmissionMode
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: ldc.i4 65536
		IL_000d: beq.s IL_0021

		IL_000f: ldloc.0
		IL_0010: ldc.i4 131072
		IL_0015: beq.s IL_0023

		IL_0017: ldloc.0
		IL_0018: ldc.i4 196608
		IL_001d: beq.s IL_0025

		IL_001f: br.s IL_0027

		IL_0021: ldc.i4.0
		IL_0022: ret

		IL_0023: ldc.i4.1
		IL_0024: ret

		IL_0025: ldc.i4.1
		IL_0026: ret

		IL_0027: ldstr "Unrecognised TransmissionMode value for TS: {0}"
		IL_002c: ldarg.0
		IL_002d: ldfld int32 Philips.PSG.Gateway.Config::_transmissionMode
		IL_0032: box [mscorlib]System.Int32
		IL_0037: call string [mscorlib]System.String::Format(string, object)
		IL_003c: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_0041: throw
	} // end of method Config::get_TransmissionModeTs

	.method public hidebysig specialname 
		instance string get_TransmissionModeAsString () cil managed 
	{
		// Method begins at RVA 0x41a4
		// Header size: 12
		// Code size: 78 (0x4e)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_transmissionMode
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: ldc.i4 65536
		IL_000d: beq.s IL_0021

		IL_000f: ldloc.0
		IL_0010: ldc.i4 131072
		IL_0015: beq.s IL_0027

		IL_0017: ldloc.0
		IL_0018: ldc.i4 196608
		IL_001d: beq.s IL_002d

		IL_001f: br.s IL_0033

		IL_0021: ldsfld string Philips.PSG.Gateway.Config::TRANSMISSION_MODE_2K_STRING
		IL_0026: ret

		IL_0027: ldsfld string Philips.PSG.Gateway.Config::TRANSMISSION_MODE_4K_STRING
		IL_002c: ret

		IL_002d: ldsfld string Philips.PSG.Gateway.Config::TRANSMISSION_MODE_8K_STRING
		IL_0032: ret

		IL_0033: ldstr "Unrecognised TransmissionMode value: {0}"
		IL_0038: ldarg.0
		IL_0039: ldfld int32 Philips.PSG.Gateway.Config::_transmissionMode
		IL_003e: box [mscorlib]System.Int32
		IL_0043: call string [mscorlib]System.String::Format(string, object)
		IL_0048: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_004d: throw
	} // end of method Config::get_TransmissionModeAsString

	.method public hidebysig specialname 
		instance void set_TransmissionModeAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4200
		// Header size: 12
		// Code size: 92 (0x5c)
		.maxstack 2

		IL_0000: ldarg.1
		IL_0001: ldsfld string Philips.PSG.Gateway.Config::TRANSMISSION_MODE_2K_STRING
		IL_0006: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_000b: brfalse.s IL_0019

		IL_000d: ldarg.0
		IL_000e: ldc.i4 65536
		IL_0013: stfld int32 Philips.PSG.Gateway.Config::_transmissionMode
		IL_0018: ret

		IL_0019: ldarg.1
		IL_001a: ldsfld string Philips.PSG.Gateway.Config::TRANSMISSION_MODE_4K_STRING
		IL_001f: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0024: brfalse.s IL_0032

		IL_0026: ldarg.0
		IL_0027: ldc.i4 131072
		IL_002c: stfld int32 Philips.PSG.Gateway.Config::_transmissionMode
		IL_0031: ret

		IL_0032: ldarg.1
		IL_0033: ldsfld string Philips.PSG.Gateway.Config::TRANSMISSION_MODE_8K_STRING
		IL_0038: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_003d: brfalse.s IL_004b

		IL_003f: ldarg.0
		IL_0040: ldc.i4 196608
		IL_0045: stfld int32 Philips.PSG.Gateway.Config::_transmissionMode
		IL_004a: ret

		IL_004b: ldstr "Unrecognised TransmissionMode string value: '{0}'"
		IL_0050: ldarg.1
		IL_0051: call string [mscorlib]System.String::Format(string, object)
		IL_0056: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_005b: throw
	} // end of method Config::set_TransmissionModeAsString

	.method public hidebysig specialname 
		instance int32 get_GuardInterval () cil managed 
	{
		// Method begins at RVA 0x4268
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_0006: ret
	} // end of method Config::get_GuardInterval

	.method public hidebysig specialname 
		instance void set_GuardInterval (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4270
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_0007: ret
	} // end of method Config::set_GuardInterval

	.method public hidebysig specialname 
		instance uint16 get_GuardIntervalTs () cil managed 
	{
		// Method begins at RVA 0x427c
		// Header size: 12
		// Code size: 84 (0x54)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: ldc.i4 512
		IL_000d: bgt.s IL_0021

		IL_000f: ldloc.0
		IL_0010: ldc.i4 256
		IL_0015: beq.s IL_0037

		IL_0017: ldloc.0
		IL_0018: ldc.i4 512
		IL_001d: beq.s IL_0035

		IL_001f: br.s IL_0039

		IL_0021: ldloc.0
		IL_0022: ldc.i4 768
		IL_0027: beq.s IL_0033

		IL_0029: ldloc.0
		IL_002a: ldc.i4 1024
		IL_002f: bne.un.s IL_0039

		IL_0031: ldc.i4.3
		IL_0032: ret

		IL_0033: ldc.i4.2
		IL_0034: ret

		IL_0035: ldc.i4.1
		IL_0036: ret

		IL_0037: ldc.i4.0
		IL_0038: ret

		IL_0039: ldstr "Unrecognised GuardInterval value for TS: {0}"
		IL_003e: ldarg.0
		IL_003f: ldfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_0044: box [mscorlib]System.Int32
		IL_0049: call string [mscorlib]System.String::Format(string, object)
		IL_004e: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_0053: throw
	} // end of method Config::get_GuardIntervalTs

	.method public hidebysig specialname 
		instance string get_GuardIntervalAsString () cil managed 
	{
		// Method begins at RVA 0x42dc
		// Header size: 12
		// Code size: 100 (0x64)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: ldc.i4 512
		IL_000d: bgt.s IL_0021

		IL_000f: ldloc.0
		IL_0010: ldc.i4 256
		IL_0015: beq.s IL_0043

		IL_0017: ldloc.0
		IL_0018: ldc.i4 512
		IL_001d: beq.s IL_003d

		IL_001f: br.s IL_0049

		IL_0021: ldloc.0
		IL_0022: ldc.i4 768
		IL_0027: beq.s IL_0037

		IL_0029: ldloc.0
		IL_002a: ldc.i4 1024
		IL_002f: bne.un.s IL_0049

		IL_0031: ldsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_4_STRING
		IL_0036: ret

		IL_0037: ldsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_8_STRING
		IL_003c: ret

		IL_003d: ldsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_16_STRING
		IL_0042: ret

		IL_0043: ldsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_32_STRING
		IL_0048: ret

		IL_0049: ldstr "Unrecognised GuardInterval value: {0}"
		IL_004e: ldarg.0
		IL_004f: ldfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_0054: box [mscorlib]System.Int32
		IL_0059: call string [mscorlib]System.String::Format(string, object)
		IL_005e: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_0063: throw
	} // end of method Config::get_GuardIntervalAsString

	.method public hidebysig specialname 
		instance void set_GuardIntervalAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x434c
		// Header size: 12
		// Code size: 117 (0x75)
		.maxstack 2

		IL_0000: ldarg.1
		IL_0001: ldsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_4_STRING
		IL_0006: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_000b: brfalse.s IL_0019

		IL_000d: ldarg.0
		IL_000e: ldc.i4 1024
		IL_0013: stfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_0018: ret

		IL_0019: ldarg.1
		IL_001a: ldsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_8_STRING
		IL_001f: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0024: brfalse.s IL_0032

		IL_0026: ldarg.0
		IL_0027: ldc.i4 768
		IL_002c: stfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_0031: ret

		IL_0032: ldarg.1
		IL_0033: ldsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_16_STRING
		IL_0038: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_003d: brfalse.s IL_004b

		IL_003f: ldarg.0
		IL_0040: ldc.i4 512
		IL_0045: stfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_004a: ret

		IL_004b: ldarg.1
		IL_004c: ldsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_32_STRING
		IL_0051: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0056: brfalse.s IL_0064

		IL_0058: ldarg.0
		IL_0059: ldc.i4 256
		IL_005e: stfld int32 Philips.PSG.Gateway.Config::_guardInterval
		IL_0063: ret

		IL_0064: ldstr "Unrecognised GuardInterval string value: '{0}'"
		IL_0069: ldarg.1
		IL_006a: call string [mscorlib]System.String::Format(string, object)
		IL_006f: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_0074: throw
	} // end of method Config::set_GuardIntervalAsString

	.method public hidebysig specialname 
		instance int32 get_Constellation () cil managed 
	{
		// Method begins at RVA 0x43cd
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_constellation
		IL_0006: ret
	} // end of method Config::get_Constellation

	.method public hidebysig specialname 
		instance void set_Constellation (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x43d5
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld int32 Philips.PSG.Gateway.Config::_constellation
		IL_0007: ret
	} // end of method Config::set_Constellation

	.method public hidebysig specialname 
		instance uint16 get_ConstellationTs () cil managed 
	{
		// Method begins at RVA 0x43e0
		// Header size: 12
		// Code size: 57 (0x39)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_constellation
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: ldc.i4.s 16
		IL_000a: beq.s IL_0018

		IL_000c: ldloc.0
		IL_000d: ldc.i4.s 32
		IL_000f: beq.s IL_001a

		IL_0011: ldloc.0
		IL_0012: ldc.i4.s 48
		IL_0014: beq.s IL_001c

		IL_0016: br.s IL_001e

		IL_0018: ldc.i4.0
		IL_0019: ret

		IL_001a: ldc.i4.1
		IL_001b: ret

		IL_001c: ldc.i4.2
		IL_001d: ret

		IL_001e: ldstr "Unrecognised Constellation value for TS: {0}"
		IL_0023: ldarg.0
		IL_0024: ldfld int32 Philips.PSG.Gateway.Config::_constellation
		IL_0029: box [mscorlib]System.Int32
		IL_002e: call string [mscorlib]System.String::Format(string, object)
		IL_0033: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_0038: throw
	} // end of method Config::get_ConstellationTs

	.method public hidebysig specialname 
		instance string get_ConstellationAsString () cil managed 
	{
		// Method begins at RVA 0x4428
		// Header size: 12
		// Code size: 69 (0x45)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_constellation
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: ldc.i4.s 16
		IL_000a: beq.s IL_0018

		IL_000c: ldloc.0
		IL_000d: ldc.i4.s 32
		IL_000f: beq.s IL_001e

		IL_0011: ldloc.0
		IL_0012: ldc.i4.s 48
		IL_0014: beq.s IL_0024

		IL_0016: br.s IL_002a

		IL_0018: ldsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QPSK_STRING
		IL_001d: ret

		IL_001e: ldsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QAM16_STRING
		IL_0023: ret

		IL_0024: ldsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QAM64_STRING
		IL_0029: ret

		IL_002a: ldstr "Unrecognised Constellation value: {0}"
		IL_002f: ldarg.0
		IL_0030: ldfld int32 Philips.PSG.Gateway.Config::_constellation
		IL_0035: box [mscorlib]System.Int32
		IL_003a: call string [mscorlib]System.String::Format(string, object)
		IL_003f: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_0044: throw
	} // end of method Config::get_ConstellationAsString

	.method public hidebysig specialname 
		instance void set_ConstellationAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x447c
		// Header size: 12
		// Code size: 83 (0x53)
		.maxstack 2

		IL_0000: ldarg.1
		IL_0001: ldsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QPSK_STRING
		IL_0006: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_000b: brfalse.s IL_0016

		IL_000d: ldarg.0
		IL_000e: ldc.i4.s 16
		IL_0010: stfld int32 Philips.PSG.Gateway.Config::_constellation
		IL_0015: ret

		IL_0016: ldarg.1
		IL_0017: ldsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QAM16_STRING
		IL_001c: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0021: brfalse.s IL_002c

		IL_0023: ldarg.0
		IL_0024: ldc.i4.s 32
		IL_0026: stfld int32 Philips.PSG.Gateway.Config::_constellation
		IL_002b: ret

		IL_002c: ldarg.1
		IL_002d: ldsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QAM64_STRING
		IL_0032: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0037: brfalse.s IL_0042

		IL_0039: ldarg.0
		IL_003a: ldc.i4.s 48
		IL_003c: stfld int32 Philips.PSG.Gateway.Config::_constellation
		IL_0041: ret

		IL_0042: ldstr "Unrecognised Constellation string value: '{0}'"
		IL_0047: ldarg.1
		IL_0048: call string [mscorlib]System.String::Format(string, object)
		IL_004d: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_0052: throw
	} // end of method Config::set_ConstellationAsString

	.method public hidebysig specialname 
		instance int32 get_CodeRate () cil managed 
	{
		// Method begins at RVA 0x44db
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0006: ret
	} // end of method Config::get_CodeRate

	.method public hidebysig specialname 
		instance void set_CodeRate (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x44e3
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0007: ret
	} // end of method Config::set_CodeRate

	.method public hidebysig specialname 
		instance uint16 get_CodeRateTs () cil managed 
	{
		// Method begins at RVA 0x44ec
		// Header size: 12
		// Code size: 80 (0x50)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: switch (IL_002b, IL_002d, IL_002f, IL_0035, IL_0031, IL_0035, IL_0033)

		IL_0029: br.s IL_0035

		IL_002b: ldc.i4.0
		IL_002c: ret

		IL_002d: ldc.i4.1
		IL_002e: ret

		IL_002f: ldc.i4.2
		IL_0030: ret

		IL_0031: ldc.i4.3
		IL_0032: ret

		IL_0033: ldc.i4.4
		IL_0034: ret

		IL_0035: ldstr "Unrecognised CodeRate value for TS: {0}"
		IL_003a: ldarg.0
		IL_003b: ldfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0040: box [mscorlib]System.Int32
		IL_0045: call string [mscorlib]System.String::Format(string, object)
		IL_004a: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_004f: throw
	} // end of method Config::get_CodeRateTs

	.method public hidebysig specialname 
		instance string get_CodeRateAsString () cil managed 
	{
		// Method begins at RVA 0x4548
		// Header size: 12
		// Code size: 112 (0x70)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: switch (IL_002b, IL_0031, IL_0037, IL_003d, IL_0043, IL_0049, IL_004f)

		IL_0029: br.s IL_0055

		IL_002b: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_1_2_STRING
		IL_0030: ret

		IL_0031: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_2_3_STRING
		IL_0036: ret

		IL_0037: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_3_4_STRING
		IL_003c: ret

		IL_003d: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_4_5_STRING
		IL_0042: ret

		IL_0043: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_5_6_STRING
		IL_0048: ret

		IL_0049: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_6_7_STRING
		IL_004e: ret

		IL_004f: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_7_8_STRING
		IL_0054: ret

		IL_0055: ldstr "Unrecognised CodeRate value: {0}"
		IL_005a: ldarg.0
		IL_005b: ldfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0060: box [mscorlib]System.Int32
		IL_0065: call string [mscorlib]System.String::Format(string, object)
		IL_006a: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_006f: throw
	} // end of method Config::get_CodeRateAsString

	.method public hidebysig specialname 
		instance void set_CodeRateAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x45c4
		// Header size: 12
		// Code size: 164 (0xa4)
		.maxstack 2

		IL_0000: ldarg.1
		IL_0001: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_1_2_STRING
		IL_0006: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_000b: brfalse.s IL_0015

		IL_000d: ldarg.0
		IL_000e: ldc.i4.0
		IL_000f: stfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0014: ret

		IL_0015: ldarg.1
		IL_0016: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_2_3_STRING
		IL_001b: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0020: brfalse.s IL_002a

		IL_0022: ldarg.0
		IL_0023: ldc.i4.1
		IL_0024: stfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0029: ret

		IL_002a: ldarg.1
		IL_002b: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_3_4_STRING
		IL_0030: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0035: brfalse.s IL_003f

		IL_0037: ldarg.0
		IL_0038: ldc.i4.2
		IL_0039: stfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_003e: ret

		IL_003f: ldarg.1
		IL_0040: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_4_5_STRING
		IL_0045: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_004a: brfalse.s IL_0054

		IL_004c: ldarg.0
		IL_004d: ldc.i4.3
		IL_004e: stfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0053: ret

		IL_0054: ldarg.1
		IL_0055: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_5_6_STRING
		IL_005a: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_005f: brfalse.s IL_0069

		IL_0061: ldarg.0
		IL_0062: ldc.i4.4
		IL_0063: stfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0068: ret

		IL_0069: ldarg.1
		IL_006a: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_6_7_STRING
		IL_006f: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0074: brfalse.s IL_007e

		IL_0076: ldarg.0
		IL_0077: ldc.i4.5
		IL_0078: stfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_007d: ret

		IL_007e: ldarg.1
		IL_007f: ldsfld string Philips.PSG.Gateway.Config::CODE_RATE_7_8_STRING
		IL_0084: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0089: brfalse.s IL_0093

		IL_008b: ldarg.0
		IL_008c: ldc.i4.6
		IL_008d: stfld int32 Philips.PSG.Gateway.Config::_codeRate
		IL_0092: ret

		IL_0093: ldstr "Unrecognised CodeRate string value: '{0}'"
		IL_0098: ldarg.1
		IL_0099: call string [mscorlib]System.String::Format(string, object)
		IL_009e: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_00a3: throw
	} // end of method Config::set_CodeRateAsString

	.method public hidebysig specialname 
		instance int32 get_ModulationType () cil managed 
	{
		// Method begins at RVA 0x4674
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_modulationType
		IL_0006: ret
	} // end of method Config::get_ModulationType

	.method public hidebysig specialname 
		instance void set_ModulationType (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x467c
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld int32 Philips.PSG.Gateway.Config::_modulationType
		IL_0007: ret
	} // end of method Config::set_ModulationType

	.method public hidebysig specialname 
		instance string get_ModulationTypeAsString () cil managed 
	{
		// Method begins at RVA 0x4685
		// Header size: 1
		// Code size: 43 (0x2b)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_modulationType
		IL_0006: ldc.i4.s 9
		IL_0008: bne.un.s IL_0010

		IL_000a: ldsfld string Philips.PSG.Gateway.Config::MODULATION_TYPE_DVBT_STRING
		IL_000f: ret

		IL_0010: ldstr "Unrecognised ModulationType value: {0}"
		IL_0015: ldarg.0
		IL_0016: ldfld int32 Philips.PSG.Gateway.Config::_modulationType
		IL_001b: box [mscorlib]System.Int32
		IL_0020: call string [mscorlib]System.String::Format(string, object)
		IL_0025: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_002a: throw
	} // end of method Config::get_ModulationTypeAsString

	.method public hidebysig specialname 
		instance void set_ModulationTypeAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x46b1
		// Header size: 1
		// Code size: 39 (0x27)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: ldsfld string Philips.PSG.Gateway.Config::MODULATION_TYPE_DVBT_STRING
		IL_0006: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_000b: brfalse.s IL_0016

		IL_000d: ldarg.0
		IL_000e: ldc.i4.s 9
		IL_0010: stfld int32 Philips.PSG.Gateway.Config::_modulationType
		IL_0015: ret

		IL_0016: ldstr "Unrecognised ModulationType string value: '{0}'"
		IL_001b: ldarg.1
		IL_001c: call string [mscorlib]System.String::Format(string, object)
		IL_0021: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_0026: throw
	} // end of method Config::set_ModulationTypeAsString

	.method public hidebysig specialname 
		instance class [DTAPINET]DTAPINET.DtCaps get_DeviceCapabilityFlags () cil managed 
	{
		// Method begins at RVA 0x46d9
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [DTAPINET]DTAPINET.DtCaps Philips.PSG.Gateway.Config::_deviceCapabilityFlags
		IL_0006: ret
	} // end of method Config::get_DeviceCapabilityFlags

	.method public hidebysig specialname 
		instance void set_DeviceCapabilityFlags (
			class [DTAPINET]DTAPINET.DtCaps 'value'
		) cil managed 
	{
		// Method begins at RVA 0x46e1
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld class [DTAPINET]DTAPINET.DtCaps Philips.PSG.Gateway.Config::_deviceCapabilityFlags
		IL_0007: ret
	} // end of method Config::set_DeviceCapabilityFlags

	.method public hidebysig specialname 
		instance string get_DeviceCapabilityFlagsAsString () cil managed 
	{
		// Method begins at RVA 0x46ec
		// Header size: 12
		// Code size: 108 (0x6c)
		.maxstack 3
		.locals init (
			[0] class [mscorlib]System.Text.StringBuilder
		)

		IL_0000: newobj instance void [mscorlib]System.Text.StringBuilder::.ctor()
		IL_0005: stloc.0
		IL_0006: ldarg.0
		IL_0007: ldfld class [DTAPINET]DTAPINET.DtCaps Philips.PSG.Gateway.Config::_deviceCapabilityFlags
		IL_000c: dup
		IL_000d: ldsfld class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst) [DTAPINET]DTAPINET.DTAPI::CAP_TX_DVBT
		IL_0012: call class [DTAPINET]DTAPINET.DtCaps [DTAPINET]DTAPINET.DtCaps::op_BitwiseAnd(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
		IL_0017: ldsfld class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst) [DTAPINET]DTAPINET.DTAPI::CAP_TX_DVBT
		IL_001c: call bool [DTAPINET]DTAPINET.DtCaps::op_Equality(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
		IL_0021: brfalse.s IL_002f

		IL_0023: ldloc.0
		IL_0024: ldsfld string Philips.PSG.Gateway.Config::DEVICE_CAPABILITY_FLAG_DVBT_STRING
		IL_0029: callvirt instance class [mscorlib]System.Text.StringBuilder [mscorlib]System.Text.StringBuilder::Append(string)
		IL_002e: pop

		IL_002f: ldsfld class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst) [DTAPINET]DTAPINET.DTAPI::CAP_UHF
		IL_0034: call class [DTAPINET]DTAPINET.DtCaps [DTAPINET]DTAPINET.DtCaps::op_BitwiseAnd(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
		IL_0039: ldsfld class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst) [DTAPINET]DTAPINET.DTAPI::CAP_UHF
		IL_003e: call bool [DTAPINET]DTAPINET.DtCaps::op_Equality(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
		IL_0043: brfalse.s IL_0065

		IL_0045: ldloc.0
		IL_0046: callvirt instance int32 [mscorlib]System.Text.StringBuilder::get_Length()
		IL_004b: brfalse.s IL_0059

		IL_004d: ldloc.0
		IL_004e: ldstr " | "
		IL_0053: callvirt instance class [mscorlib]System.Text.StringBuilder [mscorlib]System.Text.StringBuilder::Append(string)
		IL_0058: pop

		IL_0059: ldloc.0
		IL_005a: ldsfld string Philips.PSG.Gateway.Config::DEVICE_CAPABILITY_FLAG_UHF_STRING
		IL_005f: callvirt instance class [mscorlib]System.Text.StringBuilder [mscorlib]System.Text.StringBuilder::Append(string)
		IL_0064: pop

		IL_0065: ldloc.0
		IL_0066: callvirt instance string [mscorlib]System.Object::ToString()
		IL_006b: ret
	} // end of method Config::get_DeviceCapabilityFlagsAsString

	.method public hidebysig specialname 
		instance void set_DeviceCapabilityFlagsAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4764
		// Header size: 12
		// Code size: 142 (0x8e)
		.maxstack 5
		.locals init (
			[0] class [DTAPINET]DTAPINET.DtCaps,
			[1] string[],
			[2] int32,
			[3] string
		)

		IL_0000: newobj instance void [DTAPINET]DTAPINET.DtCaps::.ctor()
		IL_0005: stloc.0
		IL_0006: ldarg.1
		IL_0007: ldc.i4.2
		IL_0008: newarr [mscorlib]System.Char
		IL_000d: dup
		IL_000e: ldc.i4.0
		IL_000f: ldc.i4.s 124
		IL_0011: stelem.i2
		IL_0012: dup
		IL_0013: ldc.i4.1
		IL_0014: ldc.i4.s 32
		IL_0016: stelem.i2
		IL_0017: ldc.i4.1
		IL_0018: callvirt instance string[] [mscorlib]System.String::Split(char[], valuetype [mscorlib]System.StringSplitOptions)
		IL_001d: dup
		IL_001e: ldlen
		IL_001f: brtrue.s IL_002c

		IL_0021: ldstr "Empty DeviceCapabilityFlags string value"
		IL_0026: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_002b: throw

		IL_002c: stloc.1
		IL_002d: ldc.i4.0
		IL_002e: stloc.2
		IL_002f: br.s IL_0080
		// loop start (head: IL_0080)
			IL_0031: ldloc.1
			IL_0032: ldloc.2
			IL_0033: ldelem.ref
			IL_0034: stloc.3
			IL_0035: ldloc.3
			IL_0036: ldsfld string Philips.PSG.Gateway.Config::DEVICE_CAPABILITY_FLAG_DVBT_STRING
			IL_003b: callvirt instance bool [mscorlib]System.String::Equals(string)
			IL_0040: brfalse.s IL_0050

			IL_0042: ldloc.0
			IL_0043: ldsfld class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst) [DTAPINET]DTAPINET.DTAPI::CAP_TX_DVBT
			IL_0048: call class [DTAPINET]DTAPINET.DtCaps [DTAPINET]DTAPINET.DtCaps::op_BitwiseOr(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
			IL_004d: stloc.0
			IL_004e: br.s IL_007c

			IL_0050: ldloc.3
			IL_0051: ldsfld string Philips.PSG.Gateway.Config::DEVICE_CAPABILITY_FLAG_UHF_STRING
			IL_0056: callvirt instance bool [mscorlib]System.String::Equals(string)
			IL_005b: brfalse.s IL_006b

			IL_005d: ldloc.0
			IL_005e: ldsfld class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst) [DTAPINET]DTAPINET.DTAPI::CAP_UHF
			IL_0063: call class [DTAPINET]DTAPINET.DtCaps [DTAPINET]DTAPINET.DtCaps::op_BitwiseOr(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
			IL_0068: stloc.0
			IL_0069: br.s IL_007c

			IL_006b: ldstr "Unrecognised DeviceCapabilityFlags string value: '{0}'"
			IL_0070: ldloc.3
			IL_0071: call string [mscorlib]System.String::Format(string, object)
			IL_0076: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
			IL_007b: throw

			IL_007c: ldloc.2
			IL_007d: ldc.i4.1
			IL_007e: add
			IL_007f: stloc.2

			IL_0080: ldloc.2
			IL_0081: ldloc.1
			IL_0082: ldlen
			IL_0083: conv.i4
			IL_0084: blt.s IL_0031
		// end loop

		IL_0086: ldarg.0
		IL_0087: ldloc.0
		IL_0088: stfld class [DTAPINET]DTAPINET.DtCaps Philips.PSG.Gateway.Config::_deviceCapabilityFlags
		IL_008d: ret
	} // end of method Config::set_DeviceCapabilityFlagsAsString

	.method public hidebysig specialname 
		instance int32 get_DeviceStreamType () cil managed 
	{
		// Method begins at RVA 0x47fe
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_deviceStreamType
		IL_0006: ret
	} // end of method Config::get_DeviceStreamType

	.method public hidebysig specialname 
		instance void set_DeviceStreamType (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4806
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld int32 Philips.PSG.Gateway.Config::_deviceStreamType
		IL_0007: ret
	} // end of method Config::set_DeviceStreamType

	.method public hidebysig specialname 
		instance string get_DeviceStreamTypeAsString () cil managed 
	{
		// Method begins at RVA 0x480f
		// Header size: 1
		// Code size: 43 (0x2b)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_deviceStreamType
		IL_0006: ldc.i4.s 16
		IL_0008: bne.un.s IL_0010

		IL_000a: ldsfld string Philips.PSG.Gateway.Config::DEVICE_STREAM_TYPE_TS_MOD
		IL_000f: ret

		IL_0010: ldstr "Unrecognised DeviceStreamType value: {0}"
		IL_0015: ldarg.0
		IL_0016: ldfld int32 Philips.PSG.Gateway.Config::_deviceStreamType
		IL_001b: box [mscorlib]System.Int32
		IL_0020: call string [mscorlib]System.String::Format(string, object)
		IL_0025: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_002a: throw
	} // end of method Config::get_DeviceStreamTypeAsString

	.method public hidebysig specialname 
		instance void set_DeviceStreamTypeAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x483b
		// Header size: 1
		// Code size: 39 (0x27)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: ldsfld string Philips.PSG.Gateway.Config::DEVICE_STREAM_TYPE_TS_MOD
		IL_0006: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_000b: brfalse.s IL_0016

		IL_000d: ldarg.0
		IL_000e: ldc.i4.s 16
		IL_0010: stfld int32 Philips.PSG.Gateway.Config::_deviceStreamType
		IL_0015: ret

		IL_0016: ldstr "Unrecognised DeviceStreamType string value: '{0}'"
		IL_001b: ldarg.1
		IL_001c: call string [mscorlib]System.String::Format(string, object)
		IL_0021: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_0026: throw
	} // end of method Config::set_DeviceStreamTypeAsString

	.method public hidebysig specialname 
		instance int32 get_TxMode () cil managed 
	{
		// Method begins at RVA 0x4863
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_0006: ret
	} // end of method Config::get_TxMode

	.method public hidebysig specialname 
		instance void set_TxMode (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x486b
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_0007: ret
	} // end of method Config::set_TxMode

	.method public hidebysig specialname 
		instance string get_TxModeAsString () cil managed 
	{
		// Method begins at RVA 0x4874
		// Header size: 12
		// Code size: 109 (0x6d)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: ldc.i4.s 17
		IL_000a: sub
		IL_000b: switch (IL_002e, IL_003a, IL_0040, IL_0034, IL_0046, IL_0052, IL_004c)

		IL_002c: br.s IL_0052

		IL_002e: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_188_STRING
		IL_0033: ret

		IL_0034: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_ADD16_STRING
		IL_0039: ret

		IL_003a: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_192_STRING
		IL_003f: ret

		IL_0040: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_204_STRING
		IL_0045: ret

		IL_0046: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_MIN16_STRING
		IL_004b: ret

		IL_004c: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_RAW_STRING
		IL_0051: ret

		IL_0052: ldstr "Unrecognised TxMode value: {0}"
		IL_0057: ldarg.0
		IL_0058: ldfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_005d: box [mscorlib]System.Int32
		IL_0062: call string [mscorlib]System.String::Format(string, object)
		IL_0067: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_006c: throw
	} // end of method Config::get_TxModeAsString

	.method public hidebysig specialname 
		instance void set_TxModeAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x48f0
		// Header size: 12
		// Code size: 149 (0x95)
		.maxstack 2

		IL_0000: ldarg.1
		IL_0001: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_188_STRING
		IL_0006: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_000b: brfalse.s IL_0016

		IL_000d: ldarg.0
		IL_000e: ldc.i4.s 17
		IL_0010: stfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_0015: ret

		IL_0016: ldarg.1
		IL_0017: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_ADD16_STRING
		IL_001c: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0021: brfalse.s IL_002c

		IL_0023: ldarg.0
		IL_0024: ldc.i4.s 20
		IL_0026: stfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_002b: ret

		IL_002c: ldarg.1
		IL_002d: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_192_STRING
		IL_0032: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0037: brfalse.s IL_0042

		IL_0039: ldarg.0
		IL_003a: ldc.i4.s 18
		IL_003c: stfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_0041: ret

		IL_0042: ldarg.1
		IL_0043: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_204_STRING
		IL_0048: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_004d: brfalse.s IL_0058

		IL_004f: ldarg.0
		IL_0050: ldc.i4.s 19
		IL_0052: stfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_0057: ret

		IL_0058: ldarg.1
		IL_0059: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_MIN16_STRING
		IL_005e: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0063: brfalse.s IL_006e

		IL_0065: ldarg.0
		IL_0066: ldc.i4.s 21
		IL_0068: stfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_006d: ret

		IL_006e: ldarg.1
		IL_006f: ldsfld string Philips.PSG.Gateway.Config::TX_MODE_RAW_STRING
		IL_0074: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0079: brfalse.s IL_0084

		IL_007b: ldarg.0
		IL_007c: ldc.i4.s 23
		IL_007e: stfld int32 Philips.PSG.Gateway.Config::_txMode
		IL_0083: ret

		IL_0084: ldstr "Unrecognised TxMode string value: '{0}'"
		IL_0089: ldarg.1
		IL_008a: call string [mscorlib]System.String::Format(string, object)
		IL_008f: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_0094: throw
	} // end of method Config::set_TxModeAsString

	.method public hidebysig specialname 
		instance int32 get_StuffMode () cil managed 
	{
		// Method begins at RVA 0x4991
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_stuffMode
		IL_0006: ret
	} // end of method Config::get_StuffMode

	.method public hidebysig specialname 
		instance void set_StuffMode (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4999
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld int32 Philips.PSG.Gateway.Config::_stuffMode
		IL_0007: ret
	} // end of method Config::set_StuffMode

	.method public hidebysig specialname 
		instance string get_StuffModeAsString () cil managed 
	{
		// Method begins at RVA 0x49a4
		// Header size: 12
		// Code size: 53 (0x35)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_stuffMode
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: brfalse.s IL_0014

		IL_000a: ldloc.0
		IL_000b: ldc.i4.1
		IL_000c: bne.un.s IL_001a

		IL_000e: ldsfld string Philips.PSG.Gateway.Config::STUFF_MODE_ON_STRING
		IL_0013: ret

		IL_0014: ldsfld string Philips.PSG.Gateway.Config::STUFF_MODE_OFF_STRING
		IL_0019: ret

		IL_001a: ldstr "Unrecognised StuffMode value: {0}"
		IL_001f: ldarg.0
		IL_0020: ldfld int32 Philips.PSG.Gateway.Config::_stuffMode
		IL_0025: box [mscorlib]System.Int32
		IL_002a: call string [mscorlib]System.String::Format(string, object)
		IL_002f: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_0034: throw
	} // end of method Config::get_StuffModeAsString

	.method public hidebysig specialname 
		instance void set_StuffModeAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x49e5
		// Header size: 1
		// Code size: 59 (0x3b)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: ldsfld string Philips.PSG.Gateway.Config::STUFF_MODE_ON_STRING
		IL_0006: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_000b: brfalse.s IL_0015

		IL_000d: ldarg.0
		IL_000e: ldc.i4.1
		IL_000f: stfld int32 Philips.PSG.Gateway.Config::_stuffMode
		IL_0014: ret

		IL_0015: ldarg.1
		IL_0016: ldsfld string Philips.PSG.Gateway.Config::STUFF_MODE_OFF_STRING
		IL_001b: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0020: brfalse.s IL_002a

		IL_0022: ldarg.0
		IL_0023: ldc.i4.0
		IL_0024: stfld int32 Philips.PSG.Gateway.Config::_stuffMode
		IL_0029: ret

		IL_002a: ldstr "Unrecognised StuffMode string value: '{0}'"
		IL_002f: ldarg.1
		IL_0030: call string [mscorlib]System.String::Format(string, object)
		IL_0035: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_003a: throw
	} // end of method Config::set_StuffModeAsString

	.method public hidebysig specialname 
		instance int32 get_Bandwidth () cil managed 
	{
		// Method begins at RVA 0x4a21
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_0006: ret
	} // end of method Config::get_Bandwidth

	.method public hidebysig specialname 
		instance void set_Bandwidth (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4a29
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_0007: ret
	} // end of method Config::set_Bandwidth

	.method public hidebysig specialname 
		instance uint16 get_BandwidthTs () cil managed 
	{
		// Method begins at RVA 0x4a34
		// Header size: 12
		// Code size: 62 (0x3e)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: ldc.i4.2
		IL_0009: sub
		IL_000a: switch (IL_001d, IL_001f, IL_0021)

		IL_001b: br.s IL_0023

		IL_001d: ldc.i4.2
		IL_001e: ret

		IL_001f: ldc.i4.1
		IL_0020: ret

		IL_0021: ldc.i4.0
		IL_0022: ret

		IL_0023: ldstr "Unrecognised Bandwidth value for TS: {0}"
		IL_0028: ldarg.0
		IL_0029: ldfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_002e: box [mscorlib]System.Int32
		IL_0033: call string [mscorlib]System.String::Format(string, object)
		IL_0038: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_003d: throw
	} // end of method Config::get_BandwidthTs

	.method public hidebysig specialname 
		instance string get_BandwidthAsString () cil managed 
	{
		// Method begins at RVA 0x4a80
		// Header size: 12
		// Code size: 84 (0x54)
		.maxstack 2
		.locals init (
			[0] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: ldc.i4.1
		IL_0009: sub
		IL_000a: switch (IL_0021, IL_0027, IL_002d, IL_0033)

		IL_001f: br.s IL_0039

		IL_0021: ldsfld string Philips.PSG.Gateway.Config::BANDWIDTH_5MHZ_STRING
		IL_0026: ret

		IL_0027: ldsfld string Philips.PSG.Gateway.Config::BANDWIDTH_6MHZ_STRING
		IL_002c: ret

		IL_002d: ldsfld string Philips.PSG.Gateway.Config::BANDWIDTH_7MHZ_STRING
		IL_0032: ret

		IL_0033: ldsfld string Philips.PSG.Gateway.Config::BANDWIDTH_8MHZ_STRING
		IL_0038: ret

		IL_0039: ldstr "Unrecognised Bandwidth value: {0}"
		IL_003e: ldarg.0
		IL_003f: ldfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_0044: box [mscorlib]System.Int32
		IL_0049: call string [mscorlib]System.String::Format(string, object)
		IL_004e: newobj instance void [mscorlib]System.Exception::.ctor(string)
		IL_0053: throw
	} // end of method Config::get_BandwidthAsString

	.method public hidebysig specialname 
		instance void set_BandwidthAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4ae0
		// Header size: 12
		// Code size: 101 (0x65)
		.maxstack 2

		IL_0000: ldarg.1
		IL_0001: ldsfld string Philips.PSG.Gateway.Config::BANDWIDTH_5MHZ_STRING
		IL_0006: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_000b: brfalse.s IL_0015

		IL_000d: ldarg.0
		IL_000e: ldc.i4.1
		IL_000f: stfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_0014: ret

		IL_0015: ldarg.1
		IL_0016: ldsfld string Philips.PSG.Gateway.Config::BANDWIDTH_6MHZ_STRING
		IL_001b: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0020: brfalse.s IL_002a

		IL_0022: ldarg.0
		IL_0023: ldc.i4.2
		IL_0024: stfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_0029: ret

		IL_002a: ldarg.1
		IL_002b: ldsfld string Philips.PSG.Gateway.Config::BANDWIDTH_7MHZ_STRING
		IL_0030: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0035: brfalse.s IL_003f

		IL_0037: ldarg.0
		IL_0038: ldc.i4.3
		IL_0039: stfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_003e: ret

		IL_003f: ldarg.1
		IL_0040: ldsfld string Philips.PSG.Gateway.Config::BANDWIDTH_8MHZ_STRING
		IL_0045: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_004a: brfalse.s IL_0054

		IL_004c: ldarg.0
		IL_004d: ldc.i4.4
		IL_004e: stfld int32 Philips.PSG.Gateway.Config::_bandwidth
		IL_0053: ret

		IL_0054: ldstr "Unrecognised Bandwidth string value: '{0}'"
		IL_0059: ldarg.1
		IL_005a: call string [mscorlib]System.String::Format(string, object)
		IL_005f: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
		IL_0064: throw
	} // end of method Config::set_BandwidthAsString

	.method public hidebysig specialname 
		instance int32 get_OutputChannel () cil managed 
	{
		// Method begins at RVA 0x4b51
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_outputChannel
		IL_0006: ret
	} // end of method Config::get_OutputChannel

	.method public hidebysig specialname 
		instance void set_OutputChannel (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4b59
		// Header size: 1
		// Code size: 34 (0x22)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: ldc.i4.0
		IL_0002: bge.s IL_000c

		IL_0004: ldarg.0
		IL_0005: ldc.i4.0
		IL_0006: stfld int32 Philips.PSG.Gateway.Config::_outputChannel
		IL_000b: ret

		IL_000c: ldarg.1
		IL_000d: ldc.i4.s 68
		IL_000f: ble.s IL_001a

		IL_0011: ldarg.0
		IL_0012: ldc.i4.s 68
		IL_0014: stfld int32 Philips.PSG.Gateway.Config::_outputChannel
		IL_0019: ret

		IL_001a: ldarg.0
		IL_001b: ldarg.1
		IL_001c: stfld int32 Philips.PSG.Gateway.Config::_outputChannel
		IL_0021: ret
	} // end of method Config::set_OutputChannel

	.method public hidebysig specialname 
		instance int32 get_ModulationFrequency () cil managed 
	{
		// Method begins at RVA 0x4b7c
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld int32 Philips.PSG.Gateway.Config::_modulationFrequency
		IL_0006: ret
	} // end of method Config::get_ModulationFrequency

	.method public hidebysig specialname 
		instance void set_ModulationFrequency (
			int32 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4b84
		// Header size: 1
		// Code size: 48 (0x30)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: ldc.i4 450
		IL_0006: bge.s IL_0014

		IL_0008: ldarg.0
		IL_0009: ldc.i4 450
		IL_000e: stfld int32 Philips.PSG.Gateway.Config::_modulationFrequency
		IL_0013: ret

		IL_0014: ldarg.1
		IL_0015: ldc.i4 8640
		IL_001a: ble.s IL_0028

		IL_001c: ldarg.0
		IL_001d: ldc.i4 8640
		IL_0022: stfld int32 Philips.PSG.Gateway.Config::_modulationFrequency
		IL_0027: ret

		IL_0028: ldarg.0
		IL_0029: ldarg.1
		IL_002a: stfld int32 Philips.PSG.Gateway.Config::_modulationFrequency
		IL_002f: ret
	} // end of method Config::set_ModulationFrequency

	.method public hidebysig specialname 
		instance float64 get_OutputLevel () cil managed 
	{
		// Method begins at RVA 0x4bb5
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld float64 Philips.PSG.Gateway.Config::_outputLevel
		IL_0006: ret
	} // end of method Config::get_OutputLevel

	.method public hidebysig specialname 
		instance void set_OutputLevel (
			float64 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4bbd
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld float64 Philips.PSG.Gateway.Config::_outputLevel
		IL_0007: ret
	} // end of method Config::set_OutputLevel

	.method public hidebysig specialname 
		instance string get_OriginalNetworkId () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4bc6
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<OriginalNetworkId>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_OriginalNetworkId

	.method public hidebysig specialname 
		instance void set_OriginalNetworkId (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4bce
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<OriginalNetworkId>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_OriginalNetworkId

	.method public hidebysig specialname 
		instance string get_ProgNum () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4bd7
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<ProgNum>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_ProgNum

	.method public hidebysig specialname 
		instance void set_ProgNum (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4bdf
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<ProgNum>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_ProgNum

	.method public hidebysig specialname 
		instance string get_Pid () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4be8
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<Pid>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_Pid

	.method public hidebysig specialname 
		instance void set_Pid (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4bf0
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<Pid>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_Pid

	.method public hidebysig specialname 
		instance string get_Oui () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4bf9
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<Oui>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_Oui

	.method public hidebysig specialname 
		instance void set_Oui (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c01
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<Oui>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_Oui

	.method public hidebysig specialname 
		instance string get_TxnId () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c0a
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<TxnId>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_TxnId

	.method public hidebysig specialname 
		instance void set_TxnId (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c12
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<TxnId>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_TxnId

	.method public hidebysig specialname 
		instance string get_DwnldId () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c1b
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<DwnldId>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_DwnldId

	.method public hidebysig specialname 
		instance void set_DwnldId (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c23
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<DwnldId>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_DwnldId

	.method public hidebysig specialname 
		instance string get_NetworkId () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c2c
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<NetworkId>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_NetworkId

	.method public hidebysig specialname 
		instance void set_NetworkId (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c34
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<NetworkId>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_NetworkId

	.method public hidebysig specialname 
		instance string get_TransportStreamId () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c3d
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<TransportStreamId>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_TransportStreamId

	.method public hidebysig specialname 
		instance void set_TransportStreamId (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c45
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<TransportStreamId>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_TransportStreamId

	.method public hidebysig specialname 
		instance string get_PcrPid () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c4e
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<PcrPid>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_PcrPid

	.method public hidebysig specialname 
		instance void set_PcrPid (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c56
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<PcrPid>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_PcrPid

	.method public hidebysig specialname 
		instance uint16 get_Onid () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c5f
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld uint16 Philips.PSG.Gateway.Config::'<Onid>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_Onid

	.method public hidebysig specialname 
		instance void set_Onid (
			uint16 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c67
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld uint16 Philips.PSG.Gateway.Config::'<Onid>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_Onid

	.method public hidebysig specialname 
		instance string get_RoomNumber () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c70
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<RoomNumber>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_RoomNumber

	.method public hidebysig specialname 
		instance void set_RoomNumber (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c78
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<RoomNumber>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_RoomNumber

	.method public hidebysig specialname 
		instance string get_SsbIdentifier () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c81
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<SsbIdentifier>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_SsbIdentifier

	.method public hidebysig specialname 
		instance void set_SsbIdentifier (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c89
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<SsbIdentifier>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_SsbIdentifier

	.method public hidebysig specialname 
		instance string get_ChannelTableIdentifier () cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c92
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Config::'<ChannelTableIdentifier>k__BackingField'
		IL_0006: ret
	} // end of method Config::get_ChannelTableIdentifier

	.method public hidebysig specialname 
		instance void set_ChannelTableIdentifier (
			string 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x4c9a
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld string Philips.PSG.Gateway.Config::'<ChannelTableIdentifier>k__BackingField'
		IL_0007: ret
	} // end of method Config::set_ChannelTableIdentifier

	.method public hidebysig static 
		int32 ConvertChannelToFrequency (
			int32 channel
		) cil managed 
	{
		// Method begins at RVA 0x4ca3
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldc.i4.8
		IL_0002: mul
		IL_0003: ldc.i4.s 45
		IL_0005: add
		IL_0006: ret
	} // end of method Config::ConvertChannelToFrequency

	.method public hidebysig static 
		class Philips.PSG.Gateway.Config CreateFromFile () cil managed 
	{
		// Method begins at RVA 0x4cac
		// Header size: 12
		// Code size: 78 (0x4e)
		.maxstack 2
		.locals init (
			[0] class [System.Xml]System.Xml.Serialization.XmlSerializer,
			[1] class [mscorlib]System.IO.StreamReader,
			[2] class Philips.PSG.Gateway.Config,
			[3] class [mscorlib]System.Exception
		)

		IL_0000: ldnull
		IL_0001: stloc.1
		IL_0002: ldnull
		IL_0003: stloc.2
		IL_0004: ldtoken Philips.PSG.Gateway.Config
		IL_0009: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_000e: newobj instance void [System.Xml]System.Xml.Serialization.XmlSerializer::.ctor(class [mscorlib]System.Type)
		IL_0013: stloc.0
		.try
		{
			.try
			{
				IL_0014: call string Philips.PSG.Gateway.Settings::get_ConfigFile()
				IL_0019: call class [mscorlib]System.IO.StreamReader [mscorlib]System.IO.File::OpenText(string)
				IL_001e: stloc.1
				IL_001f: ldloc.0
				IL_0020: ldloc.1
				IL_0021: callvirt instance object [System.Xml]System.Xml.Serialization.XmlSerializer::Deserialize(class [mscorlib]System.IO.TextReader)
				IL_0026: castclass Philips.PSG.Gateway.Config
				IL_002b: stloc.2
				IL_002c: leave.s IL_004c
			} // end .try
			catch [mscorlib]System.Exception
			{
				IL_002e: stloc.3
				IL_002f: ldstr "Config: Error - failed to load configuration from file, using defaults: {0}"
				IL_0034: ldloc.3
				IL_0035: call void [mscorlib]System.Console::WriteLine(string, object)
				IL_003a: newobj instance void Philips.PSG.Gateway.Config::.ctor()
				IL_003f: stloc.2
				IL_0040: leave.s IL_004c
			} // end handler
		} // end .try
		finally
		{
			IL_0042: ldloc.1
			IL_0043: brfalse.s IL_004b

			IL_0045: ldloc.1
			IL_0046: callvirt instance void [mscorlib]System.IO.TextReader::Close()

			IL_004b: endfinally
		} // end handler

		IL_004c: ldloc.2
		IL_004d: ret
	} // end of method Config::CreateFromFile

	.method public hidebysig 
		instance void SaveToFile () cil managed 
	{
		// Method begins at RVA 0x4d24
		// Header size: 12
		// Code size: 84 (0x54)
		.maxstack 3
		.locals init (
			[0] class [System.Xml]System.Xml.Serialization.XmlSerializer,
			[1] string,
			[2] class [mscorlib]System.IO.StreamWriter,
			[3] class [mscorlib]System.Exception
		)

		IL_0000: call string Philips.PSG.Gateway.Settings::get_ConfigFile()
		IL_0005: stloc.1
		IL_0006: ldnull
		IL_0007: stloc.2
		IL_0008: ldtoken Philips.PSG.Gateway.Config
		IL_000d: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_0012: newobj instance void [System.Xml]System.Xml.Serialization.XmlSerializer::.ctor(class [mscorlib]System.Type)
		IL_0017: stloc.0
		.try
		{
			.try
			{
				IL_0018: ldloc.1
				IL_0019: call string [mscorlib]System.IO.Path::GetDirectoryName(string)
				IL_001e: call class [mscorlib]System.IO.DirectoryInfo [mscorlib]System.IO.Directory::CreateDirectory(string)
				IL_0023: pop
				IL_0024: ldloc.1
				IL_0025: call class [mscorlib]System.IO.StreamWriter [mscorlib]System.IO.File::CreateText(string)
				IL_002a: stloc.2
				IL_002b: ldloc.0
				IL_002c: ldloc.2
				IL_002d: ldarg.0
				IL_002e: callvirt instance void [System.Xml]System.Xml.Serialization.XmlSerializer::Serialize(class [mscorlib]System.IO.TextWriter, object)
				IL_0033: ldloc.2
				IL_0034: callvirt instance void [mscorlib]System.IO.TextWriter::Flush()
				IL_0039: leave.s IL_0053
			} // end .try
			catch [mscorlib]System.Exception
			{
				IL_003b: stloc.3
				IL_003c: ldstr "Config: Error - failed to save config to file: {0}"
				IL_0041: ldloc.3
				IL_0042: call void [mscorlib]System.Console::WriteLine(string, object)
				IL_0047: leave.s IL_0053
			} // end handler
		} // end .try
		finally
		{
			IL_0049: ldloc.2
			IL_004a: brfalse.s IL_0052

			IL_004c: ldloc.2
			IL_004d: callvirt instance void [mscorlib]System.IO.TextWriter::Close()

			IL_0052: endfinally
		} // end handler

		IL_0053: ret
	} // end of method Config::SaveToFile

	.method private hidebysig specialname rtspecialname static 
		void .cctor () cil managed 
	{
		// Method begins at RVA 0x4da0
		// Header size: 12
		// Code size: 341 (0x155)
		.maxstack 1

		IL_0000: ldstr "2k"
		IL_0005: stsfld string Philips.PSG.Gateway.Config::TRANSMISSION_MODE_2K_STRING
		IL_000a: ldstr "4k"
		IL_000f: stsfld string Philips.PSG.Gateway.Config::TRANSMISSION_MODE_4K_STRING
		IL_0014: ldstr "8k"
		IL_0019: stsfld string Philips.PSG.Gateway.Config::TRANSMISSION_MODE_8K_STRING
		IL_001e: ldstr "1/4"
		IL_0023: stsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_4_STRING
		IL_0028: ldstr "1/8"
		IL_002d: stsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_8_STRING
		IL_0032: ldstr "1/16"
		IL_0037: stsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_16_STRING
		IL_003c: ldstr "1/32"
		IL_0041: stsfld string Philips.PSG.Gateway.Config::GUARD_INTERVAL_1_32_STRING
		IL_0046: ldstr "QPSK"
		IL_004b: stsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QPSK_STRING
		IL_0050: ldstr "16-QAM"
		IL_0055: stsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QAM16_STRING
		IL_005a: ldstr "64-QAM"
		IL_005f: stsfld string Philips.PSG.Gateway.Config::CONSTELLATION_QAM64_STRING
		IL_0064: ldstr "1/2"
		IL_0069: stsfld string Philips.PSG.Gateway.Config::CODE_RATE_1_2_STRING
		IL_006e: ldstr "2/3"
		IL_0073: stsfld string Philips.PSG.Gateway.Config::CODE_RATE_2_3_STRING
		IL_0078: ldstr "3/4"
		IL_007d: stsfld string Philips.PSG.Gateway.Config::CODE_RATE_3_4_STRING
		IL_0082: ldstr "4/5"
		IL_0087: stsfld string Philips.PSG.Gateway.Config::CODE_RATE_4_5_STRING
		IL_008c: ldstr "5/6"
		IL_0091: stsfld string Philips.PSG.Gateway.Config::CODE_RATE_5_6_STRING
		IL_0096: ldstr "6/7"
		IL_009b: stsfld string Philips.PSG.Gateway.Config::CODE_RATE_6_7_STRING
		IL_00a0: ldstr "7/8"
		IL_00a5: stsfld string Philips.PSG.Gateway.Config::CODE_RATE_7_8_STRING
		IL_00aa: ldstr "DVB-T"
		IL_00af: stsfld string Philips.PSG.Gateway.Config::MODULATION_TYPE_DVBT_STRING
		IL_00b4: ldstr "DVB-T"
		IL_00b9: stsfld string Philips.PSG.Gateway.Config::DEVICE_CAPABILITY_FLAG_DVBT_STRING
		IL_00be: ldstr "UHF"
		IL_00c3: stsfld string Philips.PSG.Gateway.Config::DEVICE_CAPABILITY_FLAG_UHF_STRING
		IL_00c8: ldstr "MODULATED"
		IL_00cd: stsfld string Philips.PSG.Gateway.Config::DEVICE_STREAM_TYPE_TS_MOD
		IL_00d2: ldstr "188"
		IL_00d7: stsfld string Philips.PSG.Gateway.Config::TX_MODE_188_STRING
		IL_00dc: ldstr "ADD-16"
		IL_00e1: stsfld string Philips.PSG.Gateway.Config::TX_MODE_ADD16_STRING
		IL_00e6: ldstr "130"
		IL_00eb: stsfld string Philips.PSG.Gateway.Config::TX_MODE_130_STRING
		IL_00f0: ldstr "192"
		IL_00f5: stsfld string Philips.PSG.Gateway.Config::TX_MODE_192_STRING
		IL_00fa: ldstr "204"
		IL_00ff: stsfld string Philips.PSG.Gateway.Config::TX_MODE_204_STRING
		IL_0104: ldstr "MIN-16"
		IL_0109: stsfld string Philips.PSG.Gateway.Config::TX_MODE_MIN16_STRING
		IL_010e: ldstr "RAW"
		IL_0113: stsfld string Philips.PSG.Gateway.Config::TX_MODE_RAW_STRING
		IL_0118: ldstr "ON"
		IL_011d: stsfld string Philips.PSG.Gateway.Config::STUFF_MODE_ON_STRING
		IL_0122: ldstr "OFF"
		IL_0127: stsfld string Philips.PSG.Gateway.Config::STUFF_MODE_OFF_STRING
		IL_012c: ldstr "5"
		IL_0131: stsfld string Philips.PSG.Gateway.Config::BANDWIDTH_5MHZ_STRING
		IL_0136: ldstr "6"
		IL_013b: stsfld string Philips.PSG.Gateway.Config::BANDWIDTH_6MHZ_STRING
		IL_0140: ldstr "7"
		IL_0145: stsfld string Philips.PSG.Gateway.Config::BANDWIDTH_7MHZ_STRING
		IL_014a: ldstr "8"
		IL_014f: stsfld string Philips.PSG.Gateway.Config::BANDWIDTH_8MHZ_STRING
		IL_0154: ret
	} // end of method Config::.cctor

	// Properties
	.property instance string BuildRootFolder()
	{
		.get instance string Philips.PSG.Gateway.Config::get_BuildRootFolder()
		.set instance void Philips.PSG.Gateway.Config::set_BuildRootFolder(string)
	}
	.property instance string WorkingRootFolder()
	{
		.get instance string Philips.PSG.Gateway.Config::get_WorkingRootFolder()
		.set instance void Philips.PSG.Gateway.Config::set_WorkingRootFolder(string)
	}
	.property instance string ScheduleDataFolder()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_ScheduleDataFolder()
	}
	.property instance string OutputDataFolder()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_OutputDataFolder()
	}
	.property instance string PreviousScheduleDataFolder()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_PreviousScheduleDataFolder()
	}
	.property instance string PreviousOutputDataFolder()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_PreviousOutputDataFolder()
	}
	.property instance string ScheduleDataFile()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_ScheduleDataFile()
	}
	.property instance string MultiplexedTsFile()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_MultiplexedTsFile()
	}
	.property instance string ScheduleRunFile()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_ScheduleRunFile()
	}
	.property instance string TsPlayFile()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_TsPlayFile()
	}
	.property instance string BuildLogFile()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_BuildLogFile()
	}
	.property instance int32 TransmissionMode()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Config::get_TransmissionMode()
		.set instance void Philips.PSG.Gateway.Config::set_TransmissionMode(int32)
	}
	.property instance uint16 TransmissionModeTs()
	{
		.get instance uint16 Philips.PSG.Gateway.Config::get_TransmissionModeTs()
	}
	.property instance string TransmissionModeAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 10 54 72 61 6e 73 6d 69 73 73 69 6f 6e 4d
			6f 64 65 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_TransmissionModeAsString()
		.set instance void Philips.PSG.Gateway.Config::set_TransmissionModeAsString(string)
	}
	.property instance int32 GuardInterval()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Config::get_GuardInterval()
		.set instance void Philips.PSG.Gateway.Config::set_GuardInterval(int32)
	}
	.property instance uint16 GuardIntervalTs()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance uint16 Philips.PSG.Gateway.Config::get_GuardIntervalTs()
	}
	.property instance string GuardIntervalAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 0d 47 75 61 72 64 49 6e 74 65 72 76 61 6c
			00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_GuardIntervalAsString()
		.set instance void Philips.PSG.Gateway.Config::set_GuardIntervalAsString(string)
	}
	.property instance int32 Constellation()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Config::get_Constellation()
		.set instance void Philips.PSG.Gateway.Config::set_Constellation(int32)
	}
	.property instance uint16 ConstellationTs()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance uint16 Philips.PSG.Gateway.Config::get_ConstellationTs()
	}
	.property instance string ConstellationAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 0d 43 6f 6e 73 74 65 6c 6c 61 74 69 6f 6e
			00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_ConstellationAsString()
		.set instance void Philips.PSG.Gateway.Config::set_ConstellationAsString(string)
	}
	.property instance int32 CodeRate()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Config::get_CodeRate()
		.set instance void Philips.PSG.Gateway.Config::set_CodeRate(int32)
	}
	.property instance uint16 CodeRateTs()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance uint16 Philips.PSG.Gateway.Config::get_CodeRateTs()
	}
	.property instance string CodeRateAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 08 43 6f 64 65 52 61 74 65 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_CodeRateAsString()
		.set instance void Philips.PSG.Gateway.Config::set_CodeRateAsString(string)
	}
	.property instance int32 ModulationType()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Config::get_ModulationType()
		.set instance void Philips.PSG.Gateway.Config::set_ModulationType(int32)
	}
	.property instance string ModulationTypeAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 0e 4d 6f 64 75 6c 61 74 69 6f 6e 54 79 70
			65 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_ModulationTypeAsString()
		.set instance void Philips.PSG.Gateway.Config::set_ModulationTypeAsString(string)
	}
	.property instance class [DTAPINET]DTAPINET.DtCaps DeviceCapabilityFlags()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance class [DTAPINET]DTAPINET.DtCaps Philips.PSG.Gateway.Config::get_DeviceCapabilityFlags()
		.set instance void Philips.PSG.Gateway.Config::set_DeviceCapabilityFlags(class [DTAPINET]DTAPINET.DtCaps)
	}
	.property instance string DeviceCapabilityFlagsAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 15 44 65 76 69 63 65 43 61 70 61 62 69 6c
			69 74 79 46 6c 61 67 73 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_DeviceCapabilityFlagsAsString()
		.set instance void Philips.PSG.Gateway.Config::set_DeviceCapabilityFlagsAsString(string)
	}
	.property instance int32 DeviceStreamType()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Config::get_DeviceStreamType()
		.set instance void Philips.PSG.Gateway.Config::set_DeviceStreamType(int32)
	}
	.property instance string DeviceStreamTypeAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 10 44 65 76 69 63 65 53 74 72 65 61 6d 54
			79 70 65 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_DeviceStreamTypeAsString()
		.set instance void Philips.PSG.Gateway.Config::set_DeviceStreamTypeAsString(string)
	}
	.property instance int32 TxMode()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Config::get_TxMode()
		.set instance void Philips.PSG.Gateway.Config::set_TxMode(int32)
	}
	.property instance string TxModeAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 06 54 78 4d 6f 64 65 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_TxModeAsString()
		.set instance void Philips.PSG.Gateway.Config::set_TxModeAsString(string)
	}
	.property instance int32 StuffMode()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Config::get_StuffMode()
		.set instance void Philips.PSG.Gateway.Config::set_StuffMode(int32)
	}
	.property instance string StuffModeAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 09 53 74 75 66 66 4d 6f 64 65 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_StuffModeAsString()
		.set instance void Philips.PSG.Gateway.Config::set_StuffModeAsString(string)
	}
	.property instance int32 Bandwidth()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Config::get_Bandwidth()
		.set instance void Philips.PSG.Gateway.Config::set_Bandwidth(int32)
	}
	.property instance uint16 BandwidthTs()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance uint16 Philips.PSG.Gateway.Config::get_BandwidthTs()
	}
	.property instance string BandwidthAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 09 42 61 6e 64 77 69 64 74 68 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_BandwidthAsString()
		.set instance void Philips.PSG.Gateway.Config::set_BandwidthAsString(string)
	}
	.property instance int32 OutputChannel()
	{
		.get instance int32 Philips.PSG.Gateway.Config::get_OutputChannel()
		.set instance void Philips.PSG.Gateway.Config::set_OutputChannel(int32)
	}
	.property instance int32 ModulationFrequency()
	{
		.get instance int32 Philips.PSG.Gateway.Config::get_ModulationFrequency()
		.set instance void Philips.PSG.Gateway.Config::set_ModulationFrequency(int32)
	}
	.property instance float64 OutputLevel()
	{
		.get instance float64 Philips.PSG.Gateway.Config::get_OutputLevel()
		.set instance void Philips.PSG.Gateway.Config::set_OutputLevel(float64)
	}
	.property instance string OriginalNetworkId()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 11 4f 72 69 67 69 6e 61 6c 4e 65 74 77 6f
			72 6b 49 64 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_OriginalNetworkId()
		.set instance void Philips.PSG.Gateway.Config::set_OriginalNetworkId(string)
	}
	.property instance string ProgNum()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 07 50 72 6f 67 4e 75 6d 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_ProgNum()
		.set instance void Philips.PSG.Gateway.Config::set_ProgNum(string)
	}
	.property instance string Pid()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 03 50 69 64 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_Pid()
		.set instance void Philips.PSG.Gateway.Config::set_Pid(string)
	}
	.property instance string Oui()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 03 4f 75 69 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_Oui()
		.set instance void Philips.PSG.Gateway.Config::set_Oui(string)
	}
	.property instance string TxnId()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 05 54 78 6e 49 64 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_TxnId()
		.set instance void Philips.PSG.Gateway.Config::set_TxnId(string)
	}
	.property instance string DwnldId()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 07 44 77 6e 6c 64 49 64 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_DwnldId()
		.set instance void Philips.PSG.Gateway.Config::set_DwnldId(string)
	}
	.property instance string NetworkId()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 09 4e 65 74 77 6f 72 6b 49 64 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_NetworkId()
		.set instance void Philips.PSG.Gateway.Config::set_NetworkId(string)
	}
	.property instance string TransportStreamId()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 11 54 72 61 6e 73 70 6f 72 74 53 74 72 65
			61 6d 49 64 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_TransportStreamId()
		.set instance void Philips.PSG.Gateway.Config::set_TransportStreamId(string)
	}
	.property instance string PcrPid()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 06 50 63 72 50 69 64 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_PcrPid()
		.set instance void Philips.PSG.Gateway.Config::set_PcrPid(string)
	}
	.property instance uint16 Onid()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 04 4f 6e 69 64 00 00
		)
		.get instance uint16 Philips.PSG.Gateway.Config::get_Onid()
		.set instance void Philips.PSG.Gateway.Config::set_Onid(uint16)
	}
	.property instance string RoomNumber()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 0a 52 6f 6f 6d 4e 75 6d 62 65 72 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_RoomNumber()
		.set instance void Philips.PSG.Gateway.Config::set_RoomNumber(string)
	}
	.property instance string SsbIdentifier()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 0d 53 73 62 49 64 65 6e 74 69 66 69 65 72
			00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_SsbIdentifier()
		.set instance void Philips.PSG.Gateway.Config::set_SsbIdentifier(string)
	}
	.property instance string ChannelTableIdentifier()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 16 43 68 61 6e 6e 65 6c 54 61 62 6c 65 49
			64 65 6e 74 69 66 69 65 72 00 00
		)
		.get instance string Philips.PSG.Gateway.Config::get_ChannelTableIdentifier()
		.set instance void Philips.PSG.Gateway.Config::set_ChannelTableIdentifier(string)
	}

} // end of class Philips.PSG.Gateway.Config

.class public auto ansi abstract sealed beforefieldinit Philips.PSG.Gateway.ConfigFactory
	extends [mscorlib]System.Object
{
	// Fields
	.field private static class Philips.PSG.Gateway.Config _config

	// Methods
	.method public hidebysig specialname static 
		class Philips.PSG.Gateway.Config get_CurrentConfig () cil managed 
	{
		// Method begins at RVA 0x4f01
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldsfld class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::_config
		IL_0005: brtrue.s IL_0011

		IL_0007: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.Config::CreateFromFile()
		IL_000c: stsfld class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::_config

		IL_0011: ldsfld class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::_config
		IL_0016: ret
	} // end of method ConfigFactory::get_CurrentConfig

	// Properties
	.property class Philips.PSG.Gateway.Config CurrentConfig()
	{
		.get class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
	}

} // end of class Philips.PSG.Gateway.ConfigFactory

.class public auto ansi serializable beforefieldinit Philips.PSG.Gateway.Catalog
	extends [mscorlib]System.Object
{
	// Fields
	.field private class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry> _entries

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x4f19
		// Header size: 1
		// Code size: 18 (0x12)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [mscorlib]System.Object::.ctor()
		IL_0006: ldarg.0
		IL_0007: newobj instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::.ctor()
		IL_000c: stfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry> Philips.PSG.Gateway.Catalog::_entries
		IL_0011: ret
	} // end of method Catalog::.ctor

	.method public hidebysig specialname 
		instance class Philips.PSG.Gateway.CatalogEntry[] get_Entries () cil managed 
	{
		// Method begins at RVA 0x4f2c
		// Header size: 1
		// Code size: 12 (0xc)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry> Philips.PSG.Gateway.Catalog::_entries
		IL_0006: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::ToArray()
		IL_000b: ret
	} // end of method Catalog::get_Entries

	.method public hidebysig specialname 
		instance void set_Entries (
			class Philips.PSG.Gateway.CatalogEntry[] 'value'
		) cil managed 
	{
		// Method begins at RVA 0x4f39
		// Header size: 1
		// Code size: 27 (0x1b)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry> Philips.PSG.Gateway.Catalog::_entries
		IL_0006: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::Clear()
		IL_000b: ldarg.1
		IL_000c: brfalse.s IL_001a

		IL_000e: ldarg.0
		IL_000f: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry> Philips.PSG.Gateway.Catalog::_entries
		IL_0014: ldarg.1
		IL_0015: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::AddRange(class [mscorlib]System.Collections.Generic.IEnumerable`1<!0>)

		IL_001a: ret
	} // end of method Catalog::set_Entries

	.method public hidebysig specialname 
		instance int32 get_NumberOfEntries () cil managed 
	{
		// Method begins at RVA 0x4f55
		// Header size: 1
		// Code size: 12 (0xc)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry> Philips.PSG.Gateway.Catalog::_entries
		IL_0006: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::get_Count()
		IL_000b: ret
	} // end of method Catalog::get_NumberOfEntries

	.method public hidebysig 
		instance class Philips.PSG.Gateway.CatalogEntry[] GetValidCandidates (
			string rootFolder,
			class [mscorlib]System.IO.StreamWriter logStream
		) cil managed 
	{
		// Method begins at RVA 0x4f64
		// Header size: 12
		// Code size: 77 (0x4d)
		.maxstack 3
		.locals init (
			[0] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>,
			[1] valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.CatalogEntry>,
			[2] class Philips.PSG.Gateway.CatalogEntry
		)

		IL_0000: newobj instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::.ctor()
		IL_0005: stloc.0
		IL_0006: ldarg.0
		IL_0007: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry> Philips.PSG.Gateway.Catalog::_entries
		IL_000c: callvirt instance valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<!0> class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::GetEnumerator()
		IL_0011: stloc.1
		.try
		{
			IL_0012: br.s IL_002d
			// loop start (head: IL_002d)
				IL_0014: ldloca.s 1
				IL_0016: call instance !0 valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.CatalogEntry>::get_Current()
				IL_001b: stloc.2
				IL_001c: ldloc.2
				IL_001d: ldarg.1
				IL_001e: ldarg.2
				IL_001f: callvirt instance bool Philips.PSG.Gateway.CatalogEntry::IsValidCandidate(string, class [mscorlib]System.IO.StreamWriter)
				IL_0024: brfalse.s IL_002d

				IL_0026: ldloc.0
				IL_0027: ldloc.2
				IL_0028: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::Add(!0)

				IL_002d: ldloca.s 1
				IL_002f: call instance bool valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.CatalogEntry>::MoveNext()
				IL_0034: brtrue.s IL_0014
			// end loop

			IL_0036: leave.s IL_0046
		} // end .try
		finally
		{
			IL_0038: ldloca.s 1
			IL_003a: constrained. valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.CatalogEntry>
			IL_0040: callvirt instance void [mscorlib]System.IDisposable::Dispose()
			IL_0045: endfinally
		} // end handler

		IL_0046: ldloc.0
		IL_0047: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::ToArray()
		IL_004c: ret
	} // end of method Catalog::GetValidCandidates

	.method public hidebysig 
		instance void SaveToFile () cil managed 
	{
		// Method begins at RVA 0x4fd0
		// Header size: 12
		// Code size: 73 (0x49)
		.maxstack 3
		.locals init (
			[0] class [System.Xml]System.Xml.Serialization.XmlSerializer,
			[1] string,
			[2] class [mscorlib]System.IO.StreamWriter
		)

		IL_0000: call string Philips.PSG.Gateway.Settings::get_CatalogFile()
		IL_0005: stloc.1
		IL_0006: ldnull
		IL_0007: stloc.2
		IL_0008: ldtoken Philips.PSG.Gateway.Catalog
		IL_000d: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_0012: newobj instance void [System.Xml]System.Xml.Serialization.XmlSerializer::.ctor(class [mscorlib]System.Type)
		IL_0017: stloc.0
		.try
		{
			.try
			{
				IL_0018: ldloc.1
				IL_0019: call string [mscorlib]System.IO.Path::GetDirectoryName(string)
				IL_001e: call class [mscorlib]System.IO.DirectoryInfo [mscorlib]System.IO.Directory::CreateDirectory(string)
				IL_0023: pop
				IL_0024: ldloc.1
				IL_0025: call class [mscorlib]System.IO.StreamWriter [mscorlib]System.IO.File::CreateText(string)
				IL_002a: stloc.2
				IL_002b: ldloc.0
				IL_002c: ldloc.2
				IL_002d: ldarg.0
				IL_002e: callvirt instance void [System.Xml]System.Xml.Serialization.XmlSerializer::Serialize(class [mscorlib]System.IO.TextWriter, object)
				IL_0033: ldloc.2
				IL_0034: callvirt instance void [mscorlib]System.IO.TextWriter::Flush()
				IL_0039: leave.s IL_0048
			} // end .try
			catch [mscorlib]System.Exception
			{
				IL_003b: pop
				IL_003c: leave.s IL_0048
			} // end handler
		} // end .try
		finally
		{
			IL_003e: ldloc.2
			IL_003f: brfalse.s IL_0047

			IL_0041: ldloc.2
			IL_0042: callvirt instance void [mscorlib]System.IO.TextWriter::Close()

			IL_0047: endfinally
		} // end handler

		IL_0048: ret
	} // end of method Catalog::SaveToFile

	.method public hidebysig static 
		class Philips.PSG.Gateway.Catalog CreateFromFile () cil managed 
	{
		// Method begins at RVA 0x5044
		// Header size: 12
		// Code size: 78 (0x4e)
		.maxstack 2
		.locals init (
			[0] class [System.Xml]System.Xml.Serialization.XmlSerializer,
			[1] class [mscorlib]System.IO.StreamReader,
			[2] class Philips.PSG.Gateway.Catalog,
			[3] class [mscorlib]System.Exception
		)

		IL_0000: ldnull
		IL_0001: stloc.1
		IL_0002: ldnull
		IL_0003: stloc.2
		IL_0004: ldtoken Philips.PSG.Gateway.Catalog
		IL_0009: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_000e: newobj instance void [System.Xml]System.Xml.Serialization.XmlSerializer::.ctor(class [mscorlib]System.Type)
		IL_0013: stloc.0
		.try
		{
			.try
			{
				IL_0014: call string Philips.PSG.Gateway.Settings::get_CatalogFile()
				IL_0019: call class [mscorlib]System.IO.StreamReader [mscorlib]System.IO.File::OpenText(string)
				IL_001e: stloc.1
				IL_001f: ldloc.0
				IL_0020: ldloc.1
				IL_0021: callvirt instance object [System.Xml]System.Xml.Serialization.XmlSerializer::Deserialize(class [mscorlib]System.IO.TextReader)
				IL_0026: castclass Philips.PSG.Gateway.Catalog
				IL_002b: stloc.2
				IL_002c: leave.s IL_004c
			} // end .try
			catch [mscorlib]System.Exception
			{
				IL_002e: stloc.3
				IL_002f: ldstr "Catalog: Error - failed to create catalog from file: {0}"
				IL_0034: ldloc.3
				IL_0035: call void [mscorlib]System.Console::WriteLine(string, object)
				IL_003a: newobj instance void Philips.PSG.Gateway.Catalog::.ctor()
				IL_003f: stloc.2
				IL_0040: leave.s IL_004c
			} // end handler
		} // end .try
		finally
		{
			IL_0042: ldloc.1
			IL_0043: brfalse.s IL_004b

			IL_0045: ldloc.1
			IL_0046: callvirt instance void [mscorlib]System.IO.TextReader::Close()

			IL_004b: endfinally
		} // end handler

		IL_004c: ldloc.2
		IL_004d: ret
	} // end of method Catalog::CreateFromFile

	// Properties
	.property instance class Philips.PSG.Gateway.CatalogEntry[] Entries()
	{
		.get instance class Philips.PSG.Gateway.CatalogEntry[] Philips.PSG.Gateway.Catalog::get_Entries()
		.set instance void Philips.PSG.Gateway.Catalog::set_Entries(class Philips.PSG.Gateway.CatalogEntry[])
	}
	.property instance int32 NumberOfEntries()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Catalog::get_NumberOfEntries()
	}

} // end of class Philips.PSG.Gateway.Catalog

.class public auto ansi serializable beforefieldinit Philips.PSG.Gateway.CatalogEntry
	extends [mscorlib]System.Object
{
	// Nested Types
	.class nested public auto ansi sealed BuildAction
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Undefined = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Copy = int32(1)
		.field public static literal valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Generate = int32(2)
		.field public static literal valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Merge = int32(3)
		.field public static literal valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction GenerateAndMerge = int32(4)

	} // end of class BuildAction


	// Fields
	.field private string _targetTvModel
	.field private string _baseFolder
	.field private string _outputFileName
	.field private class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet> _fileSets
	.field private valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction _action
	.field private class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> _defaultScheduleEvents

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x50bc
		// Header size: 12
		// Code size: 69 (0x45)
		.maxstack 2

		IL_0000: ldarg.0
		IL_0001: call instance void [mscorlib]System.Object::.ctor()
		IL_0006: ldarg.0
		IL_0007: ldsfld string [mscorlib]System.String::Empty
		IL_000c: stfld string Philips.PSG.Gateway.CatalogEntry::_targetTvModel
		IL_0011: ldarg.0
		IL_0012: ldsfld string [mscorlib]System.String::Empty
		IL_0017: stfld string Philips.PSG.Gateway.CatalogEntry::_baseFolder
		IL_001c: ldarg.0
		IL_001d: ldsfld string [mscorlib]System.String::Empty
		IL_0022: stfld string Philips.PSG.Gateway.CatalogEntry::_outputFileName
		IL_0027: ldarg.0
		IL_0028: newobj instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::.ctor()
		IL_002d: stfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet> Philips.PSG.Gateway.CatalogEntry::_fileSets
		IL_0032: ldarg.0
		IL_0033: ldc.i4.0
		IL_0034: stfld valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Philips.PSG.Gateway.CatalogEntry::_action
		IL_0039: ldarg.0
		IL_003a: newobj instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::.ctor()
		IL_003f: stfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.CatalogEntry::_defaultScheduleEvents
		IL_0044: ret
	} // end of method CatalogEntry::.ctor

	.method public hidebysig specialname 
		instance string get_TargetTvModel () cil managed 
	{
		// Method begins at RVA 0x510d
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.CatalogEntry::_targetTvModel
		IL_0006: ret
	} // end of method CatalogEntry::get_TargetTvModel

	.method public hidebysig specialname 
		instance void set_TargetTvModel (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x5115
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brtrue.s IL_000f

		IL_0003: ldarg.0
		IL_0004: ldsfld string [mscorlib]System.String::Empty
		IL_0009: stfld string Philips.PSG.Gateway.CatalogEntry::_targetTvModel
		IL_000e: ret

		IL_000f: ldarg.0
		IL_0010: ldarg.1
		IL_0011: stfld string Philips.PSG.Gateway.CatalogEntry::_targetTvModel
		IL_0016: ret
	} // end of method CatalogEntry::set_TargetTvModel

	.method public hidebysig specialname 
		instance string get_BaseFolder () cil managed 
	{
		// Method begins at RVA 0x512d
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.CatalogEntry::_baseFolder
		IL_0006: ret
	} // end of method CatalogEntry::get_BaseFolder

	.method public hidebysig specialname 
		instance void set_BaseFolder (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x5135
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brtrue.s IL_000f

		IL_0003: ldarg.0
		IL_0004: ldsfld string [mscorlib]System.String::Empty
		IL_0009: stfld string Philips.PSG.Gateway.CatalogEntry::_baseFolder
		IL_000e: ret

		IL_000f: ldarg.0
		IL_0010: ldarg.1
		IL_0011: stfld string Philips.PSG.Gateway.CatalogEntry::_baseFolder
		IL_0016: ret
	} // end of method CatalogEntry::set_BaseFolder

	.method public hidebysig specialname 
		instance string get_OutputFileName () cil managed 
	{
		// Method begins at RVA 0x514d
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.CatalogEntry::_outputFileName
		IL_0006: ret
	} // end of method CatalogEntry::get_OutputFileName

	.method public hidebysig specialname 
		instance void set_OutputFileName (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x5155
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brtrue.s IL_000f

		IL_0003: ldarg.0
		IL_0004: ldsfld string [mscorlib]System.String::Empty
		IL_0009: stfld string Philips.PSG.Gateway.CatalogEntry::_outputFileName
		IL_000e: ret

		IL_000f: ldarg.0
		IL_0010: ldarg.1
		IL_0011: stfld string Philips.PSG.Gateway.CatalogEntry::_outputFileName
		IL_0016: ret
	} // end of method CatalogEntry::set_OutputFileName

	.method public hidebysig specialname 
		instance class Philips.PSG.Gateway.FileSet[] get_FileSets () cil managed 
	{
		// Method begins at RVA 0x516d
		// Header size: 1
		// Code size: 12 (0xc)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet> Philips.PSG.Gateway.CatalogEntry::_fileSets
		IL_0006: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::ToArray()
		IL_000b: ret
	} // end of method CatalogEntry::get_FileSets

	.method public hidebysig specialname 
		instance void set_FileSets (
			class Philips.PSG.Gateway.FileSet[] 'value'
		) cil managed 
	{
		// Method begins at RVA 0x517a
		// Header size: 1
		// Code size: 27 (0x1b)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet> Philips.PSG.Gateway.CatalogEntry::_fileSets
		IL_0006: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Clear()
		IL_000b: ldarg.1
		IL_000c: brfalse.s IL_001a

		IL_000e: ldarg.0
		IL_000f: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet> Philips.PSG.Gateway.CatalogEntry::_fileSets
		IL_0014: ldarg.1
		IL_0015: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::AddRange(class [mscorlib]System.Collections.Generic.IEnumerable`1<!0>)

		IL_001a: ret
	} // end of method CatalogEntry::set_FileSets

	.method public hidebysig specialname 
		instance valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction get_Action () cil managed 
	{
		// Method begins at RVA 0x5196
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Philips.PSG.Gateway.CatalogEntry::_action
		IL_0006: ret
	} // end of method CatalogEntry::get_Action

	.method public hidebysig specialname 
		instance void set_Action (
			valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction 'value'
		) cil managed 
	{
		// Method begins at RVA 0x519e
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Philips.PSG.Gateway.CatalogEntry::_action
		IL_0007: ret
	} // end of method CatalogEntry::set_Action

	.method public hidebysig specialname 
		instance class Philips.PSG.Gateway.ScheduleEvent[] get_DefaultScheduleEvents () cil managed 
	{
		// Method begins at RVA 0x51a7
		// Header size: 1
		// Code size: 12 (0xc)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.CatalogEntry::_defaultScheduleEvents
		IL_0006: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::ToArray()
		IL_000b: ret
	} // end of method CatalogEntry::get_DefaultScheduleEvents

	.method public hidebysig specialname 
		instance void set_DefaultScheduleEvents (
			class Philips.PSG.Gateway.ScheduleEvent[] 'value'
		) cil managed 
	{
		// Method begins at RVA 0x51b4
		// Header size: 12
		// Code size: 84 (0x54)
		.maxstack 2
		.locals init (
			[0] valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.CatalogEntry::_defaultScheduleEvents
		IL_0006: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Clear()
		IL_000b: ldarg.1
		IL_000c: brfalse.s IL_0053

		IL_000e: ldarg.0
		IL_000f: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.CatalogEntry::_defaultScheduleEvents
		IL_0014: ldarg.1
		IL_0015: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::AddRange(class [mscorlib]System.Collections.Generic.IEnumerable`1<!0>)
		IL_001a: ldarg.0
		IL_001b: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.CatalogEntry::_defaultScheduleEvents
		IL_0020: callvirt instance valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<!0> class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::GetEnumerator()
		IL_0025: stloc.0
		.try
		{
			IL_0026: br.s IL_003a
			// loop start (head: IL_003a)
				IL_0028: ldloca.s 0
				IL_002a: call instance !0 valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>::get_Current()
				IL_002f: ldarg.0
				IL_0030: ldfld string Philips.PSG.Gateway.CatalogEntry::_targetTvModel
				IL_0035: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_TargetTvModel(string)

				IL_003a: ldloca.s 0
				IL_003c: call instance bool valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>::MoveNext()
				IL_0041: brtrue.s IL_0028
			// end loop

			IL_0043: leave.s IL_0053
		} // end .try
		finally
		{
			IL_0045: ldloca.s 0
			IL_0047: constrained. valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>
			IL_004d: callvirt instance void [mscorlib]System.IDisposable::Dispose()
			IL_0052: endfinally
		} // end handler

		IL_0053: ret
	} // end of method CatalogEntry::set_DefaultScheduleEvents

	.method private hidebysig 
		instance void AddToLog (
			class [mscorlib]System.IO.StreamWriter logStream,
			string text
		) cil managed 
	{
		// Method begins at RVA 0x5224
		// Header size: 1
		// Code size: 11 (0xb)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brfalse.s IL_000a

		IL_0003: ldarg.1
		IL_0004: ldarg.2
		IL_0005: callvirt instance void [mscorlib]System.IO.TextWriter::WriteLine(string)

		IL_000a: ret
	} // end of method CatalogEntry::AddToLog

	.method public hidebysig 
		instance bool IsValidCandidate (
			string rootFolder,
			class [mscorlib]System.IO.StreamWriter logStream
		) cil managed 
	{
		// Method begins at RVA 0x5230
		// Header size: 12
		// Code size: 905 (0x389)
		.maxstack 5
		.locals init (
			[0] bool,
			[1] string,
			[2] string,
			[3] class [mscorlib]System.IO.DirectoryInfo,
			[4] class [mscorlib]System.IO.FileInfo[],
			[5] int32,
			[6] int32,
			[7] bool,
			[8] bool,
			[9] valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.FileSet>,
			[10] class Philips.PSG.Gateway.FileSet,
			[11] bool,
			[12] class [mscorlib]System.Collections.Generic.List`1<string>,
			[13] string,
			[14] string[],
			[15] int32,
			[16] string,
			[17] string,
			[18] valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>,
			[19] string,
			[20] class [mscorlib]System.Exception
		)

		IL_0000: ldarg.0
		IL_0001: ldarg.2
		IL_0002: ldsfld string [mscorlib]System.String::Empty
		IL_0007: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)
		IL_000c: ldarg.0
		IL_000d: ldarg.2
		IL_000e: ldstr "Checking candidate status for {0}"
		IL_0013: ldarg.0
		IL_0014: ldfld string Philips.PSG.Gateway.CatalogEntry::_targetTvModel
		IL_0019: call string [mscorlib]System.String::Format(string, object)
		IL_001e: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)
		IL_0023: ldc.i4.0
		IL_0024: stloc.0
		IL_0025: ldarg.1
		IL_0026: ldstr "\\"
		IL_002b: ldarg.0
		IL_002c: ldfld string Philips.PSG.Gateway.CatalogEntry::_baseFolder
		IL_0031: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0036: stloc.1
		IL_0037: ldarg.0
		IL_0038: ldfld string Philips.PSG.Gateway.CatalogEntry::_baseFolder
		IL_003d: ldstr "2k12_EasySuite"
		IL_0042: stloc.2
		IL_0043: ldloc.2
		IL_0044: callvirt instance int32 [mscorlib]System.String::IndexOf(string)
		IL_0049: ldc.i4.m1
		IL_004a: ble.s IL_006f

		IL_004c: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0051: ldstr "2k12_EasySuite"
		IL_0056: stfld string Philips.PSG.Gateway.Config::TsModelName
		IL_005b: ldarg.0
		IL_005c: ldarg.2
		IL_005d: ldstr "Looking for root folder {0}"
		IL_0062: ldloc.2
		IL_0063: call string [mscorlib]System.String::Format(string, object)
		IL_0068: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)
		IL_006d: br.s IL_0094

		IL_006f: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0074: ldstr "none"
		IL_0079: stfld string Philips.PSG.Gateway.Config::TsModelName
		IL_007e: ldarg.0
		IL_007f: ldarg.2
		IL_0080: ldstr "Looking for root folder {0}"
		IL_0085: ldstr "not 2k12_EasySuite"
		IL_008a: call string [mscorlib]System.String::Format(string, object)
		IL_008f: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)

		IL_0094: ldarg.0
		IL_0095: ldarg.2
		IL_0096: ldstr "Looking for root folder {0}"
		IL_009b: ldloc.1
		IL_009c: call string [mscorlib]System.String::Format(string, object)
		IL_00a1: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)
		IL_00a6: ldloc.1
		IL_00a7: call bool [mscorlib]System.IO.Directory::Exists(string)
		IL_00ac: brfalse IL_0352

		IL_00b1: ldarg.0
		IL_00b2: ldarg.2
		IL_00b3: ldstr "Folder exists, looking for files"
		IL_00b8: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)
		.try
		{
			IL_00bd: ldloc.1
			IL_00be: newobj instance void [mscorlib]System.IO.DirectoryInfo::.ctor(string)
			IL_00c3: stloc.3
			IL_00c4: ldc.i4.0
			IL_00c5: stloc.s 5
			IL_00c7: ldc.i4.0
			IL_00c8: stloc.s 6
			IL_00ca: ldc.i4.0
			IL_00cb: stloc.s 7
			IL_00cd: ldc.i4.0
			IL_00ce: stloc.s 8
			IL_00d0: ldarg.0
			IL_00d1: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet> Philips.PSG.Gateway.CatalogEntry::_fileSets
			IL_00d6: callvirt instance valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<!0> class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::GetEnumerator()
			IL_00db: stloc.s 9
			.try
			{
				IL_00dd: br IL_0251
				// loop start (head: IL_0251)
					IL_00e2: ldloca.s 9
					IL_00e4: call instance !0 valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.FileSet>::get_Current()
					IL_00e9: stloc.s 10
					IL_00eb: ldloc.s 10
					IL_00ed: callvirt instance valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement Philips.PSG.Gateway.FileSet::get_Requirement()
					IL_00f2: ldc.i4.1
					IL_00f3: ceq
					IL_00f5: stloc.s 11
					IL_00f7: newobj instance void class [mscorlib]System.Collections.Generic.List`1<string>::.ctor()
					IL_00fc: stloc.s 12
					IL_00fe: ldloc.1
					IL_00ff: ldstr "\\"
					IL_0104: ldloc.s 10
					IL_0106: callvirt instance string Philips.PSG.Gateway.FileSet::get_PrefixFolder()
					IL_010b: call string [mscorlib]System.String::Concat(string, string, string)
					IL_0110: stloc.s 13
					IL_0112: ldloc.s 10
					IL_0114: callvirt instance string Philips.PSG.Gateway.FileSet::get_PrefixFolder()
					IL_0119: call bool [mscorlib]System.String::IsNullOrEmpty(string)
					IL_011e: brtrue.s IL_0128

					IL_0120: ldloc.s 13
					IL_0122: newobj instance void [mscorlib]System.IO.DirectoryInfo::.ctor(string)
					IL_0127: stloc.3

					IL_0128: ldloc.s 10
					IL_012a: callvirt instance string[] Philips.PSG.Gateway.FileSet::get_Files()
					IL_012f: stloc.s 14
					IL_0131: ldc.i4.0
					IL_0132: stloc.s 15
					IL_0134: br IL_01cc
					// loop start (head: IL_01cc)
						IL_0139: ldloc.s 14
						IL_013b: ldloc.s 15
						IL_013d: ldelem.ref
						IL_013e: stloc.s 16
						IL_0140: ldloc.s 10
						IL_0142: callvirt instance string Philips.PSG.Gateway.FileSet::get_PrefixFolder()
						IL_0147: call bool [mscorlib]System.String::IsNullOrEmpty(string)
						IL_014c: brfalse.s IL_015d

						IL_014e: ldloc.1
						IL_014f: ldstr "\\"
						IL_0154: ldloc.s 16
						IL_0156: call string [mscorlib]System.String::Concat(string, string, string)
						IL_015b: br.s IL_016b

						IL_015d: ldloc.s 13
						IL_015f: ldstr "\\"
						IL_0164: ldloc.s 16
						IL_0166: call string [mscorlib]System.String::Concat(string, string, string)

						IL_016b: stloc.s 17
						.try
						{
							IL_016d: ldloc.3
							IL_016e: ldloc.s 16
							IL_0170: callvirt instance class [mscorlib]System.IO.FileInfo[] [mscorlib]System.IO.DirectoryInfo::GetFiles(string)
							IL_0175: stloc.s 4
							IL_0177: ldloc.s 4
							IL_0179: ldlen
							IL_017a: brtrue.s IL_0187

							IL_017c: ldloc.s 12
							IL_017e: ldloc.s 17
							IL_0180: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
							IL_0185: br.s IL_01b8

							IL_0187: ldloc.s 4
							IL_0189: ldlen
							IL_018a: conv.i4
							IL_018b: ldc.i4.1
							IL_018c: bne.un.s IL_0199

							IL_018e: ldloc.s 5
							IL_0190: ldc.i4.1
							IL_0191: add
							IL_0192: stloc.s 5
							IL_0194: ldc.i4.1
							IL_0195: stloc.s 11
							IL_0197: br.s IL_01b8

							IL_0199: ldarg.0
							IL_019a: ldarg.2
							IL_019b: ldstr "File {0} has more than one match"
							IL_01a0: ldloc.s 16
							IL_01a2: call string [mscorlib]System.String::Format(string, object)
							IL_01a7: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)
							IL_01ac: ldloc.s 5
							IL_01ae: ldc.i4.1
							IL_01af: add
							IL_01b0: stloc.s 5
							IL_01b2: ldc.i4.1
							IL_01b3: stloc.s 8
							IL_01b5: ldc.i4.1
							IL_01b6: stloc.s 11

							IL_01b8: leave.s IL_01c6
						} // end .try
						catch [mscorlib]System.IO.DirectoryNotFoundException
						{
							IL_01ba: pop
							IL_01bb: ldloc.s 12
							IL_01bd: ldloc.s 17
							IL_01bf: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
							IL_01c4: leave.s IL_01c6
						} // end handler

						IL_01c6: ldloc.s 15
						IL_01c8: ldc.i4.1
						IL_01c9: add
						IL_01ca: stloc.s 15

						IL_01cc: ldloc.s 15
						IL_01ce: ldloc.s 14
						IL_01d0: ldlen
						IL_01d1: conv.i4
						IL_01d2: blt IL_0139
					// end loop

					IL_01d7: ldloc.s 10
					IL_01d9: ldloc.s 12
					IL_01db: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<string>::get_Count()
					IL_01e0: ldc.i4.0
					IL_01e1: cgt
					IL_01e3: ldc.i4.0
					IL_01e4: ceq
					IL_01e6: callvirt instance void Philips.PSG.Gateway.FileSet::set_IsValid(bool)
					IL_01eb: ldloc.s 12
					IL_01ed: callvirt instance valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<!0> class [mscorlib]System.Collections.Generic.List`1<string>::GetEnumerator()
					IL_01f2: stloc.s 18
					.try
					{
						IL_01f4: br.s IL_0212
						// loop start (head: IL_0212)
							IL_01f6: ldloca.s 18
							IL_01f8: call instance !0 valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>::get_Current()
							IL_01fd: stloc.s 19
							IL_01ff: ldarg.0
							IL_0200: ldarg.2
							IL_0201: ldstr "File {0} is missing"
							IL_0206: ldloc.s 19
							IL_0208: call string [mscorlib]System.String::Format(string, object)
							IL_020d: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)

							IL_0212: ldloca.s 18
							IL_0214: call instance bool valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>::MoveNext()
							IL_0219: brtrue.s IL_01f6
						// end loop

						IL_021b: leave.s IL_022b
					} // end .try
					finally
					{
						IL_021d: ldloca.s 18
						IL_021f: constrained. valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>
						IL_0225: callvirt instance void [mscorlib]System.IDisposable::Dispose()
						IL_022a: endfinally
					} // end handler

					IL_022b: ldloc.s 11
					IL_022d: brfalse.s IL_0248

					IL_022f: ldloc.s 6
					IL_0231: ldloc.s 10
					IL_0233: callvirt instance int32 Philips.PSG.Gateway.FileSet::get_NumberOfFiles()
					IL_0238: add
					IL_0239: stloc.s 6
					IL_023b: ldloc.s 12
					IL_023d: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<string>::get_Count()
					IL_0242: ldc.i4.0
					IL_0243: ble.s IL_0248

					IL_0245: ldc.i4.1
					IL_0246: stloc.s 7

					IL_0248: ldloc.s 10
					IL_024a: ldloc.s 11
					IL_024c: callvirt instance void Philips.PSG.Gateway.FileSet::set_IncludedInBuild(bool)

					IL_0251: ldloca.s 9
					IL_0253: call instance bool valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.FileSet>::MoveNext()
					IL_0258: brtrue IL_00e2
				// end loop

				IL_025d: leave.s IL_026d
			} // end .try
			finally
			{
				IL_025f: ldloca.s 9
				IL_0261: constrained. valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.FileSet>
				IL_0267: callvirt instance void [mscorlib]System.IDisposable::Dispose()
				IL_026c: endfinally
			} // end handler

			IL_026d: ldloc.s 8
			IL_026f: brfalse.s IL_02c5

			IL_0271: call string Philips.PSG.Gateway.Resources::get_DLG_CatalogEntryInvalidMultipleFiles()
			IL_0276: ldarg.0
			IL_0277: ldfld string Philips.PSG.Gateway.CatalogEntry::_targetTvModel
			IL_027c: call string [mscorlib]System.String::Format(string, object)
			IL_0281: ldstr " "
			IL_0286: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
			IL_028b: callvirt instance string [mscorlib]System.Object::ToString()
			IL_0290: call string [mscorlib]System.String::Concat(string, string, string)
			IL_0295: call void [mscorlib]System.Console::WriteLine(string)
			IL_029a: ldarg.0
			IL_029b: ldarg.2
			IL_029c: call string Philips.PSG.Gateway.Resources::get_DLG_CatalogEntryInvalidMultipleFiles()
			IL_02a1: ldarg.0
			IL_02a2: ldfld string Philips.PSG.Gateway.CatalogEntry::_targetTvModel
			IL_02a7: call string [mscorlib]System.String::Format(string, object)
			IL_02ac: ldstr " "
			IL_02b1: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
			IL_02b6: callvirt instance string [mscorlib]System.Object::ToString()
			IL_02bb: call string [mscorlib]System.String::Concat(string, string, string)
			IL_02c0: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)

			IL_02c5: ldloc.s 7
			IL_02c7: brfalse.s IL_031d

			IL_02c9: call string Philips.PSG.Gateway.Resources::get_DLG_CatalogEntryInvalidPartialFileSet()
			IL_02ce: ldarg.0
			IL_02cf: ldfld string Philips.PSG.Gateway.CatalogEntry::_targetTvModel
			IL_02d4: call string [mscorlib]System.String::Format(string, object)
			IL_02d9: ldstr " "
			IL_02de: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
			IL_02e3: callvirt instance string [mscorlib]System.Object::ToString()
			IL_02e8: call string [mscorlib]System.String::Concat(string, string, string)
			IL_02ed: call void [mscorlib]System.Console::WriteLine(string)
			IL_02f2: ldarg.0
			IL_02f3: ldarg.2
			IL_02f4: call string Philips.PSG.Gateway.Resources::get_DLG_CatalogEntryInvalidPartialFileSet()
			IL_02f9: ldarg.0
			IL_02fa: ldfld string Philips.PSG.Gateway.CatalogEntry::_targetTvModel
			IL_02ff: call string [mscorlib]System.String::Format(string, object)
			IL_0304: ldstr " "
			IL_0309: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
			IL_030e: callvirt instance string [mscorlib]System.Object::ToString()
			IL_0313: call string [mscorlib]System.String::Concat(string, string, string)
			IL_0318: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)

			IL_031d: ldloc.s 5
			IL_031f: ldc.i4.0
			IL_0320: ble.s IL_0332

			IL_0322: ldloc.s 5
			IL_0324: ldloc.s 6
			IL_0326: bne.un.s IL_0332

			IL_0328: ldloc.s 7
			IL_032a: brtrue.s IL_0332

			IL_032c: ldloc.s 8
			IL_032e: brtrue.s IL_0332

			IL_0330: ldc.i4.1
			IL_0331: stloc.0

			IL_0332: leave.s IL_035e
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_0334: stloc.s 20
			IL_0336: ldarg.0
			IL_0337: ldarg.2
			IL_0338: ldstr "Exception during candidate processing: {0}"
			IL_033d: ldloc.s 20
			IL_033f: callvirt instance string [mscorlib]System.Exception::get_Message()
			IL_0344: call string [mscorlib]System.String::Format(string, object)
			IL_0349: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)
			IL_034e: ldc.i4.0
			IL_034f: stloc.0
			IL_0350: leave.s IL_035e
		} // end handler

		IL_0352: ldarg.0
		IL_0353: ldarg.2
		IL_0354: ldstr "Folder does not exist"
		IL_0359: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)

		IL_035e: ldloc.0
		IL_035f: brfalse.s IL_037b

		IL_0361: ldarg.0
		IL_0362: ldarg.2
		IL_0363: ldstr "Atleast one required file exist"
		IL_0368: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)
		IL_036d: ldarg.0
		IL_036e: ldarg.2
		IL_036f: ldstr "Candidate is valid and will be included in the build"
		IL_0374: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)
		IL_0379: br.s IL_0387

		IL_037b: ldarg.0
		IL_037c: ldarg.2
		IL_037d: ldstr "Candidate is not valid and will be excluded from the build"
		IL_0382: call instance void Philips.PSG.Gateway.CatalogEntry::AddToLog(class [mscorlib]System.IO.StreamWriter, string)

		IL_0387: ldloc.0
		IL_0388: ret
	} // end of method CatalogEntry::IsValidCandidate

	// Properties
	.property instance string TargetTvModel()
	{
		.get instance string Philips.PSG.Gateway.CatalogEntry::get_TargetTvModel()
		.set instance void Philips.PSG.Gateway.CatalogEntry::set_TargetTvModel(string)
	}
	.property instance string BaseFolder()
	{
		.get instance string Philips.PSG.Gateway.CatalogEntry::get_BaseFolder()
		.set instance void Philips.PSG.Gateway.CatalogEntry::set_BaseFolder(string)
	}
	.property instance string OutputFileName()
	{
		.get instance string Philips.PSG.Gateway.CatalogEntry::get_OutputFileName()
		.set instance void Philips.PSG.Gateway.CatalogEntry::set_OutputFileName(string)
	}
	.property instance class Philips.PSG.Gateway.FileSet[] FileSets()
	{
		.get instance class Philips.PSG.Gateway.FileSet[] Philips.PSG.Gateway.CatalogEntry::get_FileSets()
		.set instance void Philips.PSG.Gateway.CatalogEntry::set_FileSets(class Philips.PSG.Gateway.FileSet[])
	}
	.property instance valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Action()
	{
		.get instance valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Philips.PSG.Gateway.CatalogEntry::get_Action()
		.set instance void Philips.PSG.Gateway.CatalogEntry::set_Action(valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction)
	}
	.property instance class Philips.PSG.Gateway.ScheduleEvent[] DefaultScheduleEvents()
	{
		.get instance class Philips.PSG.Gateway.ScheduleEvent[] Philips.PSG.Gateway.CatalogEntry::get_DefaultScheduleEvents()
		.set instance void Philips.PSG.Gateway.CatalogEntry::set_DefaultScheduleEvents(class Philips.PSG.Gateway.ScheduleEvent[])
	}

} // end of class Philips.PSG.Gateway.CatalogEntry

.class public auto ansi sealed Philips.PSG.Gateway.PlayStreamHandlerErrored
	extends [mscorlib]System.MulticastDelegate
{
	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor (
			object 'object',
			native int 'method'
		) runtime managed 
	{
	} // end of method PlayStreamHandlerErrored::.ctor

	.method public hidebysig newslot virtual 
		instance void Invoke (
			string message,
			class [mscorlib]System.Exception ex
		) runtime managed 
	{
	} // end of method PlayStreamHandlerErrored::Invoke

	.method public hidebysig newslot virtual 
		instance class [mscorlib]System.IAsyncResult BeginInvoke (
			string message,
			class [mscorlib]System.Exception ex,
			class [mscorlib]System.AsyncCallback callback,
			object 'object'
		) runtime managed 
	{
	} // end of method PlayStreamHandlerErrored::BeginInvoke

	.method public hidebysig newslot virtual 
		instance void EndInvoke (
			class [mscorlib]System.IAsyncResult result
		) runtime managed 
	{
	} // end of method PlayStreamHandlerErrored::EndInvoke

} // end of class Philips.PSG.Gateway.PlayStreamHandlerErrored

.class public auto ansi sealed beforefieldinit Philips.PSG.Gateway.DekTecManager
	extends [mscorlib]System.Object
{
	// Nested Types
	.class nested private auto ansi sealed DelegateUiThread
		extends [mscorlib]System.MulticastDelegate
	{
		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor (
				object 'object',
				native int 'method'
			) runtime managed 
		{
		} // end of method DelegateUiThread::.ctor

		.method public hidebysig newslot virtual 
			instance void Invoke () runtime managed 
		{
		} // end of method DelegateUiThread::Invoke

		.method public hidebysig newslot virtual 
			instance class [mscorlib]System.IAsyncResult BeginInvoke (
				class [mscorlib]System.AsyncCallback callback,
				object 'object'
			) runtime managed 
		{
		} // end of method DelegateUiThread::BeginInvoke

		.method public hidebysig newslot virtual 
			instance void EndInvoke (
				class [mscorlib]System.IAsyncResult result
			) runtime managed 
		{
		} // end of method DelegateUiThread::EndInvoke

	} // end of class DelegateUiThread

	.class nested private auto ansi sealed beforefieldinit '<>c__DisplayClass29_0'
		extends [mscorlib]System.Object
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Fields
		.field public class Philips.PSG.Gateway.DekTecManager '<>4__this'
		.field public class [mscorlib]System.Exception exception

		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor () cil managed 
		{
			// Method begins at RVA 0x8618
			// Header size: 1
			// Code size: 7 (0x7)
			.maxstack 8

			IL_0000: ldarg.0
			IL_0001: call instance void [mscorlib]System.Object::.ctor()
			IL_0006: ret
		} // end of method '<>c__DisplayClass29_0'::.ctor

		.method assembly hidebysig 
			instance void '<PBT_PlaybackHandler>b__0' () cil managed 
		{
			// Method begins at RVA 0x9eb0
			// Header size: 1
			// Code size: 34 (0x22)
			.maxstack 8

			IL_0000: ldarg.0
			IL_0001: ldfld class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager/'<>c__DisplayClass29_0'::'<>4__this'
			IL_0006: ldfld class Philips.PSG.Gateway.PlayStreamHandlerErrored Philips.PSG.Gateway.DekTecManager::_onErrorPlayStreamCallback
			IL_000b: ldarg.0
			IL_000c: ldfld class [mscorlib]System.Exception Philips.PSG.Gateway.DekTecManager/'<>c__DisplayClass29_0'::exception
			IL_0011: callvirt instance string [mscorlib]System.Exception::get_Message()
			IL_0016: ldarg.0
			IL_0017: ldfld class [mscorlib]System.Exception Philips.PSG.Gateway.DekTecManager/'<>c__DisplayClass29_0'::exception
			IL_001c: callvirt instance void Philips.PSG.Gateway.PlayStreamHandlerErrored::Invoke(string, class [mscorlib]System.Exception)
			IL_0021: ret
		} // end of method '<>c__DisplayClass29_0'::'<PBT_PlaybackHandler>b__0'

	} // end of class <>c__DisplayClass29_0


	// Fields
	.field private class Philips.PSG.Gateway.PlayStreamHandlerErrored _onErrorPlayStreamCallback
	.field private class [WindowsBase]System.Windows.Threading.Dispatcher uiThread
	.field private static initonly class Philips.PSG.Gateway.DekTecManager _instance
	.field private static literal int32 SCAN_DEVICE_MAX = int32(20)
	.field private static literal int32 PLAYBACK_THREAD_EXIT_TIMEOUT = int32(20000)
	.field private static literal int32 PLAYBACK_FIFO_WAIT_PERIOD = int32(500)
	.field private static literal int32 PLAYBACK_TRANSFER_BUFFER_SIZE = int32(1048576)
	.field private static literal int32 PLAYBACK_MIN_FIFO_LOAD = int32(3145728)
	.field private bool _disposed
	.field private string _attachedDeviceType
	.field private class [DTAPINET]DTAPINET.DtDevice _device
	.field private class [DTAPINET]DTAPINET.DtOutpChannel _outputChannel
	.field private class [mscorlib]System.Threading.Thread _playbackThread
	.field private bool _playbackThreadExit
	.field private uint8[] _transferBuffer

	// Methods
	.method private hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x562c
		// Header size: 12
		// Code size: 167 (0xa7)
		.maxstack 3
		.locals init (
			[0] class [DTAPINET]DTAPINET.DtHwFuncDesc
		)

		IL_0000: ldarg.0
		IL_0001: call instance void [mscorlib]System.Object::.ctor()
		IL_0006: ldarg.0
		IL_0007: call class [WindowsBase]System.Windows.Threading.Dispatcher [WindowsBase]System.Windows.Threading.Dispatcher::get_CurrentDispatcher()
		IL_000c: stfld class [WindowsBase]System.Windows.Threading.Dispatcher Philips.PSG.Gateway.DekTecManager::uiThread
		IL_0011: ldarg.0
		IL_0012: ldc.i4.0
		IL_0013: stfld bool Philips.PSG.Gateway.DekTecManager::_disposed
		IL_0018: ldarg.0
		IL_0019: ldsfld string [mscorlib]System.String::Empty
		IL_001e: stfld string Philips.PSG.Gateway.DekTecManager::_attachedDeviceType
		IL_0023: ldarg.0
		IL_0024: ldnull
		IL_0025: stfld class [mscorlib]System.Threading.Thread Philips.PSG.Gateway.DekTecManager::_playbackThread
		IL_002a: ldarg.0
		IL_002b: ldc.i4.0
		IL_002c: stfld bool Philips.PSG.Gateway.DekTecManager::_playbackThreadExit
		IL_0031: ldarg.0
		IL_0032: ldc.i4 1048576
		IL_0037: newarr [mscorlib]System.Byte
		IL_003c: stfld uint8[] Philips.PSG.Gateway.DekTecManager::_transferBuffer
		IL_0041: ldarg.0
		IL_0042: newobj instance void [DTAPINET]DTAPINET.DtDevice::.ctor()
		IL_0047: stfld class [DTAPINET]DTAPINET.DtDevice Philips.PSG.Gateway.DekTecManager::_device
		IL_004c: ldarg.0
		IL_004d: newobj instance void [DTAPINET]DTAPINET.DtOutpChannel::.ctor()
		IL_0052: stfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_0057: newobj instance void [DTAPINET]DTAPINET.DtHwFuncDesc::.ctor()
		IL_005c: stloc.0
		IL_005d: ldarg.0
		IL_005e: ldloca.s 0
		IL_0060: call instance bool Philips.PSG.Gateway.DekTecManager::ScanForSupportedDevice(class [DTAPINET]DTAPINET.DtHwFuncDesc&)
		IL_0065: brtrue.s IL_0072

		IL_0067: ldstr "DekTecManager: Error - device scan failed"
		IL_006c: call void [mscorlib]System.Console::WriteLine(string)
		IL_0071: ret

		IL_0072: ldarg.0
		IL_0073: ldloc.0
		IL_0074: call instance bool Philips.PSG.Gateway.DekTecManager::AttachDevice(class [DTAPINET]DTAPINET.DtHwFuncDesc)
		IL_0079: brtrue.s IL_0086

		IL_007b: ldstr "DekTecManager: Error - device attach failed"
		IL_0080: call void [mscorlib]System.Console::WriteLine(string)
		IL_0085: ret

		IL_0086: ldarg.0
		IL_0087: ldstr "DTA-{0}"
		IL_008c: ldloc.0
		IL_008d: ldfld class [DTAPINET]DTAPINET.DtDeviceDesc [DTAPINET]DTAPINET.DtHwFuncDesc::m_DvcDesc
		IL_0092: ldfld int32 [DTAPINET]DTAPINET.DtDeviceDesc::m_TypeNumber
		IL_0097: box [mscorlib]System.Int32
		IL_009c: call string [mscorlib]System.String::Format(string, object)
		IL_00a1: stfld string Philips.PSG.Gateway.DekTecManager::_attachedDeviceType
		IL_00a6: ret
	} // end of method DekTecManager::.ctor

	.method private hidebysig 
		instance bool ScanForSupportedDevice (
			class [DTAPINET]DTAPINET.DtHwFuncDesc& deviceFuncDesc
		) cil managed 
	{
		// Method begins at RVA 0x56e0
		// Header size: 12
		// Code size: 100 (0x64)
		.maxstack 3
		.locals init (
			[0] class [DTAPINET]DTAPINET.DtHwFuncDesc[],
			[1] int32,
			[2] int32,
			[3] class Philips.PSG.Gateway.Config,
			[4] bool
		)

		IL_0000: ldc.i4.s 20
		IL_0002: newarr [DTAPINET]DTAPINET.DtHwFuncDesc
		IL_0007: stloc.0
		IL_0008: ldc.i4.0
		IL_0009: stloc.1
		IL_000a: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_000f: stloc.3
		.try
		{
			IL_0010: ldc.i4.s 20
			IL_0012: ldloca.s 1
			IL_0014: ldloc.0
			IL_0015: call valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtGlobal::DtapiHwFuncScan(int32, int32&, class [DTAPINET]DTAPINET.DtHwFuncDesc[])
			IL_001a: brfalse.s IL_0021

			IL_001c: ldc.i4.0
			IL_001d: stloc.s 4
			IL_001f: leave.s IL_0061

			IL_0021: ldc.i4.0
			IL_0022: stloc.2
			IL_0023: br.s IL_0053
			// loop start (head: IL_0053)
				IL_0025: ldloc.0
				IL_0026: ldloc.2
				IL_0027: ldelem.ref
				IL_0028: ldfld class [DTAPINET]DTAPINET.DtCaps [DTAPINET]DTAPINET.DtHwFuncDesc::m_Flags
				IL_002d: ldloc.3
				IL_002e: callvirt instance class [DTAPINET]DTAPINET.DtCaps Philips.PSG.Gateway.Config::get_DeviceCapabilityFlags()
				IL_0033: call class [DTAPINET]DTAPINET.DtCaps [DTAPINET]DTAPINET.DtCaps::op_BitwiseAnd(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
				IL_0038: ldloc.3
				IL_0039: callvirt instance class [DTAPINET]DTAPINET.DtCaps Philips.PSG.Gateway.Config::get_DeviceCapabilityFlags()
				IL_003e: call bool [DTAPINET]DTAPINET.DtCaps::op_Equality(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
				IL_0043: brfalse.s IL_004f

				IL_0045: ldarg.1
				IL_0046: ldloc.0
				IL_0047: ldloc.2
				IL_0048: ldelem.ref
				IL_0049: stind.ref
				IL_004a: ldc.i4.1
				IL_004b: stloc.s 4
				IL_004d: leave.s IL_0061

				IL_004f: ldloc.2
				IL_0050: ldc.i4.1
				IL_0051: add
				IL_0052: stloc.2

				IL_0053: ldloc.2
				IL_0054: ldloc.1
				IL_0055: blt.s IL_0025
			// end loop

			IL_0057: leave.s IL_005f
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_0059: pop
			IL_005a: ldc.i4.0
			IL_005b: stloc.s 4
			IL_005d: leave.s IL_0061
		} // end handler

		IL_005f: ldc.i4.0
		IL_0060: ret

		IL_0061: ldloc.s 4
		IL_0063: ret
	} // end of method DekTecManager::ScanForSupportedDevice

	.method private hidebysig 
		instance bool AttachDevice (
			class [DTAPINET]DTAPINET.DtHwFuncDesc deviceFuncDesc
		) cil managed 
	{
		// Method begins at RVA 0x5760
		// Header size: 12
		// Code size: 105 (0x69)
		.maxstack 4
		.locals init (
			[0] bool
		)

		.try
		{
			IL_0000: ldarg.0
			IL_0001: ldfld class [DTAPINET]DTAPINET.DtDevice Philips.PSG.Gateway.DekTecManager::_device
			IL_0006: ldarg.1
			IL_0007: ldfld class [DTAPINET]DTAPINET.DtDeviceDesc [DTAPINET]DTAPINET.DtHwFuncDesc::m_DvcDesc
			IL_000c: ldfld int64 [DTAPINET]DTAPINET.DtDeviceDesc::m_Serial
			IL_0011: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtDevice::AttachToSerial(int64)
			IL_0016: brfalse.s IL_001c

			IL_0018: ldc.i4.0
			IL_0019: stloc.0
			IL_001a: leave.s IL_0067

			IL_001c: ldarg.1
			IL_001d: ldfld int32 [DTAPINET]DTAPINET.DtHwFuncDesc::m_ChanType
			IL_0022: ldc.i4.2
			IL_0023: and
			IL_0024: ldc.i4.2
			IL_0025: beq.s IL_0041

			IL_0027: ldarg.0
			IL_0028: ldfld class [DTAPINET]DTAPINET.DtDevice Philips.PSG.Gateway.DekTecManager::_device
			IL_002d: ldarg.1
			IL_002e: ldfld int32 [DTAPINET]DTAPINET.DtHwFuncDesc::m_Port
			IL_0033: ldc.i4.0
			IL_0034: ldc.i4.s 25
			IL_0036: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtDevice::SetIoConfig(int32, int32, int32)
			IL_003b: brfalse.s IL_0041

			IL_003d: ldc.i4.0
			IL_003e: stloc.0
			IL_003f: leave.s IL_0067

			IL_0041: ldarg.0
			IL_0042: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
			IL_0047: ldarg.0
			IL_0048: ldfld class [DTAPINET]DTAPINET.DtDevice Philips.PSG.Gateway.DekTecManager::_device
			IL_004d: ldarg.1
			IL_004e: ldfld int32 [DTAPINET]DTAPINET.DtHwFuncDesc::m_Port
			IL_0053: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::AttachToPort(class [DTAPINET]DTAPINET.DtDevice, int32)
			IL_0058: brfalse.s IL_005e

			IL_005a: ldc.i4.0
			IL_005b: stloc.0
			IL_005c: leave.s IL_0067

			IL_005e: leave.s IL_0065
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_0060: pop
			IL_0061: ldc.i4.0
			IL_0062: stloc.0
			IL_0063: leave.s IL_0067
		} // end handler

		IL_0065: ldc.i4.1
		IL_0066: ret

		IL_0067: ldloc.0
		IL_0068: ret
	} // end of method DekTecManager::AttachDevice

	.method public hidebysig specialname static 
		class Philips.PSG.Gateway.DekTecManager get_Instance () cil managed 
	{
		// Method begins at RVA 0x57e8
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldsfld class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager::_instance
		IL_0005: ret
	} // end of method DekTecManager::get_Instance

	.method public hidebysig specialname 
		instance string get_DeviceType () cil managed 
	{
		// Method begins at RVA 0x57ef
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.DekTecManager::_attachedDeviceType
		IL_0006: ret
	} // end of method DekTecManager::get_DeviceType

	.method public hidebysig 
		instance bool StartPlayback (
			class Philips.PSG.Gateway.ScheduleEvent playEvent
		) cil managed 
	{
		// Method begins at RVA 0x57f8
		// Header size: 12
		// Code size: 79 (0x4f)
		.maxstack 3

		IL_0000: ldarg.0
		IL_0001: ldfld bool Philips.PSG.Gateway.DekTecManager::_disposed
		IL_0006: brfalse.s IL_000a

		IL_0008: ldc.i4.0
		IL_0009: ret

		IL_000a: ldarg.0
		IL_000b: ldfld class [DTAPINET]DTAPINET.DtDevice Philips.PSG.Gateway.DekTecManager::_device
		IL_0010: callvirt instance bool [DTAPINET]DTAPINET.DtDevice::IsAttached()
		IL_0015: brtrue.s IL_0019

		IL_0017: ldc.i4.0
		IL_0018: ret

		IL_0019: ldarg.0
		IL_001a: ldfld class [mscorlib]System.Threading.Thread Philips.PSG.Gateway.DekTecManager::_playbackThread
		IL_001f: brfalse.s IL_0023

		IL_0021: ldc.i4.0
		IL_0022: ret

		IL_0023: ldarg.0
		IL_0024: ldarg.0
		IL_0025: ldftn instance void Philips.PSG.Gateway.DekTecManager::PBT_PlaybackHandler(object)
		IL_002b: newobj instance void [mscorlib]System.Threading.ParameterizedThreadStart::.ctor(object, native int)
		IL_0030: newobj instance void [mscorlib]System.Threading.Thread::.ctor(class [mscorlib]System.Threading.ParameterizedThreadStart)
		IL_0035: stfld class [mscorlib]System.Threading.Thread Philips.PSG.Gateway.DekTecManager::_playbackThread
		IL_003a: ldarg.0
		IL_003b: ldc.i4.0
		IL_003c: stfld bool Philips.PSG.Gateway.DekTecManager::_playbackThreadExit
		IL_0041: ldarg.0
		IL_0042: ldfld class [mscorlib]System.Threading.Thread Philips.PSG.Gateway.DekTecManager::_playbackThread
		IL_0047: ldarg.1
		IL_0048: callvirt instance void [mscorlib]System.Threading.Thread::Start(object)
		IL_004d: ldc.i4.1
		IL_004e: ret
	} // end of method DekTecManager::StartPlayback

	.method public hidebysig 
		instance bool StartPlayback (
			string tsFileName,
			class Philips.PSG.Gateway.PlayStreamHandlerErrored onErrorPlayStreamCallback
		) cil managed 
	{
		// Method begins at RVA 0x5854
		// Header size: 12
		// Code size: 93 (0x5d)
		.maxstack 3

		IL_0000: ldarg.0
		IL_0001: ldarg.2
		IL_0002: stfld class Philips.PSG.Gateway.PlayStreamHandlerErrored Philips.PSG.Gateway.DekTecManager::_onErrorPlayStreamCallback
		IL_0007: ldarg.0
		IL_0008: ldfld bool Philips.PSG.Gateway.DekTecManager::_disposed
		IL_000d: brfalse.s IL_0018

		IL_000f: ldarg.0
		IL_0010: ldnull
		IL_0011: stfld class Philips.PSG.Gateway.PlayStreamHandlerErrored Philips.PSG.Gateway.DekTecManager::_onErrorPlayStreamCallback
		IL_0016: ldc.i4.0
		IL_0017: ret

		IL_0018: ldarg.0
		IL_0019: ldfld class [DTAPINET]DTAPINET.DtDevice Philips.PSG.Gateway.DekTecManager::_device
		IL_001e: callvirt instance bool [DTAPINET]DTAPINET.DtDevice::IsAttached()
		IL_0023: brtrue.s IL_0027

		IL_0025: ldc.i4.0
		IL_0026: ret

		IL_0027: ldarg.0
		IL_0028: ldfld class [mscorlib]System.Threading.Thread Philips.PSG.Gateway.DekTecManager::_playbackThread
		IL_002d: brfalse.s IL_0031

		IL_002f: ldc.i4.0
		IL_0030: ret

		IL_0031: ldarg.0
		IL_0032: ldarg.0
		IL_0033: ldftn instance void Philips.PSG.Gateway.DekTecManager::PBT_PlaybackHandler(object)
		IL_0039: newobj instance void [mscorlib]System.Threading.ParameterizedThreadStart::.ctor(object, native int)
		IL_003e: newobj instance void [mscorlib]System.Threading.Thread::.ctor(class [mscorlib]System.Threading.ParameterizedThreadStart)
		IL_0043: stfld class [mscorlib]System.Threading.Thread Philips.PSG.Gateway.DekTecManager::_playbackThread
		IL_0048: ldarg.0
		IL_0049: ldc.i4.0
		IL_004a: stfld bool Philips.PSG.Gateway.DekTecManager::_playbackThreadExit
		IL_004f: ldarg.0
		IL_0050: ldfld class [mscorlib]System.Threading.Thread Philips.PSG.Gateway.DekTecManager::_playbackThread
		IL_0055: ldarg.1
		IL_0056: callvirt instance void [mscorlib]System.Threading.Thread::Start(object)
		IL_005b: ldc.i4.1
		IL_005c: ret
	} // end of method DekTecManager::StartPlayback

	.method public hidebysig 
		instance void StopPlayback () cil managed 
	{
		// Method begins at RVA 0x58bd
		// Header size: 1
		// Code size: 57 (0x39)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld bool Philips.PSG.Gateway.DekTecManager::_disposed
		IL_0006: brfalse.s IL_0009

		IL_0008: ret

		IL_0009: ldarg.0
		IL_000a: ldfld class [mscorlib]System.Threading.Thread Philips.PSG.Gateway.DekTecManager::_playbackThread
		IL_000f: brfalse.s IL_0038

		IL_0011: ldarg.0
		IL_0012: ldc.i4.1
		IL_0013: stfld bool Philips.PSG.Gateway.DekTecManager::_playbackThreadExit
		IL_0018: ldarg.0
		IL_0019: ldfld class [mscorlib]System.Threading.Thread Philips.PSG.Gateway.DekTecManager::_playbackThread
		IL_001e: ldc.i4 20000
		IL_0023: callvirt instance bool [mscorlib]System.Threading.Thread::Join(int32)
		IL_0028: brfalse.s IL_0031

		IL_002a: ldarg.0
		IL_002b: ldnull
		IL_002c: stfld class [mscorlib]System.Threading.Thread Philips.PSG.Gateway.DekTecManager::_playbackThread

		IL_0031: ldarg.0
		IL_0032: ldnull
		IL_0033: stfld class Philips.PSG.Gateway.PlayStreamHandlerErrored Philips.PSG.Gateway.DekTecManager::_onErrorPlayStreamCallback

		IL_0038: ret
	} // end of method DekTecManager::StopPlayback

	.method public hidebysig 
		instance void Dispose () cil managed 
	{
		// Method begins at RVA 0x58f8
		// Header size: 12
		// Code size: 83 (0x53)
		.maxstack 2

		IL_0000: ldarg.0
		IL_0001: ldfld bool Philips.PSG.Gateway.DekTecManager::_disposed
		IL_0006: brtrue.s IL_0052

		IL_0008: ldarg.0
		IL_0009: call instance void Philips.PSG.Gateway.DekTecManager::StopPlayback()
		IL_000e: ldarg.0
		IL_000f: ldc.i4.1
		IL_0010: stfld bool Philips.PSG.Gateway.DekTecManager::_disposed
		.try
		{
			IL_0015: ldarg.0
			IL_0016: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
			IL_001b: ldc.i4.1
			IL_001c: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::SetTxControl(int32)
			IL_0021: pop
			IL_0022: ldarg.0
			IL_0023: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
			IL_0028: ldc.i4.1
			IL_0029: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::Detach(int32)
			IL_002e: pop
			IL_002f: ldarg.0
			IL_0030: ldfld class [DTAPINET]DTAPINET.DtDevice Philips.PSG.Gateway.DekTecManager::_device
			IL_0035: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtDevice::Detach()
			IL_003a: pop
			IL_003b: leave.s IL_0040
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_003d: pop
			IL_003e: leave.s IL_0040
		} // end handler

		IL_0040: ldarg.0
		IL_0041: ldsfld string [mscorlib]System.String::Empty
		IL_0046: stfld string Philips.PSG.Gateway.DekTecManager::_attachedDeviceType
		IL_004b: ldarg.0
		IL_004c: ldnull
		IL_004d: stfld class Philips.PSG.Gateway.PlayStreamHandlerErrored Philips.PSG.Gateway.DekTecManager::_onErrorPlayStreamCallback

		IL_0052: ret
	} // end of method DekTecManager::Dispose

	.method private hidebysig 
		instance void PBT_InitOutput () cil managed 
	{
		// Method begins at RVA 0x5968
		// Header size: 12
		// Code size: 462 (0x1ce)
		.maxstack 5
		.locals init (
			[0] valuetype [DTAPINET]DTAPINET.DTAPI_RESULT,
			[1] int64,
			[2] int32,
			[3] class [DTAPINET]DTAPINET.DtHwFuncDesc,
			[4] int32,
			[5] class Philips.PSG.Gateway.Config
		)

		IL_0000: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0005: stloc.s 5
		IL_0007: ldarg.0
		IL_0008: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_000d: ldc.i4.1
		IL_000e: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::Reset(int32)
		IL_0013: stloc.0
		IL_0014: ldloc.0
		IL_0015: brfalse.s IL_002d

		IL_0017: ldstr "outputChannel.Reset() failed with {0}"
		IL_001c: ldloc.0
		IL_001d: box [DTAPINET]DTAPINET.DTAPI_RESULT
		IL_0022: call string [mscorlib]System.String::Format(string, object)
		IL_0027: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
		IL_002c: throw

		IL_002d: ldarg.0
		IL_002e: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_0033: ldc.i4.1
		IL_0034: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::SetTxControl(int32)
		IL_0039: stloc.0
		IL_003a: ldloc.0
		IL_003b: brfalse.s IL_0053

		IL_003d: ldstr "outputChannel.SetTxControl() failed with {0}"
		IL_0042: ldloc.0
		IL_0043: box [DTAPINET]DTAPINET.DTAPI_RESULT
		IL_0048: call string [mscorlib]System.String::Format(string, object)
		IL_004d: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
		IL_0052: throw

		IL_0053: ldarg.0
		IL_0054: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_0059: ldloc.s 5
		IL_005b: callvirt instance int32 Philips.PSG.Gateway.Config::get_TxMode()
		IL_0060: ldloc.s 5
		IL_0062: callvirt instance int32 Philips.PSG.Gateway.Config::get_StuffMode()
		IL_0067: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::SetTxMode(int32, int32)
		IL_006c: stloc.0
		IL_006d: ldloc.0
		IL_006e: brfalse.s IL_0086

		IL_0070: ldstr "outputChannel.SetTxMode() failed with {0}"
		IL_0075: ldloc.0
		IL_0076: box [DTAPINET]DTAPINET.DTAPI_RESULT
		IL_007b: call string [mscorlib]System.String::Format(string, object)
		IL_0080: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
		IL_0085: throw

		IL_0086: ldloc.s 5
		IL_0088: callvirt instance int32 Philips.PSG.Gateway.Config::get_ModulationFrequency()
		IL_008d: conv.i8
		IL_008e: ldc.i4 100000
		IL_0093: conv.i8
		IL_0094: mul
		IL_0095: stloc.1
		IL_0096: ldarg.0
		IL_0097: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_009c: ldloc.1
		IL_009d: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::SetRfControl(int64)
		IL_00a2: stloc.0
		IL_00a3: ldloc.0
		IL_00a4: brfalse.s IL_00bc

		IL_00a6: ldstr "outputChannel.SetRfControl() failed with {0}"
		IL_00ab: ldloc.0
		IL_00ac: box [DTAPINET]DTAPINET.DTAPI_RESULT
		IL_00b1: call string [mscorlib]System.String::Format(string, object)
		IL_00b6: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
		IL_00bb: throw

		IL_00bc: ldloc.s 5
		IL_00be: callvirt instance int32 Philips.PSG.Gateway.Config::get_Bandwidth()
		IL_00c3: ldloc.s 5
		IL_00c5: callvirt instance int32 Philips.PSG.Gateway.Config::get_Constellation()
		IL_00ca: or
		IL_00cb: ldloc.s 5
		IL_00cd: callvirt instance int32 Philips.PSG.Gateway.Config::get_TransmissionMode()
		IL_00d2: or
		IL_00d3: ldloc.s 5
		IL_00d5: callvirt instance int32 Philips.PSG.Gateway.Config::get_GuardInterval()
		IL_00da: or
		IL_00db: ldc.i4 8192
		IL_00e0: or
		IL_00e1: stloc.2
		IL_00e2: ldarg.0
		IL_00e3: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_00e8: ldloc.s 5
		IL_00ea: callvirt instance int32 Philips.PSG.Gateway.Config::get_ModulationType()
		IL_00ef: ldloc.s 5
		IL_00f1: callvirt instance int32 Philips.PSG.Gateway.Config::get_CodeRate()
		IL_00f6: ldloc.2
		IL_00f7: ldc.i4.0
		IL_00f8: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::SetModControl(int32, int32, int32, int32)
		IL_00fd: stloc.0
		IL_00fe: ldloc.0
		IL_00ff: brfalse.s IL_0117

		IL_0101: ldstr "outputChannel.SetModControl() failed with {0}"
		IL_0106: ldloc.0
		IL_0107: box [DTAPINET]DTAPINET.DTAPI_RESULT
		IL_010c: call string [mscorlib]System.String::Format(string, object)
		IL_0111: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
		IL_0116: throw

		IL_0117: newobj instance void [DTAPINET]DTAPINET.DtHwFuncDesc::.ctor()
		IL_011c: stloc.3
		IL_011d: ldarg.0
		IL_011e: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_0123: ldloca.s 3
		IL_0125: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::GetDescriptor(class [DTAPINET]DTAPINET.DtHwFuncDesc&)
		IL_012a: brtrue.s IL_0183

		IL_012c: ldloc.3
		IL_012d: ldfld class [DTAPINET]DTAPINET.DtCaps [DTAPINET]DTAPINET.DtHwFuncDesc::m_Flags
		IL_0132: ldsfld class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst) [DTAPINET]DTAPINET.DTAPI::CAP_ADJLVL
		IL_0137: call class [DTAPINET]DTAPINET.DtCaps [DTAPINET]DTAPINET.DtCaps::op_BitwiseAnd(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
		IL_013c: ldsfld class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst) [DTAPINET]DTAPINET.DTAPI::CAP_ADJLVL
		IL_0141: call bool [DTAPINET]DTAPINET.DtCaps::op_Equality(class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst), class [DTAPINET]DTAPINET.DtCaps modopt([mscorlib]System.Runtime.CompilerServices.IsConst))
		IL_0146: brfalse.s IL_0183

		IL_0148: ldloc.s 5
		IL_014a: callvirt instance float64 Philips.PSG.Gateway.Config::get_OutputLevel()
		IL_014f: ldc.r8 10
		IL_0158: mul
		IL_0159: conv.i4
		IL_015a: stloc.s 4
		IL_015c: ldarg.0
		IL_015d: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_0162: ldloc.s 4
		IL_0164: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::SetOutputLevel(int32)
		IL_0169: stloc.0
		IL_016a: ldloc.0
		IL_016b: brfalse.s IL_0183

		IL_016d: ldstr "outputChannel.SetOutputLevel() failed with {0}"
		IL_0172: ldloc.0
		IL_0173: box [DTAPINET]DTAPINET.DTAPI_RESULT
		IL_0178: call string [mscorlib]System.String::Format(string, object)
		IL_017d: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
		IL_0182: throw

		IL_0183: ldarg.0
		IL_0184: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_0189: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::SetFifoSizeMax()
		IL_018e: stloc.0
		IL_018f: ldloc.0
		IL_0190: brfalse.s IL_01a8

		IL_0192: ldstr "outputChannel.SetFifoSizeMax() failed with {0}"
		IL_0197: ldloc.0
		IL_0198: box [DTAPINET]DTAPINET.DTAPI_RESULT
		IL_019d: call string [mscorlib]System.String::Format(string, object)
		IL_01a2: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
		IL_01a7: throw

		IL_01a8: ldarg.0
		IL_01a9: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_01ae: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::ClearFifo()
		IL_01b3: stloc.0
		IL_01b4: ldloc.0
		IL_01b5: brfalse.s IL_01cd

		IL_01b7: ldstr "outputChannel.ClearFifo() failed with {0}"
		IL_01bc: ldloc.0
		IL_01bd: box [DTAPINET]DTAPINET.DTAPI_RESULT
		IL_01c2: call string [mscorlib]System.String::Format(string, object)
		IL_01c7: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
		IL_01cc: throw

		IL_01cd: ret
	} // end of method DekTecManager::PBT_InitOutput

	.method private hidebysig 
		instance void PBT_LoopFile (
			string tsFileName
		) cil managed 
	{
		// Method begins at RVA 0x5b44
		// Header size: 12
		// Code size: 432 (0x1b0)
		.maxstack 4
		.locals init (
			[0] valuetype [DTAPINET]DTAPINET.DTAPI_RESULT,
			[1] class [mscorlib]System.IO.FileStream,
			[2] int32,
			[3] int32,
			[4] int32,
			[5] bool,
			[6] bool
		)

		IL_0000: ldnull
		IL_0001: stloc.1
		IL_0002: ldc.i4.0
		IL_0003: stloc.2
		IL_0004: ldc.i4.0
		IL_0005: stloc.3
		IL_0006: ldc.i4.0
		IL_0007: stloc.s 5
		IL_0009: ldc.i4.0
		IL_000a: stloc.s 6
		IL_000c: ldarg.0
		IL_000d: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_0012: ldc.i4.2
		IL_0013: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::SetTxControl(int32)
		IL_0018: stloc.0
		IL_0019: ldloc.0
		IL_001a: brfalse.s IL_0032

		IL_001c: ldstr "outputChannel.SetTxControl() failed with {0}"
		IL_0021: ldloc.0
		IL_0022: box [DTAPINET]DTAPINET.DTAPI_RESULT
		IL_0027: call string [mscorlib]System.String::Format(string, object)
		IL_002c: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
		IL_0031: throw

		IL_0032: ldarg.0
		IL_0033: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
		IL_0038: ldloca.s 2
		IL_003a: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::GetFifoSize(int32&)
		IL_003f: stloc.0
		IL_0040: ldloc.0
		IL_0041: brfalse.s IL_0059

		IL_0043: ldstr "outputChannel.GetFifoSize() failed with {0}"
		IL_0048: ldloc.0
		IL_0049: box [DTAPINET]DTAPINET.DTAPI_RESULT
		IL_004e: call string [mscorlib]System.String::Format(string, object)
		IL_0053: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
		IL_0058: throw

		IL_0059: nop
		.try
		{
			IL_005a: ldarg.1
			IL_005b: call class [mscorlib]System.IO.FileStream [mscorlib]System.IO.File::OpenRead(string)
			IL_0060: stloc.1
			IL_0061: br IL_016b
			// loop start (head: IL_016b)
				IL_0066: ldarg.0
				IL_0067: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
				IL_006c: ldloca.s 3
				IL_006e: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::GetFifoLoad(int32&)
				IL_0073: stloc.0
				IL_0074: ldloc.0
				IL_0075: brfalse.s IL_008d

				IL_0077: ldstr "outputChannel.GetFifoLoad() failed with {0}"
				IL_007c: ldloc.0
				IL_007d: box [DTAPINET]DTAPINET.DTAPI_RESULT
				IL_0082: call string [mscorlib]System.String::Format(string, object)
				IL_0087: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
				IL_008c: throw

				IL_008d: ldloc.3
				IL_008e: ldc.i4 1048576
				IL_0093: add
				IL_0094: ldloc.2
				IL_0095: blt.s IL_00a6

				IL_0097: ldc.i4 500
				IL_009c: call void [mscorlib]System.Threading.Thread::Sleep(int32)
				IL_00a1: br IL_016b

				IL_00a6: ldloc.1
				IL_00a7: ldarg.0
				IL_00a8: ldfld uint8[] Philips.PSG.Gateway.DekTecManager::_transferBuffer
				IL_00ad: ldc.i4.0
				IL_00ae: ldc.i4 1048576
				IL_00b3: callvirt instance int32 [mscorlib]System.IO.Stream::Read(uint8[], int32, int32)
				IL_00b8: stloc.s 4
				IL_00ba: ldloc.s 4
				IL_00bc: brtrue.s IL_00c8

				IL_00be: ldc.i4.1
				IL_00bf: stloc.s 5
				IL_00c1: ldloc.s 4
				IL_00c3: ldc.i4.s -4
				IL_00c5: and
				IL_00c6: stloc.s 4

				IL_00c8: ldloc.s 4
				IL_00ca: ldc.i4.0
				IL_00cb: ble.s IL_00fa

				IL_00cd: ldarg.0
				IL_00ce: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
				IL_00d3: ldarg.0
				IL_00d4: ldfld uint8[] Philips.PSG.Gateway.DekTecManager::_transferBuffer
				IL_00d9: ldloc.s 4
				IL_00db: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::Write(uint8[], int32)
				IL_00e0: stloc.0
				IL_00e1: ldloc.0
				IL_00e2: brfalse.s IL_00fa

				IL_00e4: ldstr "outputChannel.Write() failed with {0}"
				IL_00e9: ldloc.0
				IL_00ea: box [DTAPINET]DTAPINET.DTAPI_RESULT
				IL_00ef: call string [mscorlib]System.String::Format(string, object)
				IL_00f4: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
				IL_00f9: throw

				IL_00fa: ldarg.0
				IL_00fb: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
				IL_0100: ldloca.s 3
				IL_0102: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::GetFifoLoad(int32&)
				IL_0107: stloc.0
				IL_0108: ldloc.0
				IL_0109: brfalse.s IL_0121

				IL_010b: ldstr "outputChannel.GetFifoLoad() failed with {0}"
				IL_0110: ldloc.0
				IL_0111: box [DTAPINET]DTAPINET.DTAPI_RESULT
				IL_0116: call string [mscorlib]System.String::Format(string, object)
				IL_011b: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
				IL_0120: throw

				IL_0121: ldloc.s 6
				IL_0123: brtrue.s IL_015a

				IL_0125: ldloc.s 5
				IL_0127: brtrue.s IL_0131

				IL_0129: ldloc.3
				IL_012a: ldc.i4 3145728
				IL_012f: blt.s IL_015a

				IL_0131: ldarg.0
				IL_0132: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
				IL_0137: ldc.i4.3
				IL_0138: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::SetTxControl(int32)
				IL_013d: stloc.0
				IL_013e: ldloc.0
				IL_013f: brfalse.s IL_0157

				IL_0141: ldstr "outputChannel.SetTxControl() failed with {0}"
				IL_0146: ldloc.0
				IL_0147: box [DTAPINET]DTAPINET.DTAPI_RESULT
				IL_014c: call string [mscorlib]System.String::Format(string, object)
				IL_0151: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
				IL_0156: throw

				IL_0157: ldc.i4.1
				IL_0158: stloc.s 6

				IL_015a: ldloc.s 5
				IL_015c: brfalse.s IL_016b

				IL_015e: ldloc.1
				IL_015f: ldc.i4.0
				IL_0160: conv.i8
				IL_0161: ldc.i4.0
				IL_0162: callvirt instance int64 [mscorlib]System.IO.Stream::Seek(int64, valuetype [mscorlib]System.IO.SeekOrigin)
				IL_0167: pop
				IL_0168: ldc.i4.0
				IL_0169: stloc.s 5

				IL_016b: ldarg.0
				IL_016c: ldfld bool Philips.PSG.Gateway.DekTecManager::_playbackThreadExit
				IL_0171: brfalse IL_0066
			// end loop

			IL_0176: ldarg.0
			IL_0177: ldfld class [DTAPINET]DTAPINET.DtOutpChannel Philips.PSG.Gateway.DekTecManager::_outputChannel
			IL_017c: callvirt instance valuetype [DTAPINET]DTAPINET.DTAPI_RESULT [DTAPINET]DTAPINET.DtOutpChannel::ClearFifo()
			IL_0181: stloc.0
			IL_0182: ldloc.0
			IL_0183: brfalse.s IL_019b

			IL_0185: ldstr "outputChannel.ClearFifo() failed with {0}"
			IL_018a: ldloc.0
			IL_018b: box [DTAPINET]DTAPINET.DTAPI_RESULT
			IL_0190: call string [mscorlib]System.String::Format(string, object)
			IL_0195: newobj instance void [mscorlib]System.InvalidOperationException::.ctor(string)
			IL_019a: throw

			IL_019b: leave.s IL_01a9
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_019d: pop
			IL_019e: ldloc.1
			IL_019f: brfalse.s IL_01a7

			IL_01a1: ldloc.1
			IL_01a2: callvirt instance void [mscorlib]System.IO.Stream::Close()

			IL_01a7: rethrow
		} // end handler

		IL_01a9: ldloc.1
		IL_01aa: callvirt instance void [mscorlib]System.IO.Stream::Close()
		IL_01af: ret
	} // end of method DekTecManager::PBT_LoopFile

	.method private hidebysig 
		instance void PBT_PlaybackHandler (
			object tsFile
		) cil managed 
	{
		// Method begins at RVA 0x5d1c
		// Header size: 12
		// Code size: 85 (0x55)
		.maxstack 4
		.locals init (
			[0] class Philips.PSG.Gateway.DekTecManager/'<>c__DisplayClass29_0',
			[1] class [mscorlib]System.Exception
		)

		IL_0000: newobj instance void Philips.PSG.Gateway.DekTecManager/'<>c__DisplayClass29_0'::.ctor()
		IL_0005: stloc.0
		IL_0006: ldloc.0
		IL_0007: ldarg.0
		IL_0008: stfld class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager/'<>c__DisplayClass29_0'::'<>4__this'
		.try
		{
			IL_000d: ldarg.0
			IL_000e: call instance void Philips.PSG.Gateway.DekTecManager::PBT_InitOutput()
			IL_0013: ldarg.0
			IL_0014: ldarg.1
			IL_0015: callvirt instance string [mscorlib]System.Object::ToString()
			IL_001a: call instance void Philips.PSG.Gateway.DekTecManager::PBT_LoopFile(string)
			IL_001f: leave.s IL_0054
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_0021: stloc.1
			IL_0022: ldloc.0
			IL_0023: ldloc.1
			IL_0024: stfld class [mscorlib]System.Exception Philips.PSG.Gateway.DekTecManager/'<>c__DisplayClass29_0'::exception
			IL_0029: ldarg.0
			IL_002a: ldfld class Philips.PSG.Gateway.PlayStreamHandlerErrored Philips.PSG.Gateway.DekTecManager::_onErrorPlayStreamCallback
			IL_002f: brfalse.s IL_0052

			IL_0031: ldarg.0
			IL_0032: ldfld class [WindowsBase]System.Windows.Threading.Dispatcher Philips.PSG.Gateway.DekTecManager::uiThread
			IL_0037: brfalse.s IL_0052

			IL_0039: ldarg.0
			IL_003a: ldfld class [WindowsBase]System.Windows.Threading.Dispatcher Philips.PSG.Gateway.DekTecManager::uiThread
			IL_003f: ldc.i4.4
			IL_0040: ldloc.0
			IL_0041: ldftn instance void Philips.PSG.Gateway.DekTecManager/'<>c__DisplayClass29_0'::'<PBT_PlaybackHandler>b__0'()
			IL_0047: newobj instance void Philips.PSG.Gateway.DekTecManager/DelegateUiThread::.ctor(object, native int)
			IL_004c: callvirt instance class [WindowsBase]System.Windows.Threading.DispatcherOperation [WindowsBase]System.Windows.Threading.Dispatcher::BeginInvoke(valuetype [WindowsBase]System.Windows.Threading.DispatcherPriority, class [mscorlib]System.Delegate)
			IL_0051: pop

			IL_0052: leave.s IL_0054
		} // end handler

		IL_0054: ret
	} // end of method DekTecManager::PBT_PlaybackHandler

	.method private hidebysig specialname rtspecialname static 
		void .cctor () cil managed 
	{
		// Method begins at RVA 0x5d90
		// Header size: 1
		// Code size: 11 (0xb)
		.maxstack 8

		IL_0000: newobj instance void Philips.PSG.Gateway.DekTecManager::.ctor()
		IL_0005: stsfld class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager::_instance
		IL_000a: ret
	} // end of method DekTecManager::.cctor

	// Properties
	.property class Philips.PSG.Gateway.DekTecManager Instance()
	{
		.get class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager::get_Instance()
	}
	.property instance string DeviceType()
	{
		.get instance string Philips.PSG.Gateway.DekTecManager::get_DeviceType()
	}

} // end of class Philips.PSG.Gateway.DekTecManager

.class private auto ansi beforefieldinit Philips.PSG.Gateway.AboutBox
	extends [System.Windows.Forms]System.Windows.Forms.Form
{
	// Fields
	.field private class [System]System.ComponentModel.IContainer components
	.field private class [System.Windows.Forms]System.Windows.Forms.Button okButton
	.field private class [System.Windows.Forms]System.Windows.Forms.PictureBox logoPictureBox
	.field private class [System.Windows.Forms]System.Windows.Forms.Label labelProductName
	.field private class [System.Windows.Forms]System.Windows.Forms.Label labelVersion
	.field private class [System.Windows.Forms]System.Windows.Forms.Label labelCopyright
	.field private class [System.Windows.Forms]System.Windows.Forms.Label labelCompanyName

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x5d9c
		// Header size: 12
		// Code size: 154 (0x9a)
		.maxstack 4

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Form::.ctor()
		IL_0006: ldarg.0
		IL_0007: call instance void Philips.PSG.Gateway.AboutBox::InitializeComponent()
		IL_000c: ldarg.0
		IL_000d: ldstr "{0} {1}"
		IL_0012: call string Philips.PSG.Gateway.Resources::get_DLG_AboutTitle()
		IL_0017: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
		IL_001c: call string [mscorlib]System.String::Format(string, object, object)
		IL_0021: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0026: ldarg.0
		IL_0027: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_002c: ldarg.0
		IL_002d: call instance string Philips.PSG.Gateway.AboutBox::get_AssemblyProduct()
		IL_0032: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0037: ldarg.0
		IL_0038: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_003d: ldstr "{0} {1}"
		IL_0042: call string Philips.PSG.Gateway.Resources::get_DLG_AboutVersion()
		IL_0047: ldarg.0
		IL_0048: call instance string Philips.PSG.Gateway.AboutBox::get_AssemblyVersion()
		IL_004d: call string [mscorlib]System.String::Format(string, object, object)
		IL_0052: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0057: ldarg.0
		IL_0058: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_005d: ldarg.0
		IL_005e: call instance string Philips.PSG.Gateway.AboutBox::get_AssemblyCopyright()
		IL_0063: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0068: ldarg.0
		IL_0069: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_006e: ldarg.0
		IL_006f: call instance string Philips.PSG.Gateway.AboutBox::get_AssemblyCompany()
		IL_0074: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0079: ldarg.0
		IL_007a: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_007f: call class [System.Drawing]System.Drawing.Bitmap Philips.PSG.Gateway.Resources::get_Logo()
		IL_0084: callvirt instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::set_Image(class [System.Drawing]System.Drawing.Image)
		IL_0089: ldarg.0
		IL_008a: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_008f: call string Philips.PSG.Gateway.Resources::get_BTN_OK()
		IL_0094: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0099: ret
	} // end of method AboutBox::.ctor

	.method public hidebysig specialname 
		instance string get_AssemblyVersion () cil managed 
	{
		// Method begins at RVA 0x5e42
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Reflection.Assembly [mscorlib]System.Reflection.Assembly::GetExecutingAssembly()
		IL_0005: callvirt instance class [mscorlib]System.Reflection.AssemblyName [mscorlib]System.Reflection.Assembly::GetName()
		IL_000a: callvirt instance class [mscorlib]System.Version [mscorlib]System.Reflection.AssemblyName::get_Version()
		IL_000f: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0014: ret
	} // end of method AboutBox::get_AssemblyVersion

	.method public hidebysig specialname 
		instance string get_AssemblyProduct () cil managed 
	{
		// Method begins at RVA 0x5e58
		// Header size: 12
		// Code size: 46 (0x2e)
		.maxstack 3
		.locals init (
			[0] object[]
		)

		IL_0000: call class [mscorlib]System.Reflection.Assembly [mscorlib]System.Reflection.Assembly::GetExecutingAssembly()
		IL_0005: ldtoken [mscorlib]System.Reflection.AssemblyProductAttribute
		IL_000a: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_000f: ldc.i4.0
		IL_0010: callvirt instance object[] [mscorlib]System.Reflection.Assembly::GetCustomAttributes(class [mscorlib]System.Type, bool)
		IL_0015: stloc.0
		IL_0016: ldloc.0
		IL_0017: ldlen
		IL_0018: brtrue.s IL_0020

		IL_001a: ldsfld string [mscorlib]System.String::Empty
		IL_001f: ret

		IL_0020: ldloc.0
		IL_0021: ldc.i4.0
		IL_0022: ldelem.ref
		IL_0023: castclass [mscorlib]System.Reflection.AssemblyProductAttribute
		IL_0028: callvirt instance string [mscorlib]System.Reflection.AssemblyProductAttribute::get_Product()
		IL_002d: ret
	} // end of method AboutBox::get_AssemblyProduct

	.method public hidebysig specialname 
		instance string get_AssemblyCopyright () cil managed 
	{
		// Method begins at RVA 0x5e94
		// Header size: 12
		// Code size: 46 (0x2e)
		.maxstack 3
		.locals init (
			[0] object[]
		)

		IL_0000: call class [mscorlib]System.Reflection.Assembly [mscorlib]System.Reflection.Assembly::GetExecutingAssembly()
		IL_0005: ldtoken [mscorlib]System.Reflection.AssemblyCopyrightAttribute
		IL_000a: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_000f: ldc.i4.0
		IL_0010: callvirt instance object[] [mscorlib]System.Reflection.Assembly::GetCustomAttributes(class [mscorlib]System.Type, bool)
		IL_0015: stloc.0
		IL_0016: ldloc.0
		IL_0017: ldlen
		IL_0018: brtrue.s IL_0020

		IL_001a: ldsfld string [mscorlib]System.String::Empty
		IL_001f: ret

		IL_0020: ldloc.0
		IL_0021: ldc.i4.0
		IL_0022: ldelem.ref
		IL_0023: castclass [mscorlib]System.Reflection.AssemblyCopyrightAttribute
		IL_0028: callvirt instance string [mscorlib]System.Reflection.AssemblyCopyrightAttribute::get_Copyright()
		IL_002d: ret
	} // end of method AboutBox::get_AssemblyCopyright

	.method public hidebysig specialname 
		instance string get_AssemblyCompany () cil managed 
	{
		// Method begins at RVA 0x5ed0
		// Header size: 12
		// Code size: 46 (0x2e)
		.maxstack 3
		.locals init (
			[0] object[]
		)

		IL_0000: call class [mscorlib]System.Reflection.Assembly [mscorlib]System.Reflection.Assembly::GetExecutingAssembly()
		IL_0005: ldtoken [mscorlib]System.Reflection.AssemblyCompanyAttribute
		IL_000a: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_000f: ldc.i4.0
		IL_0010: callvirt instance object[] [mscorlib]System.Reflection.Assembly::GetCustomAttributes(class [mscorlib]System.Type, bool)
		IL_0015: stloc.0
		IL_0016: ldloc.0
		IL_0017: ldlen
		IL_0018: brtrue.s IL_0020

		IL_001a: ldsfld string [mscorlib]System.String::Empty
		IL_001f: ret

		IL_0020: ldloc.0
		IL_0021: ldc.i4.0
		IL_0022: ldelem.ref
		IL_0023: castclass [mscorlib]System.Reflection.AssemblyCompanyAttribute
		IL_0028: callvirt instance string [mscorlib]System.Reflection.AssemblyCompanyAttribute::get_Company()
		IL_002d: ret
	} // end of method AboutBox::get_AssemblyCompany

	.method private hidebysig 
		instance void okButton_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x3a83
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Form::Close()
		IL_0006: ret
	} // end of method AboutBox::okButton_Click

	.method family hidebysig virtual 
		instance void Dispose (
			bool disposing
		) cil managed 
	{
		// Method begins at RVA 0x5f0a
		// Header size: 1
		// Code size: 30 (0x1e)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brfalse.s IL_0016

		IL_0003: ldarg.0
		IL_0004: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.AboutBox::components
		IL_0009: brfalse.s IL_0016

		IL_000b: ldarg.0
		IL_000c: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.AboutBox::components
		IL_0011: callvirt instance void [mscorlib]System.IDisposable::Dispose()

		IL_0016: ldarg.0
		IL_0017: ldarg.1
		IL_0018: call instance void [System.Windows.Forms]System.Windows.Forms.Form::Dispose(bool)
		IL_001d: ret
	} // end of method AboutBox::Dispose

	.method private hidebysig 
		instance void InitializeComponent () cil managed 
	{
		// Method begins at RVA 0x5f2c
		// Header size: 12
		// Code size: 1155 (0x483)
		.maxstack 5

		IL_0000: ldarg.0
		IL_0001: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_0006: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_000b: ldarg.0
		IL_000c: newobj instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::.ctor()
		IL_0011: stfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_0016: ldarg.0
		IL_0017: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_001c: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_0021: ldarg.0
		IL_0022: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0027: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_002c: ldarg.0
		IL_002d: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0032: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_0037: ldarg.0
		IL_0038: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_003d: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_0042: ldarg.0
		IL_0043: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_0048: callvirt instance void [System]System.ComponentModel.ISupportInitialize::BeginInit()
		IL_004d: ldarg.0
		IL_004e: call instance void [System.Windows.Forms]System.Windows.Forms.Control::SuspendLayout()
		IL_0053: ldarg.0
		IL_0054: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_0059: ldc.i4.s 10
		IL_005b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Anchor(valuetype [System.Windows.Forms]System.Windows.Forms.AnchorStyles)
		IL_0060: ldarg.0
		IL_0061: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_0066: ldc.i4.2
		IL_0067: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Button::set_DialogResult(valuetype [System.Windows.Forms]System.Windows.Forms.DialogResult)
		IL_006c: ldarg.0
		IL_006d: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_0072: ldc.i4 287
		IL_0077: ldc.i4 320
		IL_007c: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0081: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0086: ldarg.0
		IL_0087: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_008c: ldstr "okButton"
		IL_0091: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0096: ldarg.0
		IL_0097: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_009c: ldc.i4.s 75
		IL_009e: ldc.i4.s 23
		IL_00a0: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_00a5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_00aa: ldarg.0
		IL_00ab: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_00b0: ldc.i4.s 24
		IL_00b2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_00b7: ldarg.0
		IL_00b8: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_00bd: ldstr "&OK"
		IL_00c2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_00c7: ldarg.0
		IL_00c8: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_00cd: ldarg.0
		IL_00ce: ldftn instance void Philips.PSG.Gateway.AboutBox::okButton_Click(object, class [mscorlib]System.EventArgs)
		IL_00d4: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_00d9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::add_Click(class [mscorlib]System.EventHandler)
		IL_00de: ldarg.0
		IL_00df: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_00e4: ldc.i4.1
		IL_00e5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::set_BorderStyle(valuetype [System.Windows.Forms]System.Windows.Forms.BorderStyle)
		IL_00ea: ldarg.0
		IL_00eb: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_00f0: ldc.i4.s 12
		IL_00f2: ldc.i4.s 12
		IL_00f4: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_00f9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_00fe: ldarg.0
		IL_00ff: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_0104: ldstr "logoPictureBox"
		IL_0109: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_010e: ldarg.0
		IL_010f: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_0114: ldc.i4 350
		IL_0119: ldc.i4 170
		IL_011e: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0123: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0128: ldarg.0
		IL_0129: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_012e: ldc.i4.3
		IL_012f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::set_SizeMode(valuetype [System.Windows.Forms]System.Windows.Forms.PictureBoxSizeMode)
		IL_0134: ldarg.0
		IL_0135: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_013a: ldc.i4.s 25
		IL_013c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::set_TabIndex(int32)
		IL_0141: ldarg.0
		IL_0142: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_0147: ldc.i4.0
		IL_0148: callvirt instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::set_TabStop(bool)
		IL_014d: ldarg.0
		IL_014e: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_0153: ldc.i4.s 52
		IL_0155: ldc.i4 201
		IL_015a: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_015f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0164: ldarg.0
		IL_0165: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_016a: ldc.i4.6
		IL_016b: ldc.i4.0
		IL_016c: ldc.i4.3
		IL_016d: ldc.i4.0
		IL_016e: newobj instance void [System.Windows.Forms]System.Windows.Forms.Padding::.ctor(int32, int32, int32, int32)
		IL_0173: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Margin(valuetype [System.Windows.Forms]System.Windows.Forms.Padding)
		IL_0178: ldarg.0
		IL_0179: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_017e: ldc.i4.0
		IL_017f: ldc.i4.s 17
		IL_0181: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0186: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_MaximumSize(valuetype [System.Drawing]System.Drawing.Size)
		IL_018b: ldarg.0
		IL_018c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_0191: ldstr "labelProductName"
		IL_0196: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_019b: ldarg.0
		IL_019c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_01a1: ldc.i4 271
		IL_01a6: ldc.i4.s 17
		IL_01a8: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_01ad: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_01b2: ldarg.0
		IL_01b3: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_01b8: ldc.i4.s 27
		IL_01ba: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_01bf: ldarg.0
		IL_01c0: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_01c5: ldstr "Product Name"
		IL_01ca: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_01cf: ldarg.0
		IL_01d0: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_01d5: ldc.i4.s 32
		IL_01d7: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Label::set_TextAlign(valuetype [System.Drawing]System.Drawing.ContentAlignment)
		IL_01dc: ldarg.0
		IL_01dd: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_01e2: ldc.i4.s 52
		IL_01e4: ldc.i4 228
		IL_01e9: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_01ee: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_01f3: ldarg.0
		IL_01f4: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_01f9: ldc.i4.6
		IL_01fa: ldc.i4.0
		IL_01fb: ldc.i4.3
		IL_01fc: ldc.i4.0
		IL_01fd: newobj instance void [System.Windows.Forms]System.Windows.Forms.Padding::.ctor(int32, int32, int32, int32)
		IL_0202: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Margin(valuetype [System.Windows.Forms]System.Windows.Forms.Padding)
		IL_0207: ldarg.0
		IL_0208: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_020d: ldc.i4.0
		IL_020e: ldc.i4.s 17
		IL_0210: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0215: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_MaximumSize(valuetype [System.Drawing]System.Drawing.Size)
		IL_021a: ldarg.0
		IL_021b: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_0220: ldstr "labelVersion"
		IL_0225: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_022a: ldarg.0
		IL_022b: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_0230: ldc.i4 271
		IL_0235: ldc.i4.s 17
		IL_0237: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_023c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0241: ldarg.0
		IL_0242: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_0247: ldc.i4.s 26
		IL_0249: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_024e: ldarg.0
		IL_024f: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_0254: ldstr "Version"
		IL_0259: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_025e: ldarg.0
		IL_025f: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_0264: ldc.i4.s 32
		IL_0266: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Label::set_TextAlign(valuetype [System.Drawing]System.Drawing.ContentAlignment)
		IL_026b: ldarg.0
		IL_026c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_0271: ldc.i4.s 52
		IL_0273: ldc.i4 255
		IL_0278: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_027d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0282: ldarg.0
		IL_0283: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_0288: ldc.i4.6
		IL_0289: ldc.i4.0
		IL_028a: ldc.i4.3
		IL_028b: ldc.i4.0
		IL_028c: newobj instance void [System.Windows.Forms]System.Windows.Forms.Padding::.ctor(int32, int32, int32, int32)
		IL_0291: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Margin(valuetype [System.Windows.Forms]System.Windows.Forms.Padding)
		IL_0296: ldarg.0
		IL_0297: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_029c: ldc.i4.0
		IL_029d: ldc.i4.s 17
		IL_029f: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_02a4: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_MaximumSize(valuetype [System.Drawing]System.Drawing.Size)
		IL_02a9: ldarg.0
		IL_02aa: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_02af: ldstr "labelCopyright"
		IL_02b4: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_02b9: ldarg.0
		IL_02ba: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_02bf: ldc.i4 271
		IL_02c4: ldc.i4.s 17
		IL_02c6: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_02cb: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_02d0: ldarg.0
		IL_02d1: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_02d6: ldc.i4.s 28
		IL_02d8: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_02dd: ldarg.0
		IL_02de: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_02e3: ldstr "Copyright"
		IL_02e8: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_02ed: ldarg.0
		IL_02ee: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_02f3: ldc.i4.s 32
		IL_02f5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Label::set_TextAlign(valuetype [System.Drawing]System.Drawing.ContentAlignment)
		IL_02fa: ldarg.0
		IL_02fb: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_0300: ldc.i4.s 52
		IL_0302: ldc.i4 282
		IL_0307: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_030c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0311: ldarg.0
		IL_0312: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_0317: ldc.i4.6
		IL_0318: ldc.i4.0
		IL_0319: ldc.i4.3
		IL_031a: ldc.i4.0
		IL_031b: newobj instance void [System.Windows.Forms]System.Windows.Forms.Padding::.ctor(int32, int32, int32, int32)
		IL_0320: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Margin(valuetype [System.Windows.Forms]System.Windows.Forms.Padding)
		IL_0325: ldarg.0
		IL_0326: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_032b: ldc.i4.0
		IL_032c: ldc.i4.s 17
		IL_032e: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0333: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_MaximumSize(valuetype [System.Drawing]System.Drawing.Size)
		IL_0338: ldarg.0
		IL_0339: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_033e: ldstr "labelCompanyName"
		IL_0343: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0348: ldarg.0
		IL_0349: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_034e: ldc.i4 271
		IL_0353: ldc.i4.s 17
		IL_0355: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_035a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_035f: ldarg.0
		IL_0360: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_0365: ldc.i4.s 29
		IL_0367: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_036c: ldarg.0
		IL_036d: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_0372: ldstr "Company Name"
		IL_0377: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_037c: ldarg.0
		IL_037d: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_0382: ldc.i4.s 32
		IL_0384: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Label::set_TextAlign(valuetype [System.Drawing]System.Drawing.ContentAlignment)
		IL_0389: ldarg.0
		IL_038a: ldarg.0
		IL_038b: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_0390: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_AcceptButton(class [System.Windows.Forms]System.Windows.Forms.IButtonControl)
		IL_0395: ldarg.0
		IL_0396: ldc.i4.0
		IL_0397: call instance void [System.Windows.Forms]System.Windows.Forms.ContainerControl::set_AutoScaleMode(valuetype [System.Windows.Forms]System.Windows.Forms.AutoScaleMode)
		IL_039c: ldarg.0
		IL_039d: ldarg.0
		IL_039e: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_03a3: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_CancelButton(class [System.Windows.Forms]System.Windows.Forms.IButtonControl)
		IL_03a8: ldarg.0
		IL_03a9: ldc.i4 374
		IL_03ae: ldc.i4 355
		IL_03b3: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_03b8: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ClientSize(valuetype [System.Drawing]System.Drawing.Size)
		IL_03bd: ldarg.0
		IL_03be: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_03c3: ldarg.0
		IL_03c4: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelProductName
		IL_03c9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_03ce: ldarg.0
		IL_03cf: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_03d4: ldarg.0
		IL_03d5: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelVersion
		IL_03da: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_03df: ldarg.0
		IL_03e0: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_03e5: ldarg.0
		IL_03e6: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCopyright
		IL_03eb: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_03f0: ldarg.0
		IL_03f1: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_03f6: ldarg.0
		IL_03f7: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.AboutBox::labelCompanyName
		IL_03fc: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0401: ldarg.0
		IL_0402: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0407: ldarg.0
		IL_0408: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_040d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0412: ldarg.0
		IL_0413: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_0418: ldarg.0
		IL_0419: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.AboutBox::okButton
		IL_041e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0423: ldarg.0
		IL_0424: ldc.i4.3
		IL_0425: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_FormBorderStyle(valuetype [System.Windows.Forms]System.Windows.Forms.FormBorderStyle)
		IL_042a: ldarg.0
		IL_042b: ldc.i4.0
		IL_042c: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_MaximizeBox(bool)
		IL_0431: ldarg.0
		IL_0432: ldc.i4.0
		IL_0433: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_MinimizeBox(bool)
		IL_0438: ldarg.0
		IL_0439: ldstr "AboutBox"
		IL_043e: call instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0443: ldarg.0
		IL_0444: ldc.i4.s 9
		IL_0446: newobj instance void [System.Windows.Forms]System.Windows.Forms.Padding::.ctor(int32)
		IL_044b: call instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Padding(valuetype [System.Windows.Forms]System.Windows.Forms.Padding)
		IL_0450: ldarg.0
		IL_0451: ldc.i4.0
		IL_0452: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ShowIcon(bool)
		IL_0457: ldarg.0
		IL_0458: ldc.i4.0
		IL_0459: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ShowInTaskbar(bool)
		IL_045e: ldarg.0
		IL_045f: ldc.i4.1
		IL_0460: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_StartPosition(valuetype [System.Windows.Forms]System.Windows.Forms.FormStartPosition)
		IL_0465: ldarg.0
		IL_0466: ldstr "AboutBox"
		IL_046b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0470: ldarg.0
		IL_0471: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.AboutBox::logoPictureBox
		IL_0476: callvirt instance void [System]System.ComponentModel.ISupportInitialize::EndInit()
		IL_047b: ldarg.0
		IL_047c: ldc.i4.0
		IL_047d: call instance void [System.Windows.Forms]System.Windows.Forms.Control::ResumeLayout(bool)
		IL_0482: ret
	} // end of method AboutBox::InitializeComponent

	// Properties
	.property instance string AssemblyVersion()
	{
		.get instance string Philips.PSG.Gateway.AboutBox::get_AssemblyVersion()
	}
	.property instance string AssemblyProduct()
	{
		.get instance string Philips.PSG.Gateway.AboutBox::get_AssemblyProduct()
	}
	.property instance string AssemblyCopyright()
	{
		.get instance string Philips.PSG.Gateway.AboutBox::get_AssemblyCopyright()
	}
	.property instance string AssemblyCompany()
	{
		.get instance string Philips.PSG.Gateway.AboutBox::get_AssemblyCompany()
	}

} // end of class Philips.PSG.Gateway.AboutBox

.class public auto ansi beforefieldinit Philips.PSG.Gateway.MainForm
	extends [System.Windows.Forms]System.Windows.Forms.Form
{
	// Nested Types
	.class nested private auto ansi sealed Invoke_UpdateControls
		extends [mscorlib]System.MulticastDelegate
	{
		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor (
				object 'object',
				native int 'method'
			) runtime managed 
		{
		} // end of method Invoke_UpdateControls::.ctor

		.method public hidebysig newslot virtual 
			instance void Invoke () runtime managed 
		{
		} // end of method Invoke_UpdateControls::Invoke

		.method public hidebysig newslot virtual 
			instance class [mscorlib]System.IAsyncResult BeginInvoke (
				class [mscorlib]System.AsyncCallback callback,
				object 'object'
			) runtime managed 
		{
		} // end of method Invoke_UpdateControls::BeginInvoke

		.method public hidebysig newslot virtual 
			instance void EndInvoke (
				class [mscorlib]System.IAsyncResult result
			) runtime managed 
		{
		} // end of method Invoke_UpdateControls::EndInvoke

	} // end of class Invoke_UpdateControls

	.class nested private auto ansi sealed Invoke_UpdateDekTecDeviceStatusDisplay
		extends [mscorlib]System.MulticastDelegate
	{
		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor (
				object 'object',
				native int 'method'
			) runtime managed 
		{
		} // end of method Invoke_UpdateDekTecDeviceStatusDisplay::.ctor

		.method public hidebysig newslot virtual 
			instance void Invoke () runtime managed 
		{
		} // end of method Invoke_UpdateDekTecDeviceStatusDisplay::Invoke

		.method public hidebysig newslot virtual 
			instance class [mscorlib]System.IAsyncResult BeginInvoke (
				class [mscorlib]System.AsyncCallback callback,
				object 'object'
			) runtime managed 
		{
		} // end of method Invoke_UpdateDekTecDeviceStatusDisplay::BeginInvoke

		.method public hidebysig newslot virtual 
			instance void EndInvoke (
				class [mscorlib]System.IAsyncResult result
			) runtime managed 
		{
		} // end of method Invoke_UpdateDekTecDeviceStatusDisplay::EndInvoke

	} // end of class Invoke_UpdateDekTecDeviceStatusDisplay


	// Fields
	.field private class Philips.PSG.Gateway.DekTecManager _dekTecManager
	.field private class Philips.PSG.Gateway.Catalog _catalog
	.field private class Philips.PSG.Gateway.BuildProgressDialog _buildProgressDialog
	.field private class Philips.PSG.Gateway.ConfigurationDialog _configDialog
	.field private class [mscorlib]System.IO.StreamWriter _buildLog
	.field private class Philips.PSG.Gateway.Data.TsPlayer _tsPlayer
	.field private class [System]System.ComponentModel.IContainer components
	.field private class [System.Windows.Forms]System.Windows.Forms.MenuStrip menuStrip
	.field private class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem fileToolStripMenuItem
	.field private class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem
	.field private class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem helpToolStripMenuItem
	.field private class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem aboutToolStripMenuItem
	.field private class [System.Windows.Forms]System.Windows.Forms.Button runPauseButton
	.field private class [System.Windows.Forms]System.Windows.Forms.Button buildButton
	.field private class [System.Windows.Forms]System.Windows.Forms.Label dekTecDeviceStatusLabel
	.field private class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem configurationToolStripMenuItem
	.field private class [System.Windows.Forms]System.Windows.Forms.ToolStripSeparator separatorToolStripMenuItem
	.field private class [System.Windows.Forms]System.Windows.Forms.PictureBox philipsPictureBox

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x63bb
		// Header size: 1
		// Code size: 19 (0x13)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Form::.ctor()
		IL_0006: ldarg.0
		IL_0007: call instance void Philips.PSG.Gateway.MainForm::InitializeComponent()
		IL_000c: ldarg.0
		IL_000d: call instance void Philips.PSG.Gateway.MainForm::InitializeCustom()
		IL_0012: ret
	} // end of method MainForm::.ctor

	.method public hidebysig 
		instance void InitializeCustom () cil managed 
	{
		// Method begins at RVA 0x63d0
		// Header size: 12
		// Code size: 327 (0x147)
		.maxstack 3
		.locals init (
			[0] int32
		)

		IL_0000: ldstr "MainForm: initializing"
		IL_0005: call void [mscorlib]System.Console::WriteLine(string)
		IL_000a: ldarg.0
		IL_000b: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
		IL_0010: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0015: ldarg.0
		IL_0016: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::fileToolStripMenuItem
		IL_001b: call string Philips.PSG.Gateway.Resources::get_MENU_File()
		IL_0020: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Text(string)
		IL_0025: ldarg.0
		IL_0026: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem
		IL_002b: call string Philips.PSG.Gateway.Resources::get_MENU_FileConfiguration()
		IL_0030: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Text(string)
		IL_0035: ldarg.0
		IL_0036: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::exitToolStripMenuItem
		IL_003b: call string Philips.PSG.Gateway.Resources::get_MENU_FileExit()
		IL_0040: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Text(string)
		IL_0045: ldarg.0
		IL_0046: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::helpToolStripMenuItem
		IL_004b: call string Philips.PSG.Gateway.Resources::get_MENU_Help()
		IL_0050: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Text(string)
		IL_0055: ldarg.0
		IL_0056: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::aboutToolStripMenuItem
		IL_005b: call string Philips.PSG.Gateway.Resources::get_MENU_HelpAbout()
		IL_0060: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Text(string)
		IL_0065: ldarg.0
		IL_0066: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_006b: call string Philips.PSG.Gateway.Resources::get_BTN_PlayTs()
		IL_0070: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0075: ldarg.0
		IL_0076: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_007b: call string Philips.PSG.Gateway.Resources::get_BTN_BuildTs()
		IL_0080: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0085: ldarg.0
		IL_0086: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_008b: call class [System.Drawing]System.Drawing.Bitmap Philips.PSG.Gateway.Resources::get_Philips()
		IL_0090: callvirt instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::set_Image(class [System.Drawing]System.Drawing.Image)
		IL_0095: ldarg.0
		IL_0096: call instance void Philips.PSG.Gateway.MainForm::CheckForDekTecAssembly()
		IL_009b: ldarg.0
		IL_009c: call class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager::get_Instance()
		IL_00a1: stfld class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.MainForm::_dekTecManager
		IL_00a6: ldarg.0
		IL_00a7: ldnull
		IL_00a8: stfld class [mscorlib]System.IO.StreamWriter Philips.PSG.Gateway.MainForm::_buildLog
		IL_00ad: ldarg.0
		IL_00ae: newobj instance void Philips.PSG.Gateway.BuildProgressDialog::.ctor()
		IL_00b3: stfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
		IL_00b8: ldarg.0
		IL_00b9: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
		IL_00be: callvirt instance native int [System.Windows.Forms]System.Windows.Forms.Control::get_Handle()
		IL_00c3: pop
		IL_00c4: ldarg.0
		IL_00c5: newobj instance void Philips.PSG.Gateway.ConfigurationDialog::.ctor()
		IL_00ca: stfld class Philips.PSG.Gateway.ConfigurationDialog Philips.PSG.Gateway.MainForm::_configDialog
		IL_00cf: ldarg.0
		IL_00d0: call class Philips.PSG.Gateway.Catalog Philips.PSG.Gateway.Catalog::CreateFromFile()
		IL_00d5: stfld class Philips.PSG.Gateway.Catalog Philips.PSG.Gateway.MainForm::_catalog
		IL_00da: ldstr "MainForm: default catalog has item(s) -  "
		IL_00df: ldarg.0
		IL_00e0: ldfld class Philips.PSG.Gateway.Catalog Philips.PSG.Gateway.MainForm::_catalog
		IL_00e5: callvirt instance int32 Philips.PSG.Gateway.Catalog::get_NumberOfEntries()
		IL_00ea: stloc.0
		IL_00eb: ldloca.s 0
		IL_00ed: call instance string [mscorlib]System.Int32::ToString()
		IL_00f2: call string [mscorlib]System.String::Concat(string, string)
		IL_00f7: call void [mscorlib]System.Console::WriteLine(string)
		IL_00fc: ldarg.0
		IL_00fd: call class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.Data.TsPlayer::get_Instance()
		IL_0102: stfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_0107: ldarg.0
		IL_0108: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_010d: callvirt instance valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::get_State()
		IL_0112: ldc.i4.1
		IL_0113: bne.un.s IL_013a

		IL_0115: ldarg.0
		IL_0116: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_011b: callvirt instance bool Philips.PSG.Gateway.Data.TsPlayer::WasTsPlayInProgress()
		IL_0120: brfalse.s IL_013a

		IL_0122: ldarg.0
		IL_0123: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_0128: ldarg.0
		IL_0129: ldftn instance void Philips.PSG.Gateway.MainForm::OnErrorPlayStreamCallback(string, class [mscorlib]System.Exception)
		IL_012f: newobj instance void Philips.PSG.Gateway.PlayStreamHandlerErrored::.ctor(object, native int)
		IL_0134: callvirt instance bool Philips.PSG.Gateway.Data.TsPlayer::Play(class Philips.PSG.Gateway.PlayStreamHandlerErrored)
		IL_0139: pop

		IL_013a: ldarg.0
		IL_013b: call instance void Philips.PSG.Gateway.MainForm::UpdateDekTecDeviceStatusDisplay()
		IL_0140: ldarg.0
		IL_0141: call instance void Philips.PSG.Gateway.MainForm::UpdateControls()
		IL_0146: ret
	} // end of method MainForm::InitializeCustom

	.method private hidebysig 
		instance void CheckForDekTecAssembly () cil managed 
	{
		// Method begins at RVA 0x6524
		// Header size: 12
		// Code size: 80 (0x50)
		.maxstack 4
		.locals init (
			[0] class [mscorlib]System.Exception
		)

		.try
		{
			IL_0000: ldstr "DTAPINET"
			IL_0005: call class [mscorlib]System.Reflection.Assembly [mscorlib]System.Reflection.Assembly::Load(string)
			IL_000a: pop
			IL_000b: leave.s IL_004f
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_000d: stloc.0
			IL_000e: ldstr "MainForm: Fatal Error - failed to load DekTec assembly or supporting libraries: "
			IL_0013: ldloc.0
			IL_0014: dup
			IL_0015: brtrue.s IL_001b

			IL_0017: pop
			IL_0018: ldnull
			IL_0019: br.s IL_0020

			IL_001b: callvirt instance string [mscorlib]System.Object::ToString()

			IL_0020: call string [mscorlib]System.String::Concat(string, string)
			IL_0025: call void [mscorlib]System.Console::WriteLine(string)
			IL_002a: ldarg.0
			IL_002b: ldstr "MainForm: Fatal Error - failed to load DekTec assembly or supporting libraries: "
			IL_0030: ldloc.0
			IL_0031: dup
			IL_0032: brtrue.s IL_0038

			IL_0034: pop
			IL_0035: ldnull
			IL_0036: br.s IL_003d

			IL_0038: callvirt instance string [mscorlib]System.Object::ToString()

			IL_003d: call string [mscorlib]System.String::Concat(string, string)
			IL_0042: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_0047: ldc.i4.0
			IL_0048: call void [mscorlib]System.Environment::Exit(int32)
			IL_004d: leave.s IL_004f
		} // end handler

		IL_004f: ret
	} // end of method MainForm::CheckForDekTecAssembly

	.method private hidebysig 
		instance void UpdateDekTecDeviceStatusDisplay () cil managed 
	{
		// Method begins at RVA 0x6590
		// Header size: 12
		// Code size: 157 (0x9d)
		.maxstack 5
		.locals init (
			[0] string,
			[1] class [mscorlib]System.Text.StringBuilder,
			[2] class Philips.PSG.Gateway.Config
		)

		IL_0000: ldarg.0
		IL_0001: call instance bool [System.Windows.Forms]System.Windows.Forms.Control::get_InvokeRequired()
		IL_0006: brfalse.s IL_001c

		IL_0008: ldarg.0
		IL_0009: ldarg.0
		IL_000a: ldftn instance void Philips.PSG.Gateway.MainForm::UpdateDekTecDeviceStatusDisplay()
		IL_0010: newobj instance void Philips.PSG.Gateway.MainForm/Invoke_UpdateDekTecDeviceStatusDisplay::.ctor(object, native int)
		IL_0015: call instance object [System.Windows.Forms]System.Windows.Forms.Control::Invoke(class [mscorlib]System.Delegate)
		IL_001a: pop
		IL_001b: ret

		IL_001c: ldarg.0
		IL_001d: ldfld class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.MainForm::_dekTecManager
		IL_0022: callvirt instance string Philips.PSG.Gateway.DekTecManager::get_DeviceType()
		IL_0027: stloc.0
		IL_0028: ldloc.0
		IL_0029: call bool [mscorlib]System.String::IsNullOrEmpty(string)
		IL_002e: brfalse.s IL_0041

		IL_0030: ldarg.0
		IL_0031: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.MainForm::dekTecDeviceStatusLabel
		IL_0036: call string Philips.PSG.Gateway.Resources::get_LBL_DekTecDeviceNotAvailable()
		IL_003b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0040: ret

		IL_0041: newobj instance void [mscorlib]System.Text.StringBuilder::.ctor()
		IL_0046: stloc.1
		IL_0047: ldloc.1
		IL_0048: call string Philips.PSG.Gateway.Resources::get_LBL_DekTecDeviceAvailable()
		IL_004d: ldloc.0
		IL_004e: callvirt instance class [mscorlib]System.Text.StringBuilder [mscorlib]System.Text.StringBuilder::AppendFormat(string, object)
		IL_0053: pop
		IL_0054: ldarg.0
		IL_0055: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_005a: callvirt instance valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::get_State()
		IL_005f: ldc.i4.2
		IL_0060: bne.un.s IL_008b

		IL_0062: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0067: stloc.2
		IL_0068: ldloc.1
		IL_0069: call string Philips.PSG.Gateway.Resources::get_LBL_DekTecDeviceConfiguration()
		IL_006e: ldloc.2
		IL_006f: callvirt instance string Philips.PSG.Gateway.Config::get_ModulationTypeAsString()
		IL_0074: ldloc.2
		IL_0075: callvirt instance int32 Philips.PSG.Gateway.Config::get_ModulationFrequency()
		IL_007a: box [mscorlib]System.Int32
		IL_007f: ldloc.2
		IL_0080: callvirt instance string Philips.PSG.Gateway.Config::get_ConstellationAsString()
		IL_0085: callvirt instance class [mscorlib]System.Text.StringBuilder [mscorlib]System.Text.StringBuilder::AppendFormat(string, object, object, object)
		IL_008a: pop

		IL_008b: ldarg.0
		IL_008c: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.MainForm::dekTecDeviceStatusLabel
		IL_0091: ldloc.1
		IL_0092: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0097: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_009c: ret
	} // end of method MainForm::UpdateDekTecDeviceStatusDisplay

	.method private hidebysig 
		instance void UpdateControls () cil managed 
	{
		// Method begins at RVA 0x663c
		// Header size: 12
		// Code size: 263 (0x107)
		.maxstack 3
		.locals init (
			[0] valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState,
			[1] valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState
		)

		IL_0000: ldarg.0
		IL_0001: call instance bool [System.Windows.Forms]System.Windows.Forms.Control::get_InvokeRequired()
		IL_0006: brfalse.s IL_001c

		IL_0008: ldarg.0
		IL_0009: ldarg.0
		IL_000a: ldftn instance void Philips.PSG.Gateway.MainForm::UpdateControls()
		IL_0010: newobj instance void Philips.PSG.Gateway.MainForm/Invoke_UpdateControls::.ctor(object, native int)
		IL_0015: call instance object [System.Windows.Forms]System.Windows.Forms.Control::Invoke(class [mscorlib]System.Delegate)
		IL_001a: pop
		IL_001b: ret

		IL_001c: ldarg.0
		IL_001d: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_0022: callvirt instance valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::get_State()
		IL_0027: stloc.0
		IL_0028: ldloc.0
		IL_0029: switch (IL_003f, IL_0074, IL_00a9)

		IL_003a: br IL_00de

		IL_003f: ldarg.0
		IL_0040: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_0045: ldc.i4.1
		IL_0046: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Enabled(bool)
		IL_004b: ldarg.0
		IL_004c: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0051: call string Philips.PSG.Gateway.Resources::get_BTN_PlayTs()
		IL_0056: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_005b: ldarg.0
		IL_005c: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0061: ldc.i4.0
		IL_0062: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Enabled(bool)
		IL_0067: ldarg.0
		IL_0068: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem
		IL_006d: ldc.i4.1
		IL_006e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Enabled(bool)
		IL_0073: ret

		IL_0074: ldarg.0
		IL_0075: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_007a: ldc.i4.1
		IL_007b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Enabled(bool)
		IL_0080: ldarg.0
		IL_0081: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0086: call string Philips.PSG.Gateway.Resources::get_BTN_PlayTs()
		IL_008b: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0090: ldarg.0
		IL_0091: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0096: ldc.i4.1
		IL_0097: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Enabled(bool)
		IL_009c: ldarg.0
		IL_009d: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem
		IL_00a2: ldc.i4.1
		IL_00a3: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Enabled(bool)
		IL_00a8: ret

		IL_00a9: ldarg.0
		IL_00aa: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_00af: ldc.i4.0
		IL_00b0: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Enabled(bool)
		IL_00b5: ldarg.0
		IL_00b6: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_00bb: call string Philips.PSG.Gateway.Resources::get_BTN_StopTs()
		IL_00c0: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_00c5: ldarg.0
		IL_00c6: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_00cb: ldc.i4.1
		IL_00cc: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Enabled(bool)
		IL_00d1: ldarg.0
		IL_00d2: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem
		IL_00d7: ldc.i4.0
		IL_00d8: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Enabled(bool)
		IL_00dd: ret

		IL_00de: ldstr "MainForm: unhandled TsPlayer state "
		IL_00e3: ldarg.0
		IL_00e4: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_00e9: callvirt instance valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::get_State()
		IL_00ee: stloc.1
		IL_00ef: ldloca.s 1
		IL_00f1: constrained. Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState
		IL_00f7: callvirt instance string [mscorlib]System.Object::ToString()
		IL_00fc: call string [mscorlib]System.String::Concat(string, string)
		IL_0101: call void [mscorlib]System.Console::WriteLine(string)
		IL_0106: ret
	} // end of method MainForm::UpdateControls

	.method private hidebysig 
		instance class Philips.PSG.Gateway.ScheduleEvent[] ProcessCopyBuildAction (
			string rootFolder,
			class Philips.PSG.Gateway.CatalogEntry entry
		) cil managed 
	{
		// Method begins at RVA 0x6750
		// Header size: 12
		// Code size: 332 (0x14c)
		.maxstack 3
		.locals init (
			[0] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>,
			[1] class Philips.PSG.Gateway.ScheduleEvent,
			[2] string,
			[3] string,
			[4] string,
			[5] class Philips.PSG.Gateway.FileSet,
			[6] class Philips.PSG.Gateway.ScheduleEvent[],
			[7] int32,
			[8] class [mscorlib]System.Exception
		)

		IL_0000: ldstr "MainForm: processing copy build action"
		IL_0005: call void [mscorlib]System.Console::WriteLine(string)
		IL_000a: ldarg.0
		IL_000b: ldstr "TV Model requires a copy build action"
		IL_0010: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
		IL_0015: newobj instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::.ctor()
		IL_001a: stloc.0
		IL_001b: ldarg.1
		IL_001c: ldstr "\\"
		IL_0021: ldarg.2
		IL_0022: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_BaseFolder()
		IL_0027: call string [mscorlib]System.String::Concat(string, string, string)
		IL_002c: stloc.2
		IL_002d: ldarg.2
		IL_002e: callvirt instance class Philips.PSG.Gateway.FileSet[] Philips.PSG.Gateway.CatalogEntry::get_FileSets()
		IL_0033: ldc.i4.0
		IL_0034: ldelem.ref
		IL_0035: stloc.s 5
		.try
		{
			.try
			{
				IL_0037: ldloc.s 5
				IL_0039: callvirt instance bool Philips.PSG.Gateway.FileSet::get_IncludedInBuild()
				IL_003e: brtrue.s IL_004b

				IL_0040: ldstr "The required FileSet is marked as excluded from the build"
				IL_0045: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
				IL_004a: throw

				IL_004b: ldloc.s 5
				IL_004d: ldloc.2
				IL_004e: callvirt instance string Philips.PSG.Gateway.FileSet::PrepareUsageArgument(string)
				IL_0053: stloc.3
				IL_0054: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
				IL_0059: callvirt instance string Philips.PSG.Gateway.Config::get_ScheduleDataFolder()
				IL_005e: ldstr "\\"
				IL_0063: ldarg.2
				IL_0064: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_OutputFileName()
				IL_0069: call string [mscorlib]System.String::Concat(string, string, string)
				IL_006e: stloc.s 4
				IL_0070: ldstr "MainForm: copying file\n          from: {0}\n            to: {1}"
				IL_0075: ldloc.3
				IL_0076: ldloc.s 4
				IL_0078: call void [mscorlib]System.Console::WriteLine(string, object, object)
				IL_007d: ldarg.0
				IL_007e: ldstr "Copying from {0}"
				IL_0083: ldloc.3
				IL_0084: call string [mscorlib]System.String::Format(string, object)
				IL_0089: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_008e: ldarg.0
				IL_008f: ldstr "          to {0}"
				IL_0094: ldloc.s 4
				IL_0096: call string [mscorlib]System.String::Format(string, object)
				IL_009b: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_00a0: ldarg.0
				IL_00a1: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
				IL_00a6: call string Philips.PSG.Gateway.Resources::get_DLG_BuildActionCopyingMessage()
				IL_00ab: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetAction(string)
				IL_00b0: ldloc.3
				IL_00b1: ldloc.s 4
				IL_00b3: call void [mscorlib]System.IO.File::Copy(string, string)
				IL_00b8: ldarg.2
				IL_00b9: callvirt instance class Philips.PSG.Gateway.ScheduleEvent[] Philips.PSG.Gateway.CatalogEntry::get_DefaultScheduleEvents()
				IL_00be: stloc.s 6
				IL_00c0: ldc.i4.0
				IL_00c1: stloc.s 7
				IL_00c3: br.s IL_00e5
				// loop start (head: IL_00e5)
					IL_00c5: ldloc.s 6
					IL_00c7: ldloc.s 7
					IL_00c9: ldelem.ref
					IL_00ca: callvirt instance class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleEvent::Clone()
					IL_00cf: stloc.1
					IL_00d0: ldloc.1
					IL_00d1: ldloc.s 4
					IL_00d3: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_TransportStreamFile(string)
					IL_00d8: ldloc.0
					IL_00d9: ldloc.1
					IL_00da: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
					IL_00df: ldloc.s 7
					IL_00e1: ldc.i4.1
					IL_00e2: add
					IL_00e3: stloc.s 7

					IL_00e5: ldloc.s 7
					IL_00e7: ldloc.s 6
					IL_00e9: ldlen
					IL_00ea: conv.i4
					IL_00eb: blt.s IL_00c5
				// end loop

				IL_00ed: ldstr "Mainform: suggested {0} new schedule event(s)"
				IL_00f2: ldloc.0
				IL_00f3: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::get_Count()
				IL_00f8: box [mscorlib]System.Int32
				IL_00fd: call void [mscorlib]System.Console::WriteLine(string, object)
				IL_0102: ldarg.0
				IL_0103: ldstr "Build action completed"
				IL_0108: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_010d: leave.s IL_0145
			} // end .try
			catch [mscorlib]System.Exception
			{
				IL_010f: stloc.s 8
				IL_0111: ldstr "MainForm: Error - build action failed: {0}"
				IL_0116: ldloc.s 8
				IL_0118: call void [mscorlib]System.Console::WriteLine(string, object)
				IL_011d: ldarg.0
				IL_011e: ldstr "Build action failed with exception: {0}"
				IL_0123: ldloc.s 8
				IL_0125: callvirt instance string [mscorlib]System.Exception::get_Message()
				IL_012a: call string [mscorlib]System.String::Format(string, object)
				IL_012f: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_0134: ldloc.0
				IL_0135: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Clear()
				IL_013a: leave.s IL_0145
			} // end handler
		} // end .try
		finally
		{
			IL_013c: ldloc.s 5
			IL_013e: ldloc.2
			IL_013f: callvirt instance void Philips.PSG.Gateway.FileSet::ReleaseUsageArgument(string)
			IL_0144: endfinally
		} // end handler

		IL_0145: ldloc.0
		IL_0146: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::ToArray()
		IL_014b: ret
	} // end of method MainForm::ProcessCopyBuildAction

	.method private hidebysig 
		instance void GenerateBuildProgressCB (
			valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage message
		) cil managed 
	{
		// Method begins at RVA 0x68dc
		// Header size: 12
		// Code size: 197 (0xc5)
		.maxstack 2

		.try
		{
			IL_0000: ldarg.1
			IL_0001: switch (IL_0023, IL_0040, IL_005d, IL_00b4, IL_007a, IL_0097)

			IL_001e: br IL_00b4

			IL_0023: ldarg.0
			IL_0024: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
			IL_0029: call string Philips.PSG.Gateway.Resources::get_DLG_BuildActionPreparingChannelTableMessage()
			IL_002e: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetAction(string)
			IL_0033: ldarg.0
			IL_0034: ldstr "Preparing Channel Table"
			IL_0039: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_003e: br.s IL_00bf

			IL_0040: ldarg.0
			IL_0041: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
			IL_0046: call string Philips.PSG.Gateway.Resources::get_DLG_BuildActionPreparingNvmMessage()
			IL_004b: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetAction(string)
			IL_0050: ldarg.0
			IL_0051: ldstr "Preparing NVM"
			IL_0056: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_005b: br.s IL_00bf

			IL_005d: ldarg.0
			IL_005e: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
			IL_0063: call string Philips.PSG.Gateway.Resources::get_DLG_BuildActionPreparingThemeMessage()
			IL_0068: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetAction(string)
			IL_006d: ldarg.0
			IL_006e: ldstr "Preparing Theme"
			IL_0073: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_0078: br.s IL_00bf

			IL_007a: ldarg.0
			IL_007b: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
			IL_0080: call string Philips.PSG.Gateway.Resources::get_DLG_BuildActionGeneratingTransportStreamMessage()
			IL_0085: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetAction(string)
			IL_008a: ldarg.0
			IL_008b: ldstr "Generating transport stream"
			IL_0090: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_0095: br.s IL_00bf

			IL_0097: ldarg.0
			IL_0098: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
			IL_009d: call string Philips.PSG.Gateway.Resources::get_DLG_BuildActionMergingTransportStreamMessage()
			IL_00a2: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetAction(string)
			IL_00a7: ldarg.0
			IL_00a8: ldstr "Generating transport stream"
			IL_00ad: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_00b2: br.s IL_00bf

			IL_00b4: ldarg.0
			IL_00b5: ldstr "Unknown progress message reported"
			IL_00ba: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)

			IL_00bf: leave.s IL_00c4
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_00c1: pop
			IL_00c2: leave.s IL_00c4
		} // end handler

		IL_00c4: ret
	} // end of method MainForm::GenerateBuildProgressCB

	.method private hidebysig 
		instance bool ProcessGenerateBuildAction (
			string rootFolder,
			class Philips.PSG.Gateway.CatalogEntry entry
		) cil managed 
	{
		// Method begins at RVA 0x69c0
		// Header size: 12
		// Code size: 987 (0x3db)
		.maxstack 5
		.locals init (
			[0] bool,
			[1] string,
			[2] string,
			[3] class Philips.PSG.Gateway.Config,
			[4] valuetype Philips.PSG.Gateway.TSGen/TS_Result,
			[5] valuetype [mscorlib]System.Nullable`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>,
			[6] valuetype [mscorlib]System.Nullable`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>,
			[7] class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>,
			[8] string,
			[9] string,
			[10] string,
			[11] string,
			[12] string,
			[13] bool,
			[14] valuetype Philips.PSG.Gateway.TSGen/TS_NetworkDetails,
			[15] class Philips.PSG.Gateway.FileSet[],
			[16] int32,
			[17] class Philips.PSG.Gateway.FileSet,
			[18] valuetype Philips.PSG.Gateway.FileSet/FileSetID,
			[19] valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails,
			[20] uint16,
			[21] class [mscorlib]System.Exception,
			[22] class Philips.PSG.Gateway.FileSet
		)

		IL_0000: ldc.i4.0
		IL_0001: stloc.0
		IL_0002: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0007: stloc.3
		IL_0008: ldloca.s 5
		IL_000a: initobj valuetype [mscorlib]System.Nullable`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>
		IL_0010: ldloca.s 6
		IL_0012: initobj valuetype [mscorlib]System.Nullable`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>
		IL_0018: newobj instance void class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::.ctor()
		IL_001d: stloc.s 7
		IL_001f: ldstr "MainForm: processing generate build action"
		IL_0024: call void [mscorlib]System.Console::WriteLine(string)
		IL_0029: ldarg.0
		IL_002a: ldstr "TV Model requires a generate build action"
		IL_002f: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
		IL_0034: ldarg.1
		IL_0035: ldstr "\\"
		IL_003a: ldarg.2
		IL_003b: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_BaseFolder()
		IL_0040: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0045: stloc.1
		IL_0046: ldloc.1
		IL_0047: ldstr "\\"
		IL_004c: ldarg.2
		IL_004d: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_OutputFileName()
		IL_0052: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0057: stloc.2
		IL_0058: ldstr "entry.BaseFolder = "
		IL_005d: ldarg.2
		IL_005e: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_BaseFolder()
		IL_0063: call string [mscorlib]System.String::Concat(string, string)
		IL_0068: call void [mscorlib]System.Console::WriteLine(string)
		IL_006d: ldstr "entry.OutputFileName = "
		IL_0072: ldarg.2
		IL_0073: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_OutputFileName()
		IL_0078: call string [mscorlib]System.String::Concat(string, string)
		IL_007d: call void [mscorlib]System.Console::WriteLine(string)
		IL_0082: ldarg.2
		IL_0083: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_BaseFolder()
		IL_0088: stloc.s 8
		IL_008a: ldstr "2K14"
		IL_008f: stloc.s 9
		IL_0091: ldstr "2K15"
		IL_0096: stloc.s 10
		IL_0098: ldstr "2K16"
		IL_009d: stloc.s 11
		IL_009f: ldstr "0000"
		IL_00a4: stloc.s 12
		IL_00a6: ldc.i4.0
		IL_00a7: stloc.s 13
		IL_00a9: ldloc.s 8
		IL_00ab: ldloc.s 9
		IL_00ad: callvirt instance int32 [mscorlib]System.String::IndexOf(string)
		IL_00b2: ldc.i4.m1
		IL_00b3: ble.s IL_00cb

		IL_00b5: ldstr "2K14 Model"
		IL_00ba: call void [mscorlib]System.Console::WriteLine(string)
		IL_00bf: ldc.i4.1
		IL_00c0: stloc.s 13
		IL_00c2: ldstr "0000"
		IL_00c7: stloc.s 12
		IL_00c9: br.s IL_011a

		IL_00cb: ldloc.s 8
		IL_00cd: ldloc.s 10
		IL_00cf: callvirt instance int32 [mscorlib]System.String::IndexOf(string)
		IL_00d4: ldc.i4.m1
		IL_00d5: ble.s IL_00ed

		IL_00d7: ldstr "2K15 Model"
		IL_00dc: call void [mscorlib]System.Console::WriteLine(string)
		IL_00e1: ldc.i4.1
		IL_00e2: stloc.s 13
		IL_00e4: ldstr "00000"
		IL_00e9: stloc.s 12
		IL_00eb: br.s IL_011a

		IL_00ed: ldloc.s 8
		IL_00ef: ldloc.s 11
		IL_00f1: callvirt instance int32 [mscorlib]System.String::IndexOf(string)
		IL_00f6: ldc.i4.m1
		IL_00f7: ble.s IL_010f

		IL_00f9: ldstr "2K16 Model"
		IL_00fe: call void [mscorlib]System.Console::WriteLine(string)
		IL_0103: ldc.i4.1
		IL_0104: stloc.s 13
		IL_0106: ldstr "00000"
		IL_010b: stloc.s 12
		IL_010d: br.s IL_011a

		IL_010f: ldc.i4.0
		IL_0110: stloc.s 13
		IL_0112: ldloc.3
		IL_0113: callvirt instance string Philips.PSG.Gateway.Config::get_RoomNumber()
		IL_0118: stloc.s 12

		IL_011a: ldstr "use_room_number_string = "
		IL_011f: ldloca.s 13
		IL_0121: call instance string [mscorlib]System.Boolean::ToString()
		IL_0126: call string [mscorlib]System.String::Concat(string, string)
		IL_012b: call void [mscorlib]System.Console::WriteLine(string)
		IL_0130: ldloc.3
		IL_0131: call valuetype Philips.PSG.Gateway.TSGen/TS_NetworkDetails Philips.PSG.Gateway.MainForm::GetNetworkDetails(class Philips.PSG.Gateway.Config)
		IL_0136: stloc.s 14
		.try
		{
			.try
			{
				IL_0138: ldarg.2
				IL_0139: callvirt instance class Philips.PSG.Gateway.FileSet[] Philips.PSG.Gateway.CatalogEntry::get_FileSets()
				IL_013e: stloc.s 15
				IL_0140: ldc.i4.0
				IL_0141: stloc.s 16
				IL_0143: br IL_0308
				// loop start (head: IL_0308)
					IL_0148: ldloc.s 15
					IL_014a: ldloc.s 16
					IL_014c: ldelem.ref
					IL_014d: stloc.s 17
					IL_014f: ldloc.s 17
					IL_0151: callvirt instance bool Philips.PSG.Gateway.FileSet::get_IsValid()
					IL_0156: brfalse IL_02b4

					IL_015b: ldloc.s 17
					IL_015d: callvirt instance valuetype Philips.PSG.Gateway.FileSet/FileSetID Philips.PSG.Gateway.FileSet::get_ID()
					IL_0162: stloc.s 18
					IL_0164: ldloc.s 18
					IL_0166: ldc.i4.s 13
					IL_0168: beq.s IL_0178

					IL_016a: ldloc.s 18
					IL_016c: ldc.i4.s 14
					IL_016e: beq IL_0212

					IL_0173: br IL_02a9

					IL_0178: ldstr "FileSet.FileSetID.Easy2K11Clone filePath"
					IL_017d: ldloc.s 17
					IL_017f: ldloc.1
					IL_0180: callvirt instance string Philips.PSG.Gateway.FileSet::PrepareUsageArgument(string)
					IL_0185: call string [mscorlib]System.String::Concat(string, string)
					IL_018a: call void [mscorlib]System.Console::WriteLine(string)
					IL_018f: ldloca.s 19
					IL_0191: initobj Philips.PSG.Gateway.TSGen/TS_FileDetails
					IL_0197: ldloca.s 19
					IL_0199: ldloc.s 17
					IL_019b: ldloc.1
					IL_019c: callvirt instance string Philips.PSG.Gateway.FileSet::PrepareUsageArgument(string)
					IL_01a1: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::filePath
					IL_01a6: ldloca.s 19
					IL_01a8: ldc.i4.0
					IL_01a9: stfld valuetype Philips.PSG.Gateway.TSGen/TS_FileType Philips.PSG.Gateway.TSGen/TS_FileDetails::fileType
					IL_01ae: ldloca.s 19
					IL_01b0: ldloc.s 17
					IL_01b2: callvirt instance valuetype Philips.PSG.Gateway.TSGen/TS_FileVersionDetails Philips.PSG.Gateway.FileSet::GetVersionDetails()
					IL_01b7: stfld valuetype Philips.PSG.Gateway.TSGen/TS_FileVersionDetails Philips.PSG.Gateway.TSGen/TS_FileDetails::fileVersionDetails
					IL_01bc: ldloca.s 19
					IL_01be: ldloc.3
					IL_01bf: callvirt instance string Philips.PSG.Gateway.Config::get_ChannelTableIdentifier()
					IL_01c4: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::channel_table_identifier
					IL_01c9: ldloca.s 19
					IL_01cb: ldloc.s 12
					IL_01cd: ldc.i4 515
					IL_01d2: call uint16 [mscorlib]System.UInt16::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
					IL_01d7: stfld uint32 Philips.PSG.Gateway.TSGen/TS_FileDetails::room_number
					IL_01dc: ldloca.s 19
					IL_01de: ldloc.3
					IL_01df: callvirt instance string Philips.PSG.Gateway.Config::get_RoomNumber()
					IL_01e4: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::room_number_string
					IL_01e9: ldloca.s 19
					IL_01eb: ldloc.3
					IL_01ec: callvirt instance string Philips.PSG.Gateway.Config::get_SsbIdentifier()
					IL_01f1: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::ssb_identifier
					IL_01f6: ldloc.s 19
					IL_01f8: newobj instance void valuetype [mscorlib]System.Nullable`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::.ctor(!0)
					IL_01fd: stloc.s 5
					IL_01ff: ldloc.s 7
					IL_0201: ldloca.s 5
					IL_0203: call instance !0 valuetype [mscorlib]System.Nullable`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::get_Value()
					IL_0208: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::Add(!0)
					IL_020d: br IL_02b4

					IL_0212: ldstr "FileSet.FileSetID.Easy2K11Firmware filePath"
					IL_0217: ldloc.s 17
					IL_0219: ldloc.1
					IL_021a: callvirt instance string Philips.PSG.Gateway.FileSet::PrepareUsageArgument(string)
					IL_021f: call string [mscorlib]System.String::Concat(string, string)
					IL_0224: call void [mscorlib]System.Console::WriteLine(string)
					IL_0229: ldloca.s 19
					IL_022b: initobj Philips.PSG.Gateway.TSGen/TS_FileDetails
					IL_0231: ldloca.s 19
					IL_0233: ldloc.s 17
					IL_0235: ldloc.1
					IL_0236: callvirt instance string Philips.PSG.Gateway.FileSet::PrepareUsageArgument(string)
					IL_023b: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::filePath
					IL_0240: ldloca.s 19
					IL_0242: ldc.i4.1
					IL_0243: stfld valuetype Philips.PSG.Gateway.TSGen/TS_FileType Philips.PSG.Gateway.TSGen/TS_FileDetails::fileType
					IL_0248: ldloca.s 19
					IL_024a: ldloc.s 17
					IL_024c: callvirt instance valuetype Philips.PSG.Gateway.TSGen/TS_FileVersionDetails Philips.PSG.Gateway.FileSet::GetVersionDetails()
					IL_0251: stfld valuetype Philips.PSG.Gateway.TSGen/TS_FileVersionDetails Philips.PSG.Gateway.TSGen/TS_FileDetails::fileVersionDetails
					IL_0256: ldloca.s 19
					IL_0258: ldloc.3
					IL_0259: callvirt instance string Philips.PSG.Gateway.Config::get_ChannelTableIdentifier()
					IL_025e: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::channel_table_identifier
					IL_0263: ldloca.s 19
					IL_0265: ldloc.s 12
					IL_0267: ldc.i4 515
					IL_026c: call uint16 [mscorlib]System.UInt16::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
					IL_0271: stfld uint32 Philips.PSG.Gateway.TSGen/TS_FileDetails::room_number
					IL_0276: ldloca.s 19
					IL_0278: ldloc.3
					IL_0279: callvirt instance string Philips.PSG.Gateway.Config::get_RoomNumber()
					IL_027e: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::room_number_string
					IL_0283: ldloca.s 19
					IL_0285: ldloc.3
					IL_0286: callvirt instance string Philips.PSG.Gateway.Config::get_SsbIdentifier()
					IL_028b: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::ssb_identifier
					IL_0290: ldloc.s 19
					IL_0292: newobj instance void valuetype [mscorlib]System.Nullable`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::.ctor(!0)
					IL_0297: stloc.s 6
					IL_0299: ldloc.s 7
					IL_029b: ldloca.s 6
					IL_029d: call instance !0 valuetype [mscorlib]System.Nullable`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::get_Value()
					IL_02a2: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::Add(!0)
					IL_02a7: br.s IL_02b4

					IL_02a9: ldstr "Invalid FileSet ID"
					IL_02ae: newobj instance void [System]System.IO.InvalidDataException::.ctor(string)
					IL_02b3: throw

					IL_02b4: ldstr "config.RoomNumber = "
					IL_02b9: ldloc.3
					IL_02ba: callvirt instance string Philips.PSG.Gateway.Config::get_RoomNumber()
					IL_02bf: call string [mscorlib]System.String::Concat(string, string)
					IL_02c4: call void [mscorlib]System.Console::WriteLine(string)
					IL_02c9: ldstr "room_number = "
					IL_02ce: ldloc.s 12
					IL_02d0: ldc.i4 515
					IL_02d5: call uint16 [mscorlib]System.UInt16::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
					IL_02da: stloc.s 20
					IL_02dc: ldloca.s 20
					IL_02de: call instance string [mscorlib]System.UInt16::ToString()
					IL_02e3: call string [mscorlib]System.String::Concat(string, string)
					IL_02e8: call void [mscorlib]System.Console::WriteLine(string)
					IL_02ed: ldstr "room_number_string = "
					IL_02f2: ldloc.3
					IL_02f3: callvirt instance string Philips.PSG.Gateway.Config::get_RoomNumber()
					IL_02f8: call string [mscorlib]System.String::Concat(string, string)
					IL_02fd: call void [mscorlib]System.Console::WriteLine(string)
					IL_0302: ldloc.s 16
					IL_0304: ldc.i4.1
					IL_0305: add
					IL_0306: stloc.s 16

					IL_0308: ldloc.s 16
					IL_030a: ldloc.s 15
					IL_030c: ldlen
					IL_030d: conv.i4
					IL_030e: blt IL_0148
				// end loop

				IL_0313: ldloc.s 7
				IL_0315: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::get_Count()
				IL_031a: ldc.i4.0
				IL_031b: ble.s IL_037c

				IL_031d: ldarg.0
				IL_031e: ldftn instance void Philips.PSG.Gateway.MainForm::GenerateBuildProgressCB(valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage)
				IL_0324: newobj instance void Philips.PSG.Gateway.TSGen/TS_ProgressCallback::.ctor(object, native int)
				IL_0329: ldloc.2
				IL_032a: ldloc.s 7
				IL_032c: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::ToArray()
				IL_0331: ldloc.s 7
				IL_0333: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::get_Count()
				IL_0338: ldloc.s 14
				IL_033a: call valuetype Philips.PSG.Gateway.TSGen/TS_Result Philips.PSG.Gateway.TSGen::TS_Generate(class Philips.PSG.Gateway.TSGen/TS_ProgressCallback, string, valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails[], int32, valuetype Philips.PSG.Gateway.TSGen/TS_NetworkDetails)
				IL_033f: stloc.s 4
				IL_0341: ldloc.s 4
				IL_0343: brtrue.s IL_0365

				IL_0345: ldarg.0
				IL_0346: ldstr "Generated stream saved to {0}"
				IL_034b: ldloc.2
				IL_034c: call string [mscorlib]System.String::Format(string, object)
				IL_0351: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_0356: ldarg.0
				IL_0357: ldstr "Build action completed"
				IL_035c: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_0361: ldc.i4.1
				IL_0362: stloc.0
				IL_0363: br.s IL_037c

				IL_0365: ldarg.0
				IL_0366: ldstr "Build action failed with {0}"
				IL_036b: ldloc.s 4
				IL_036d: box Philips.PSG.Gateway.TSGen/TS_Result
				IL_0372: call string [mscorlib]System.String::Format(string, object)
				IL_0377: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)

				IL_037c: leave.s IL_03d9
			} // end .try
			catch [mscorlib]System.Exception
			{
				IL_037e: stloc.s 21
				IL_0380: ldstr "MainForm: Error - build action failed: {0}"
				IL_0385: ldloc.s 21
				IL_0387: call void [mscorlib]System.Console::WriteLine(string, object)
				IL_038c: ldarg.0
				IL_038d: ldstr "Build action failed with exception: {0}"
				IL_0392: ldloc.s 21
				IL_0394: callvirt instance string [mscorlib]System.Exception::get_Message()
				IL_0399: call string [mscorlib]System.String::Format(string, object)
				IL_039e: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_03a3: leave.s IL_03d9
			} // end handler
		} // end .try
		finally
		{
			IL_03a5: ldarg.2
			IL_03a6: callvirt instance class Philips.PSG.Gateway.FileSet[] Philips.PSG.Gateway.CatalogEntry::get_FileSets()
			IL_03ab: stloc.s 15
			IL_03ad: ldc.i4.0
			IL_03ae: stloc.s 16
			IL_03b0: br.s IL_03d0
			// loop start (head: IL_03d0)
				IL_03b2: ldloc.s 15
				IL_03b4: ldloc.s 16
				IL_03b6: ldelem.ref
				IL_03b7: stloc.s 22
				IL_03b9: ldloc.s 22
				IL_03bb: callvirt instance bool Philips.PSG.Gateway.FileSet::get_IncludedInBuild()
				IL_03c0: brfalse.s IL_03ca

				IL_03c2: ldloc.s 22
				IL_03c4: ldloc.1
				IL_03c5: callvirt instance void Philips.PSG.Gateway.FileSet::ReleaseUsageArgument(string)

				IL_03ca: ldloc.s 16
				IL_03cc: ldc.i4.1
				IL_03cd: add
				IL_03ce: stloc.s 16

				IL_03d0: ldloc.s 16
				IL_03d2: ldloc.s 15
				IL_03d4: ldlen
				IL_03d5: conv.i4
				IL_03d6: blt.s IL_03b2
			// end loop

			IL_03d8: endfinally
		} // end handler

		IL_03d9: ldloc.0
		IL_03da: ret
	} // end of method MainForm::ProcessGenerateBuildAction

	.method private hidebysig static 
		valuetype Philips.PSG.Gateway.TSGen/TS_NetworkDetails GetNetworkDetails (
			class Philips.PSG.Gateway.Config config
		) cil managed 
	{
		// Method begins at RVA 0x6ddc
		// Header size: 12
		// Code size: 295 (0x127)
		.maxstack 3
		.locals init (
			[0] valuetype Philips.PSG.Gateway.TSGen/TS_NetworkDetails
		)

		IL_0000: ldloca.s 0
		IL_0002: initobj Philips.PSG.Gateway.TSGen/TS_NetworkDetails
		IL_0008: ldloca.s 0
		IL_000a: ldarg.0
		IL_000b: callvirt instance string Philips.PSG.Gateway.Config::get_OriginalNetworkId()
		IL_0010: ldc.i4 515
		IL_0015: call uint16 [mscorlib]System.UInt16::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
		IL_001a: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::original_network_id
		IL_001f: ldloca.s 0
		IL_0021: ldarg.0
		IL_0022: callvirt instance int32 Philips.PSG.Gateway.Config::get_ModulationFrequency()
		IL_0027: stfld uint32 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::centre_frequency
		IL_002c: ldloca.s 0
		IL_002e: ldarg.0
		IL_002f: callvirt instance uint16 Philips.PSG.Gateway.Config::get_BandwidthTs()
		IL_0034: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::bandwidth
		IL_0039: ldloca.s 0
		IL_003b: ldarg.0
		IL_003c: callvirt instance uint16 Philips.PSG.Gateway.Config::get_ConstellationTs()
		IL_0041: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::constellation
		IL_0046: ldloca.s 0
		IL_0048: ldarg.0
		IL_0049: callvirt instance uint16 Philips.PSG.Gateway.Config::get_CodeRateTs()
		IL_004e: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::code_rate
		IL_0053: ldloca.s 0
		IL_0055: ldarg.0
		IL_0056: callvirt instance uint16 Philips.PSG.Gateway.Config::get_GuardIntervalTs()
		IL_005b: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::guard_interval
		IL_0060: ldloca.s 0
		IL_0062: ldarg.0
		IL_0063: callvirt instance uint16 Philips.PSG.Gateway.Config::get_TransmissionModeTs()
		IL_0068: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::transmission_mode
		IL_006d: ldloca.s 0
		IL_006f: ldarg.0
		IL_0070: callvirt instance string Philips.PSG.Gateway.Config::get_ProgNum()
		IL_0075: ldc.i4 515
		IL_007a: call uint16 [mscorlib]System.UInt16::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
		IL_007f: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::prognum
		IL_0084: ldloca.s 0
		IL_0086: ldarg.0
		IL_0087: callvirt instance string Philips.PSG.Gateway.Config::get_Pid()
		IL_008c: ldc.i4 515
		IL_0091: call uint16 [mscorlib]System.UInt16::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
		IL_0096: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::pid
		IL_009b: ldloca.s 0
		IL_009d: ldarg.0
		IL_009e: callvirt instance string Philips.PSG.Gateway.Config::get_Oui()
		IL_00a3: ldc.i4 515
		IL_00a8: call uint32 [mscorlib]System.UInt32::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
		IL_00ad: stfld uint32 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::oui
		IL_00b2: ldloca.s 0
		IL_00b4: ldarg.0
		IL_00b5: callvirt instance string Philips.PSG.Gateway.Config::get_TxnId()
		IL_00ba: ldc.i4 515
		IL_00bf: call uint32 [mscorlib]System.UInt32::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
		IL_00c4: stfld uint32 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::txnid
		IL_00c9: ldloca.s 0
		IL_00cb: ldarg.0
		IL_00cc: callvirt instance string Philips.PSG.Gateway.Config::get_DwnldId()
		IL_00d1: ldc.i4 515
		IL_00d6: call uint32 [mscorlib]System.UInt32::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
		IL_00db: stfld uint32 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::dwnldid
		IL_00e0: ldloca.s 0
		IL_00e2: ldarg.0
		IL_00e3: callvirt instance string Philips.PSG.Gateway.Config::get_NetworkId()
		IL_00e8: ldc.i4 515
		IL_00ed: call uint16 [mscorlib]System.UInt16::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
		IL_00f2: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::network_id
		IL_00f7: ldloca.s 0
		IL_00f9: ldarg.0
		IL_00fa: callvirt instance string Philips.PSG.Gateway.Config::get_TransportStreamId()
		IL_00ff: ldc.i4 515
		IL_0104: call uint16 [mscorlib]System.UInt16::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
		IL_0109: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::transport_stream_id
		IL_010e: ldloca.s 0
		IL_0110: ldarg.0
		IL_0111: callvirt instance string Philips.PSG.Gateway.Config::get_PcrPid()
		IL_0116: ldc.i4 515
		IL_011b: call uint16 [mscorlib]System.UInt16::Parse(string, valuetype [mscorlib]System.Globalization.NumberStyles)
		IL_0120: stfld uint16 Philips.PSG.Gateway.TSGen/TS_NetworkDetails::pcrpid
		IL_0125: ldloc.0
		IL_0126: ret
	} // end of method MainForm::GetNetworkDetails

	.method private hidebysig 
		instance bool ProcessMergeBuildAction (
			class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails> allTsFileDetails
		) cil managed 
	{
		// Method begins at RVA 0x6f10
		// Header size: 12
		// Code size: 197 (0xc5)
		.maxstack 5
		.locals init (
			[0] bool,
			[1] string,
			[2] valuetype Philips.PSG.Gateway.TSGen/TS_NetworkDetails,
			[3] valuetype Philips.PSG.Gateway.TSGen/TS_Result,
			[4] class [mscorlib]System.Exception
		)

		IL_0000: ldstr "MainForm: processing merge build action"
		IL_0005: call void [mscorlib]System.Console::WriteLine(string)
		IL_000a: ldc.i4.0
		IL_000b: stloc.0
		IL_000c: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0011: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0016: callvirt instance string Philips.PSG.Gateway.Config::get_OutputDataFolder()
		IL_001b: ldstr "\\"
		IL_0020: call string Philips.PSG.Gateway.Settings::get_MultiplexedTsFileName()
		IL_0025: call string [mscorlib]System.String::Concat(string, string, string)
		IL_002a: stloc.1
		IL_002b: call valuetype Philips.PSG.Gateway.TSGen/TS_NetworkDetails Philips.PSG.Gateway.MainForm::GetNetworkDetails(class Philips.PSG.Gateway.Config)
		IL_0030: stloc.2
		.try
		{
			IL_0031: ldarg.1
			IL_0032: brfalse.s IL_005f

			IL_0034: ldarg.1
			IL_0035: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::get_Count()
			IL_003a: ldc.i4.0
			IL_003b: ble.s IL_005f

			IL_003d: ldarg.0
			IL_003e: ldftn instance void Philips.PSG.Gateway.MainForm::GenerateBuildProgressCB(valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage)
			IL_0044: newobj instance void Philips.PSG.Gateway.TSGen/TS_ProgressCallback::.ctor(object, native int)
			IL_0049: ldloc.1
			IL_004a: ldarg.1
			IL_004b: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::ToArray()
			IL_0050: ldarg.1
			IL_0051: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::get_Count()
			IL_0056: ldloc.2
			IL_0057: call valuetype Philips.PSG.Gateway.TSGen/TS_Result Philips.PSG.Gateway.TSGen::TS_Merge(class Philips.PSG.Gateway.TSGen/TS_ProgressCallback, string, valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails[], int32, valuetype Philips.PSG.Gateway.TSGen/TS_NetworkDetails)
			IL_005c: stloc.3
			IL_005d: br.s IL_0061

			IL_005f: ldc.i4.1
			IL_0060: stloc.3

			IL_0061: ldloc.3
			IL_0062: brtrue.s IL_0084

			IL_0064: ldarg.0
			IL_0065: ldstr "Generated merged stream saved to {0}"
			IL_006a: ldloc.1
			IL_006b: call string [mscorlib]System.String::Format(string, object)
			IL_0070: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_0075: ldarg.0
			IL_0076: ldstr "Build action completed"
			IL_007b: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_0080: ldc.i4.1
			IL_0081: stloc.0
			IL_0082: br.s IL_009a

			IL_0084: ldarg.0
			IL_0085: ldstr "Build action failed with {0}"
			IL_008a: ldloc.3
			IL_008b: box Philips.PSG.Gateway.TSGen/TS_Result
			IL_0090: call string [mscorlib]System.String::Format(string, object)
			IL_0095: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)

			IL_009a: leave.s IL_00c3
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_009c: stloc.s 4
			IL_009e: ldstr "MainForm: Error - build action failed: {0}"
			IL_00a3: ldloc.s 4
			IL_00a5: call void [mscorlib]System.Console::WriteLine(string, object)
			IL_00aa: ldarg.0
			IL_00ab: ldstr "Build action failed with exception: {0}"
			IL_00b0: ldloc.s 4
			IL_00b2: callvirt instance string [mscorlib]System.Exception::get_Message()
			IL_00b7: call string [mscorlib]System.String::Format(string, object)
			IL_00bc: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_00c1: leave.s IL_00c3
		} // end handler

		IL_00c3: ldloc.0
		IL_00c4: ret
	} // end of method MainForm::ProcessMergeBuildAction

	.method private hidebysig 
		instance bool PrepareBuildOutputFolders () cil managed 
	{
		// Method begins at RVA 0x6ff4
		// Header size: 12
		// Code size: 146 (0x92)
		.maxstack 3
		.locals init (
			[0] string,
			[1] string,
			[2] class [mscorlib]System.Exception,
			[3] bool
		)

		IL_0000: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0005: dup
		IL_0006: callvirt instance string Philips.PSG.Gateway.Config::get_PreviousOutputDataFolder()
		IL_000b: stloc.0
		IL_000c: callvirt instance string Philips.PSG.Gateway.Config::get_OutputDataFolder()
		IL_0011: stloc.1
		IL_0012: ldarg.0
		IL_0013: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
		IL_0018: call string Philips.PSG.Gateway.Resources::get_DLG_BuildActionPreparingFoldersMessage()
		IL_001d: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetAction(string)
		.try
		{
			IL_0022: ldloc.0
			IL_0023: call bool [mscorlib]System.IO.Directory::Exists(string)
			IL_0028: brfalse.s IL_0031

			IL_002a: ldloc.0
			IL_002b: ldc.i4.1
			IL_002c: call void [mscorlib]System.IO.Directory::Delete(string, bool)

			IL_0031: ldloc.1
			IL_0032: call bool [mscorlib]System.IO.Directory::Exists(string)
			IL_0037: brfalse.s IL_0040

			IL_0039: ldloc.1
			IL_003a: ldc.i4.1
			IL_003b: call void [mscorlib]System.IO.Directory::Delete(string, bool)

			IL_0040: ldstr "MainForm: creating new output folder\n          "
			IL_0045: ldloc.1
			IL_0046: call string [mscorlib]System.String::Concat(string, string)
			IL_004b: call void [mscorlib]System.Console::WriteLine(string)
			IL_0050: ldc.i4 500
			IL_0055: call void [mscorlib]System.Threading.Thread::Sleep(int32)
			IL_005a: ldloc.1
			IL_005b: call class [mscorlib]System.IO.DirectoryInfo [mscorlib]System.IO.Directory::CreateDirectory(string)
			IL_0060: pop
			IL_0061: ldc.i4 500
			IL_0066: call void [mscorlib]System.Threading.Thread::Sleep(int32)
			IL_006b: leave.s IL_008e
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_006d: stloc.2
			IL_006e: ldstr "MainForm: build output folder preparation failed with exception: "
			IL_0073: ldloc.2
			IL_0074: dup
			IL_0075: brtrue.s IL_007b

			IL_0077: pop
			IL_0078: ldnull
			IL_0079: br.s IL_0080

			IL_007b: callvirt instance string [mscorlib]System.Object::ToString()

			IL_0080: call string [mscorlib]System.String::Concat(string, string)
			IL_0085: call void [mscorlib]System.Console::WriteLine(string)
			IL_008a: ldc.i4.0
			IL_008b: stloc.3
			IL_008c: leave.s IL_0090
		} // end handler

		IL_008e: ldc.i4.1
		IL_008f: ret

		IL_0090: ldloc.3
		IL_0091: ret
	} // end of method MainForm::PrepareBuildOutputFolders

	.method private hidebysig 
		instance void OpenBuildLog () cil managed 
	{
		// Method begins at RVA 0x70a4
		// Header size: 12
		// Code size: 82 (0x52)
		.maxstack 3
		.locals init (
			[0] class [mscorlib]System.Exception
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.IO.StreamWriter Philips.PSG.Gateway.MainForm::_buildLog
		IL_0006: brfalse.s IL_0013

		IL_0008: ldstr "MainForm: Error - trying to open a build log when one is already in use"
		IL_000d: call void [mscorlib]System.Console::WriteLine(string)
		IL_0012: ret

		IL_0013: nop
		.try
		{
			IL_0014: ldarg.0
			IL_0015: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
			IL_001a: callvirt instance string Philips.PSG.Gateway.Config::get_BuildLogFile()
			IL_001f: call class [mscorlib]System.IO.StreamWriter [mscorlib]System.IO.File::CreateText(string)
			IL_0024: stfld class [mscorlib]System.IO.StreamWriter Philips.PSG.Gateway.MainForm::_buildLog
			IL_0029: leave.s IL_0051
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_002b: stloc.0
			IL_002c: ldstr "MainForm: Error - failed to open build log file: "
			IL_0031: ldloc.0
			IL_0032: dup
			IL_0033: brtrue.s IL_0039

			IL_0035: pop
			IL_0036: ldnull
			IL_0037: br.s IL_003e

			IL_0039: callvirt instance string [mscorlib]System.Object::ToString()

			IL_003e: call string [mscorlib]System.String::Concat(string, string)
			IL_0043: call void [mscorlib]System.Console::WriteLine(string)
			IL_0048: ldarg.0
			IL_0049: ldnull
			IL_004a: stfld class [mscorlib]System.IO.StreamWriter Philips.PSG.Gateway.MainForm::_buildLog
			IL_004f: leave.s IL_0051
		} // end handler

		IL_0051: ret
	} // end of method MainForm::OpenBuildLog

	.method public hidebysig 
		instance void AddToBuildLog (
			string text
		) cil managed 
	{
		// Method begins at RVA 0x7114
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.IO.StreamWriter Philips.PSG.Gateway.MainForm::_buildLog
		IL_0006: brfalse.s IL_0014

		IL_0008: ldarg.0
		IL_0009: ldfld class [mscorlib]System.IO.StreamWriter Philips.PSG.Gateway.MainForm::_buildLog
		IL_000e: ldarg.1
		IL_000f: callvirt instance void [mscorlib]System.IO.TextWriter::WriteLine(string)

		IL_0014: ret
	} // end of method MainForm::AddToBuildLog

	.method private hidebysig 
		instance void CloseBuildLog () cil managed 
	{
		// Method begins at RVA 0x712a
		// Header size: 1
		// Code size: 27 (0x1b)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.IO.StreamWriter Philips.PSG.Gateway.MainForm::_buildLog
		IL_0006: brfalse.s IL_001a

		IL_0008: ldarg.0
		IL_0009: ldfld class [mscorlib]System.IO.StreamWriter Philips.PSG.Gateway.MainForm::_buildLog
		IL_000e: callvirt instance void [mscorlib]System.IO.TextWriter::Close()
		IL_0013: ldarg.0
		IL_0014: ldnull
		IL_0015: stfld class [mscorlib]System.IO.StreamWriter Philips.PSG.Gateway.MainForm::_buildLog

		IL_001a: ret
	} // end of method MainForm::CloseBuildLog

	.method private hidebysig static 
		void DeleteFiles (
			string directoryPath,
			string extention
		) cil managed 
	{
		// Method begins at RVA 0x7148
		// Header size: 12
		// Code size: 31 (0x1f)
		.maxstack 2
		.locals init (
			[0] string[],
			[1] int32
		)

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: call string[] [mscorlib]System.IO.Directory::GetFiles(string, string)
		IL_0007: stloc.0
		IL_0008: ldc.i4.0
		IL_0009: stloc.1
		IL_000a: br.s IL_0018
		// loop start (head: IL_0018)
			IL_000c: ldloc.0
			IL_000d: ldloc.1
			IL_000e: ldelem.ref
			IL_000f: call void [mscorlib]System.IO.File::Delete(string)
			IL_0014: ldloc.1
			IL_0015: ldc.i4.1
			IL_0016: add
			IL_0017: stloc.1

			IL_0018: ldloc.1
			IL_0019: ldloc.0
			IL_001a: ldlen
			IL_001b: conv.i4
			IL_001c: blt.s IL_000c
		// end loop

		IL_001e: ret
	} // end of method MainForm::DeleteFiles

	.method private hidebysig 
		instance bool ProcessBuild () cil managed 
	{
		// Method begins at RVA 0x7174
		// Header size: 12
		// Code size: 1137 (0x471)
		.maxstack 4
		.locals init (
			[0] class Philips.PSG.Gateway.CatalogEntry[],
			[1] bool,
			[2] class Philips.PSG.Gateway.Config,
			[3] string,
			[4] string,
			[5] string,
			[6] class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>,
			[7] string,
			[8] class Philips.PSG.Gateway.CatalogEntry[],
			[9] int32,
			[10] class Philips.PSG.Gateway.CatalogEntry,
			[11] valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction,
			[12] class Philips.PSG.Gateway.FileSet[],
			[13] int32,
			[14] class Philips.PSG.Gateway.FileSet,
			[15] valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction,
			[16] valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails,
			[17] class [mscorlib]System.Exception
		)

		IL_0000: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0005: stloc.2
		IL_0006: ldloc.2
		IL_0007: callvirt instance string Philips.PSG.Gateway.Config::get_BuildRootFolder()
		IL_000c: stloc.3
		IL_000d: ldsfld string [mscorlib]System.String::Empty
		IL_0012: stloc.s 4
		IL_0014: ldstr "MainForm: starting build process with root folder "
		IL_0019: ldloc.3
		IL_001a: call string [mscorlib]System.String::Concat(string, string)
		IL_001f: call void [mscorlib]System.Console::WriteLine(string)
		IL_0024: ldsfld string [mscorlib]System.String::Empty
		IL_0029: stloc.s 5
		IL_002b: newobj instance void class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::.ctor()
		IL_0030: stloc.s 6
		IL_0032: ldc.i4.0
		IL_0033: stloc.1
		IL_0034: ldsfld string [mscorlib]System.String::Empty
		IL_0039: stloc.s 7
		.try
		{
			.try
			{
				IL_003b: ldarg.0
				IL_003c: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
				IL_0041: callvirt instance void Philips.PSG.Gateway.Data.TsPlayer::Pause()
				IL_0046: ldarg.0
				IL_0047: call instance void Philips.PSG.Gateway.MainForm::OpenBuildLog()
				IL_004c: ldarg.0
				IL_004d: ldstr "============================================================"
				IL_0052: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_0057: ldarg.0
				IL_0058: ldstr "{0}, Version {1}"
				IL_005d: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
				IL_0062: call class [mscorlib]System.Reflection.Assembly [mscorlib]System.Reflection.Assembly::GetExecutingAssembly()
				IL_0067: callvirt instance class [mscorlib]System.Reflection.AssemblyName [mscorlib]System.Reflection.Assembly::GetName()
				IL_006c: callvirt instance class [mscorlib]System.Version [mscorlib]System.Reflection.AssemblyName::get_Version()
				IL_0071: call string [mscorlib]System.String::Format(string, object, object)
				IL_0076: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_007b: ldarg.0
				IL_007c: ldstr "============================================================"
				IL_0081: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_0086: ldarg.0
				IL_0087: ldsfld string [mscorlib]System.String::Empty
				IL_008c: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_0091: ldarg.0
				IL_0092: ldstr "Build started at {0}"
				IL_0097: call valuetype [mscorlib]System.DateTime [mscorlib]System.DateTime::get_Now()
				IL_009c: box [mscorlib]System.DateTime
				IL_00a1: call string [mscorlib]System.String::Format(string, object)
				IL_00a6: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_00ab: ldarg.0
				IL_00ac: ldstr "Input folder is {0}"
				IL_00b1: ldloc.3
				IL_00b2: call string [mscorlib]System.String::Format(string, object)
				IL_00b7: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_00bc: ldarg.0
				IL_00bd: ldstr "Output folder is {0}"
				IL_00c2: ldloc.2
				IL_00c3: callvirt instance string Philips.PSG.Gateway.Config::get_ScheduleDataFolder()
				IL_00c8: call string [mscorlib]System.String::Format(string, object)
				IL_00cd: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_00d2: ldarg.0
				IL_00d3: ldfld class Philips.PSG.Gateway.Catalog Philips.PSG.Gateway.MainForm::_catalog
				IL_00d8: ldloc.3
				IL_00d9: ldarg.0
				IL_00da: ldfld class [mscorlib]System.IO.StreamWriter Philips.PSG.Gateway.MainForm::_buildLog
				IL_00df: callvirt instance class Philips.PSG.Gateway.CatalogEntry[] Philips.PSG.Gateway.Catalog::GetValidCandidates(string, class [mscorlib]System.IO.StreamWriter)
				IL_00e4: stloc.0
				IL_00e5: ldarg.0
				IL_00e6: ldsfld string [mscorlib]System.String::Empty
				IL_00eb: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_00f0: ldarg.0
				IL_00f1: ldstr "{0} candidate(s) to build"
				IL_00f6: ldloc.0
				IL_00f7: call int32 [System.Core]System.Linq.Enumerable::Count<class Philips.PSG.Gateway.CatalogEntry>(class [mscorlib]System.Collections.Generic.IEnumerable`1<!!0>)
				IL_00fc: box [mscorlib]System.Int32
				IL_0101: call string [mscorlib]System.String::Format(string, object)
				IL_0106: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_010b: ldloc.0
				IL_010c: brfalse IL_03ec

				IL_0111: ldloc.0
				IL_0112: call int32 [System.Core]System.Linq.Enumerable::Count<class Philips.PSG.Gateway.CatalogEntry>(class [mscorlib]System.Collections.Generic.IEnumerable`1<!!0>)
				IL_0117: ldc.i4.0
				IL_0118: ble IL_03ec

				IL_011d: call string [mscorlib]System.Environment::get_CurrentDirectory()
				IL_0122: ldstr "*.dat"
				IL_0127: call void Philips.PSG.Gateway.MainForm::DeleteFiles(string, string)
				IL_012c: ldloc.0
				IL_012d: stloc.s 8
				IL_012f: ldc.i4.0
				IL_0130: stloc.s 9
				IL_0132: br IL_03be
				// loop start (head: IL_03be)
					IL_0137: ldloc.s 8
					IL_0139: ldloc.s 9
					IL_013b: ldelem.ref
					IL_013c: stloc.s 10
					IL_013e: ldstr "MainForm: building for "
					IL_0143: ldloc.s 10
					IL_0145: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_TargetTvModel()
					IL_014a: call string [mscorlib]System.String::Concat(string, string)
					IL_014f: call void [mscorlib]System.Console::WriteLine(string)
					IL_0154: ldarg.0
					IL_0155: ldsfld string [mscorlib]System.String::Empty
					IL_015a: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
					IL_015f: ldarg.0
					IL_0160: ldstr "Building for {0}"
					IL_0165: ldloc.s 10
					IL_0167: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_TargetTvModel()
					IL_016c: call string [mscorlib]System.String::Format(string, object)
					IL_0171: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
					IL_0176: ldarg.0
					IL_0177: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
					IL_017c: call string Philips.PSG.Gateway.Resources::get_DLG_BuildTargetTvModelMessage()
					IL_0181: ldloc.s 10
					IL_0183: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_TargetTvModel()
					IL_0188: call string [mscorlib]System.String::Format(string, object)
					IL_018d: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetTvModel(string)
					IL_0192: ldarg.0
					IL_0193: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
					IL_0198: call string Philips.PSG.Gateway.Resources::get_DLG_BuildActionStarting()
					IL_019d: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetAction(string)
					IL_01a2: ldloc.s 7
					IL_01a4: ldstr "{0} "
					IL_01a9: ldloc.s 10
					IL_01ab: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_TargetTvModel()
					IL_01b0: call string [mscorlib]System.String::Format(string, object)
					IL_01b5: call string [mscorlib]System.String::Concat(string, string)
					IL_01ba: stloc.s 7
					IL_01bc: ldloc.3
					IL_01bd: ldstr "\\"
					IL_01c2: ldloc.s 10
					IL_01c4: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_BaseFolder()
					IL_01c9: call string [mscorlib]System.String::Concat(string, string, string)
					IL_01ce: stloc.s 5
					IL_01d0: ldloc.s 10
					IL_01d2: callvirt instance valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Philips.PSG.Gateway.CatalogEntry::get_Action()
					IL_01d7: stloc.s 11
					IL_01d9: ldloc.s 11
					IL_01db: ldc.i4.3
					IL_01dc: beq.s IL_020a

					IL_01de: ldloc.s 11
					IL_01e0: ldc.i4.4
					IL_01e1: bne.un IL_0268

					IL_01e6: ldarg.0
					IL_01e7: ldloc.3
					IL_01e8: ldloc.s 10
					IL_01ea: call instance bool Philips.PSG.Gateway.MainForm::ProcessGenerateBuildAction(string, class Philips.PSG.Gateway.CatalogEntry)
					IL_01ef: stloc.1
					IL_01f0: ldloc.s 5
					IL_01f2: ldstr "\\"
					IL_01f7: ldloc.s 10
					IL_01f9: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_OutputFileName()
					IL_01fe: call string [mscorlib]System.String::Concat(string, string, string)
					IL_0203: stloc.s 4
					IL_0205: br IL_02ab

					IL_020a: ldarg.0
					IL_020b: ldstr "TV Model {0} requires {1} action"
					IL_0210: ldloc.s 10
					IL_0212: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_TargetTvModel()
					IL_0217: ldloc.s 10
					IL_0219: callvirt instance valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Philips.PSG.Gateway.CatalogEntry::get_Action()
					IL_021e: box Philips.PSG.Gateway.CatalogEntry/BuildAction
					IL_0223: call string [mscorlib]System.String::Format(string, object, object)
					IL_0228: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
					IL_022d: ldloc.s 10
					IL_022f: callvirt instance class Philips.PSG.Gateway.FileSet[] Philips.PSG.Gateway.CatalogEntry::get_FileSets()
					IL_0234: stloc.s 12
					IL_0236: ldc.i4.0
					IL_0237: stloc.s 13
					IL_0239: br.s IL_025c
					// loop start (head: IL_025c)
						IL_023b: ldloc.s 12
						IL_023d: ldloc.s 13
						IL_023f: ldelem.ref
						IL_0240: stloc.s 14
						IL_0242: ldloc.s 14
						IL_0244: callvirt instance bool Philips.PSG.Gateway.FileSet::get_IsValid()
						IL_0249: brfalse.s IL_0256

						IL_024b: ldloc.s 14
						IL_024d: ldloc.s 5
						IL_024f: callvirt instance string Philips.PSG.Gateway.FileSet::PrepareUsageArgument(string)
						IL_0254: stloc.s 4

						IL_0256: ldloc.s 13
						IL_0258: ldc.i4.1
						IL_0259: add
						IL_025a: stloc.s 13

						IL_025c: ldloc.s 13
						IL_025e: ldloc.s 12
						IL_0260: ldlen
						IL_0261: conv.i4
						IL_0262: blt.s IL_023b
					// end loop

					IL_0264: ldc.i4.1
					IL_0265: stloc.1
					IL_0266: br.s IL_02ab

					IL_0268: ldstr "MainForm: Error - unhandled build action "
					IL_026d: ldloc.s 10
					IL_026f: callvirt instance valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Philips.PSG.Gateway.CatalogEntry::get_Action()
					IL_0274: stloc.s 15
					IL_0276: ldloca.s 15
					IL_0278: constrained. Philips.PSG.Gateway.CatalogEntry/BuildAction
					IL_027e: callvirt instance string [mscorlib]System.Object::ToString()
					IL_0283: call string [mscorlib]System.String::Concat(string, string)
					IL_0288: call void [mscorlib]System.Console::WriteLine(string)
					IL_028d: ldarg.0
					IL_028e: ldstr "Unhandled build action with value {0}"
					IL_0293: ldloc.s 10
					IL_0295: callvirt instance valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction Philips.PSG.Gateway.CatalogEntry::get_Action()
					IL_029a: box Philips.PSG.Gateway.CatalogEntry/BuildAction
					IL_029f: call string [mscorlib]System.String::Format(string, object)
					IL_02a4: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
					IL_02a9: ldc.i4.0
					IL_02aa: stloc.1

					IL_02ab: ldloc.1
					IL_02ac: brfalse IL_03b8

					IL_02b1: ldarg.0
					IL_02b2: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
					IL_02b7: ldloc.s 4
					IL_02b9: callvirt instance bool Philips.PSG.Gateway.Data.TsPlayer::IsValidTsFile(string)
					IL_02be: brfalse IL_039d

					IL_02c3: ldloc.s 4
					IL_02c5: ldstr ".ts"
					IL_02ca: callvirt instance bool [mscorlib]System.String::Contains(string)
					IL_02cf: brfalse.s IL_02f8

					IL_02d1: ldloc.s 6
					IL_02d3: ldloca.s 16
					IL_02d5: initobj Philips.PSG.Gateway.TSGen/TS_FileDetails
					IL_02db: ldloca.s 16
					IL_02dd: ldloc.s 4
					IL_02df: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::filePath
					IL_02e4: ldloca.s 16
					IL_02e6: ldc.i4.2
					IL_02e7: stfld valuetype Philips.PSG.Gateway.TSGen/TS_FileType Philips.PSG.Gateway.TSGen/TS_FileDetails::fileType
					IL_02ec: ldloc.s 16
					IL_02ee: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::Add(!0)
					IL_02f3: br IL_03b8

					IL_02f8: ldloc.s 4
					IL_02fa: ldstr "Cloning.upg"
					IL_02ff: callvirt instance bool [mscorlib]System.String::Contains(string)
					IL_0304: brfalse.s IL_032d

					IL_0306: ldloc.s 6
					IL_0308: ldloca.s 16
					IL_030a: initobj Philips.PSG.Gateway.TSGen/TS_FileDetails
					IL_0310: ldloca.s 16
					IL_0312: ldloc.s 4
					IL_0314: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::filePath
					IL_0319: ldloca.s 16
					IL_031b: ldc.i4.0
					IL_031c: stfld valuetype Philips.PSG.Gateway.TSGen/TS_FileType Philips.PSG.Gateway.TSGen/TS_FileDetails::fileType
					IL_0321: ldloc.s 16
					IL_0323: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::Add(!0)
					IL_0328: br IL_03b8

					IL_032d: ldloc.s 4
					IL_032f: ldstr "run.upg"
					IL_0334: callvirt instance bool [mscorlib]System.String::Contains(string)
					IL_0339: brfalse.s IL_035f

					IL_033b: ldloc.s 6
					IL_033d: ldloca.s 16
					IL_033f: initobj Philips.PSG.Gateway.TSGen/TS_FileDetails
					IL_0345: ldloca.s 16
					IL_0347: ldloc.s 4
					IL_0349: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::filePath
					IL_034e: ldloca.s 16
					IL_0350: ldc.i4.1
					IL_0351: stfld valuetype Philips.PSG.Gateway.TSGen/TS_FileType Philips.PSG.Gateway.TSGen/TS_FileDetails::fileType
					IL_0356: ldloc.s 16
					IL_0358: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::Add(!0)
					IL_035d: br.s IL_03b8

					IL_035f: ldloc.s 4
					IL_0361: ldstr ".trp"
					IL_0366: callvirt instance bool [mscorlib]System.String::Contains(string)
					IL_036b: brfalse.s IL_0391

					IL_036d: ldloc.s 6
					IL_036f: ldloca.s 16
					IL_0371: initobj Philips.PSG.Gateway.TSGen/TS_FileDetails
					IL_0377: ldloca.s 16
					IL_0379: ldloc.s 4
					IL_037b: stfld string Philips.PSG.Gateway.TSGen/TS_FileDetails::filePath
					IL_0380: ldloca.s 16
					IL_0382: ldc.i4.2
					IL_0383: stfld valuetype Philips.PSG.Gateway.TSGen/TS_FileType Philips.PSG.Gateway.TSGen/TS_FileDetails::fileType
					IL_0388: ldloc.s 16
					IL_038a: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>::Add(!0)
					IL_038f: br.s IL_03b8

					IL_0391: ldstr "Unknown upg or trp or ts file"
					IL_0396: call void [mscorlib]System.Console::WriteLine(string)
					IL_039b: br.s IL_03b8

					IL_039d: ldarg.0
					IL_039e: ldstr "Invalid TS file found {0} for TV modle {1}"
					IL_03a3: ldloc.s 4
					IL_03a5: ldloc.s 10
					IL_03a7: callvirt instance string Philips.PSG.Gateway.CatalogEntry::get_TargetTvModel()
					IL_03ac: call string [mscorlib]System.String::Format(string, object, object)
					IL_03b1: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
					IL_03b6: ldc.i4.0
					IL_03b7: stloc.1

					IL_03b8: ldloc.s 9
					IL_03ba: ldc.i4.1
					IL_03bb: add
					IL_03bc: stloc.s 9

					IL_03be: ldloc.s 9
					IL_03c0: ldloc.s 8
					IL_03c2: ldlen
					IL_03c3: conv.i4
					IL_03c4: blt IL_0137
				// end loop

				IL_03c9: ldloc.1
				IL_03ca: brfalse.s IL_03ec

				IL_03cc: ldarg.0
				IL_03cd: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
				IL_03d2: call string Philips.PSG.Gateway.Resources::get_DLG_BuildTargetTvModelMessage()
				IL_03d7: ldloc.s 7
				IL_03d9: call string [mscorlib]System.String::Format(string, object)
				IL_03de: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetTvModel(string)
				IL_03e3: ldarg.0
				IL_03e4: ldloc.s 6
				IL_03e6: call instance bool Philips.PSG.Gateway.MainForm::ProcessMergeBuildAction(class [mscorlib]System.Collections.Generic.List`1<valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails>)
				IL_03eb: stloc.1

				IL_03ec: ldarg.0
				IL_03ed: call instance void Philips.PSG.Gateway.MainForm::UpdateControls()
				IL_03f2: leave.s IL_046f
			} // end .try
			catch [mscorlib]System.Exception
			{
				IL_03f4: stloc.s 17
				IL_03f6: ldstr "MainForm: Error - an exception occurred during the build:  "
				IL_03fb: ldloc.s 17
				IL_03fd: dup
				IL_03fe: brtrue.s IL_0404

				IL_0400: pop
				IL_0401: ldnull
				IL_0402: br.s IL_0409

				IL_0404: callvirt instance string [mscorlib]System.Object::ToString()

				IL_0409: call string [mscorlib]System.String::Concat(string, string)
				IL_040e: call void [mscorlib]System.Console::WriteLine(string)
				IL_0413: ldarg.0
				IL_0414: ldstr "Exception during build: {0}"
				IL_0419: ldloc.s 17
				IL_041b: callvirt instance string [mscorlib]System.Exception::get_Message()
				IL_0420: call string [mscorlib]System.String::Format(string, object)
				IL_0425: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
				IL_042a: ldc.i4.0
				IL_042b: stloc.1
				IL_042c: leave.s IL_046f
			} // end handler
		} // end .try
		finally
		{
			IL_042e: ldarg.0
			IL_042f: ldsfld string [mscorlib]System.String::Empty
			IL_0434: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_0439: ldarg.0
			IL_043a: ldloc.1
			IL_043b: brtrue.s IL_0444

			IL_043d: ldstr "Build failed"
			IL_0442: br.s IL_0449

			IL_0444: ldstr "Build successful"

			IL_0449: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_044e: ldarg.0
			IL_044f: ldstr "Build finished at {0}"
			IL_0454: call valuetype [mscorlib]System.DateTime [mscorlib]System.DateTime::get_Now()
			IL_0459: box [mscorlib]System.DateTime
			IL_045e: call string [mscorlib]System.String::Format(string, object)
			IL_0463: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_0468: ldarg.0
			IL_0469: call instance void Philips.PSG.Gateway.MainForm::CloseBuildLog()
			IL_046e: endfinally
		} // end handler

		IL_046f: ldloc.1
		IL_0470: ret
	} // end of method MainForm::ProcessBuild

	.method private hidebysig 
		instance void ProcessBuildWorker (
			object stateInfo
		) cil managed 
	{
		// Method begins at RVA 0x7628
		// Header size: 12
		// Code size: 124 (0x7c)
		.maxstack 3

		.try
		{
			IL_0000: ldarg.0
			IL_0001: call instance bool Philips.PSG.Gateway.MainForm::PrepareBuildOutputFolders()
			IL_0006: brtrue.s IL_0030

			IL_0008: call class [System]System.Media.SystemSound [System]System.Media.SystemSounds::get_Beep()
			IL_000d: callvirt instance void [System]System.Media.SystemSound::Play()
			IL_0012: ldarg.0
			IL_0013: call string Philips.PSG.Gateway.Resources::get_DLG_PreBuildFailedMessage()
			IL_0018: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_001d: ldarg.0
			IL_001e: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
			IL_0023: call string Philips.PSG.Gateway.Resources::get_DLG_PreBuildFailedMessage()
			IL_0028: ldc.i4.0
			IL_0029: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetResult(string, bool)
			IL_002e: br.s IL_0076

			IL_0030: ldarg.0
			IL_0031: call instance bool Philips.PSG.Gateway.MainForm::ProcessBuild()
			IL_0036: brtrue.s IL_0060

			IL_0038: call class [System]System.Media.SystemSound [System]System.Media.SystemSounds::get_Beep()
			IL_003d: callvirt instance void [System]System.Media.SystemSound::Play()
			IL_0042: ldarg.0
			IL_0043: call string Philips.PSG.Gateway.Resources::get_DLG_BuildFailedMessage()
			IL_0048: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_004d: ldarg.0
			IL_004e: ldfld class Philips.PSG.Gateway.BuildProgressDialog Philips.PSG.Gateway.MainForm::_buildProgressDialog
			IL_0053: call string Philips.PSG.Gateway.Resources::get_DLG_BuildFailedMessage()
			IL_0058: ldc.i4.1
			IL_0059: callvirt instance void Philips.PSG.Gateway.BuildProgressDialog::SetResult(string, bool)
			IL_005e: br.s IL_0076

			IL_0060: ldarg.0
			IL_0061: call string Philips.PSG.Gateway.Resources::get_DLG_BuildSuccessfulMessage()
			IL_0066: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
			IL_006b: ldarg.0
			IL_006c: ldstr "Build Log is placed in the Outout folder"
			IL_0071: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)

			IL_0076: leave.s IL_007b
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_0078: pop
			IL_0079: leave.s IL_007b
		} // end handler

		IL_007b: ret
	} // end of method MainForm::ProcessBuildWorker

	.method private hidebysig 
		instance void CleanUpOnExit () cil managed 
	{
		// Method begins at RVA 0x76c0
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_0006: callvirt instance void Philips.PSG.Gateway.Data.TsPlayer::Pause()
		IL_000b: ldarg.0
		IL_000c: ldfld class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.MainForm::_dekTecManager
		IL_0011: callvirt instance void Philips.PSG.Gateway.DekTecManager::Dispose()
		IL_0016: ret
	} // end of method MainForm::CleanUpOnExit

	.method private hidebysig 
		instance void exitToolStripMenuItem_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x3a83
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Form::Close()
		IL_0006: ret
	} // end of method MainForm::exitToolStripMenuItem_Click

	.method private hidebysig 
		instance void MainForm_FormClosing (
			object sender,
			class [System.Windows.Forms]System.Windows.Forms.FormClosingEventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x76d8
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void Philips.PSG.Gateway.MainForm::CleanUpOnExit()
		IL_0006: ret
	} // end of method MainForm::MainForm_FormClosing

	.method private hidebysig 
		instance void aboutToolStripMenuItem_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x76e0
		// Header size: 1
		// Code size: 12 (0xc)
		.maxstack 8

		IL_0000: newobj instance void Philips.PSG.Gateway.AboutBox::.ctor()
		IL_0005: ldarg.0
		IL_0006: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Form::Show(class [System.Windows.Forms]System.Windows.Forms.IWin32Window)
		IL_000b: ret
	} // end of method MainForm::aboutToolStripMenuItem_Click

	.method private hidebysig 
		instance void configurationToolStripMenuItem_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x76f0
		// Header size: 12
		// Code size: 35 (0x23)
		.maxstack 3
		.locals init (
			[0] class Philips.PSG.Gateway.Config
		)

		IL_0000: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0005: stloc.0
		IL_0006: ldloc.0
		IL_0007: callvirt instance string Philips.PSG.Gateway.Config::get_WorkingRootFolder()
		IL_000c: pop
		IL_000d: ldarg.0
		IL_000e: ldfld class Philips.PSG.Gateway.ConfigurationDialog Philips.PSG.Gateway.MainForm::_configDialog
		IL_0013: ldarg.0
		IL_0014: ldloc.0
		IL_0015: callvirt instance bool Philips.PSG.Gateway.ConfigurationDialog::ShowDialog(class [System.Windows.Forms]System.Windows.Forms.IWin32Window, class Philips.PSG.Gateway.Config)
		IL_001a: brfalse.s IL_0022

		IL_001c: ldloc.0
		IL_001d: callvirt instance void Philips.PSG.Gateway.Config::SaveToFile()

		IL_0022: ret
	} // end of method MainForm::configurationToolStripMenuItem_Click

	.method public hidebysig 
		instance void PlayPauseButton_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x7720
		// Header size: 12
		// Code size: 124 (0x7c)
		.maxstack 3
		.locals init (
			[0] valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState,
			[1] valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_0006: callvirt instance valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::get_State()
		IL_000b: stloc.0
		IL_000c: ldloc.0
		IL_000d: switch (IL_006f, IL_0020, IL_003a)

		IL_001e: br.s IL_0047

		IL_0020: ldarg.0
		IL_0021: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_0026: ldarg.0
		IL_0027: ldftn instance void Philips.PSG.Gateway.MainForm::OnErrorPlayStreamCallback(string, class [mscorlib]System.Exception)
		IL_002d: newobj instance void Philips.PSG.Gateway.PlayStreamHandlerErrored::.ctor(object, native int)
		IL_0032: callvirt instance bool Philips.PSG.Gateway.Data.TsPlayer::Play(class Philips.PSG.Gateway.PlayStreamHandlerErrored)
		IL_0037: pop
		IL_0038: br.s IL_006f

		IL_003a: ldarg.0
		IL_003b: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_0040: callvirt instance void Philips.PSG.Gateway.Data.TsPlayer::Pause()
		IL_0045: br.s IL_006f

		IL_0047: ldstr "MainForm: unhandled TsPlayer state  "
		IL_004c: ldarg.0
		IL_004d: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_0052: callvirt instance valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::get_State()
		IL_0057: stloc.1
		IL_0058: ldloca.s 1
		IL_005a: constrained. Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState
		IL_0060: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0065: call string [mscorlib]System.String::Concat(string, string)
		IL_006a: call void [mscorlib]System.Console::WriteLine(string)

		IL_006f: ldarg.0
		IL_0070: call instance void Philips.PSG.Gateway.MainForm::UpdateControls()
		IL_0075: ldarg.0
		IL_0076: call instance void Philips.PSG.Gateway.MainForm::UpdateDekTecDeviceStatusDisplay()
		IL_007b: ret
	} // end of method MainForm::PlayPauseButton_Click

	.method private hidebysig 
		instance void OnErrorPlayStreamCallback (
			string message,
			class [mscorlib]System.Exception exception
		) cil managed 
	{
		// Method begins at RVA 0x77a8
		// Header size: 12
		// Code size: 71 (0x47)
		.maxstack 4

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: ldstr " "
		IL_0007: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
		IL_000c: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0011: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0016: call instance void Philips.PSG.Gateway.MainForm::AddToBuildLog(string)
		IL_001b: ldarg.1
		IL_001c: ldstr " "
		IL_0021: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
		IL_0026: callvirt instance string [mscorlib]System.Object::ToString()
		IL_002b: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0030: call void [mscorlib]System.Console::WriteLine(string)
		IL_0035: ldarg.0
		IL_0036: ldfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.MainForm::_tsPlayer
		IL_003b: callvirt instance void Philips.PSG.Gateway.Data.TsPlayer::Pause()
		IL_0040: ldarg.0
		IL_0041: call instance void Philips.PSG.Gateway.MainForm::UpdateControls()
		IL_0046: ret
	} // end of method MainForm::OnErrorPlayStreamCallback

	.method public hidebysig 
		instance void BuildButton_Click (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x77fb
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldnull
		IL_0002: call instance void Philips.PSG.Gateway.MainForm::ProcessBuildWorker(object)
		IL_0007: ret
	} // end of method MainForm::BuildButton_Click

	.method private hidebysig 
		instance void SaveDefaultConfiguration () cil managed 
	{
		// Method begins at RVA 0x7804
		// Header size: 1
		// Code size: 11 (0xb)
		.maxstack 8

		IL_0000: newobj instance void Philips.PSG.Gateway.Config::.ctor()
		IL_0005: callvirt instance void Philips.PSG.Gateway.Config::SaveToFile()
		IL_000a: ret
	} // end of method MainForm::SaveDefaultConfiguration

	.method private hidebysig 
		instance void SaveDefaultCatalog () cil managed 
	{
		// Method begins at RVA 0x7810
		// Header size: 12
		// Code size: 1968 (0x7b0)
		.maxstack 3
		.locals init (
			[0] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>,
			[1] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>,
			[2] class [mscorlib]System.Collections.Generic.List`1<string>,
			[3] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>,
			[4] class Philips.PSG.Gateway.CatalogEntry,
			[5] class Philips.PSG.Gateway.FileSet,
			[6] class Philips.PSG.Gateway.ScheduleEvent
		)

		IL_0000: newobj instance void Philips.PSG.Gateway.Catalog::.ctor()
		IL_0005: newobj instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::.ctor()
		IL_000a: stloc.0
		IL_000b: newobj instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::.ctor()
		IL_0010: stloc.1
		IL_0011: newobj instance void class [mscorlib]System.Collections.Generic.List`1<string>::.ctor()
		IL_0016: stloc.2
		IL_0017: newobj instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::.ctor()
		IL_001c: stloc.3
		IL_001d: ldloc.0
		IL_001e: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::Clear()
		IL_0023: newobj instance void Philips.PSG.Gateway.CatalogEntry::.ctor()
		IL_0028: stloc.s 4
		IL_002a: ldloc.s 4
		IL_002c: ldstr "2k10 Prime"
		IL_0031: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_TargetTvModel(string)
		IL_0036: ldloc.s 4
		IL_0038: ldstr "2k10_Prime"
		IL_003d: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_BaseFolder(string)
		IL_0042: ldloc.s 4
		IL_0044: ldstr "2k10_Prime.ts"
		IL_0049: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_OutputFileName(string)
		IL_004e: ldloc.s 4
		IL_0050: ldc.i4.2
		IL_0051: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_Action(valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction)
		IL_0056: ldloc.0
		IL_0057: ldloc.s 4
		IL_0059: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::Add(!0)
		IL_005e: ldloc.1
		IL_005f: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Clear()
		IL_0064: newobj instance void Philips.PSG.Gateway.FileSet::.ctor()
		IL_0069: stloc.s 5
		IL_006b: ldloc.s 5
		IL_006d: ldc.i4.7
		IL_006e: callvirt instance void Philips.PSG.Gateway.FileSet::set_ID(valuetype Philips.PSG.Gateway.FileSet/FileSetID)
		IL_0073: ldloc.s 5
		IL_0075: ldc.i4.0
		IL_0076: callvirt instance void Philips.PSG.Gateway.FileSet::set_Requirement(valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement)
		IL_007b: ldloc.s 5
		IL_007d: ldc.i4.0
		IL_007e: callvirt instance void Philips.PSG.Gateway.FileSet::set_Usage(valuetype Philips.PSG.Gateway.FileSet/FileSetUsage)
		IL_0083: ldloc.2
		IL_0084: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Clear()
		IL_0089: ldloc.2
		IL_008a: ldstr "OAD_Q555H_*.upg"
		IL_008f: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0094: ldloc.s 5
		IL_0096: ldloc.2
		IL_0097: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<string>::ToArray()
		IL_009c: callvirt instance void Philips.PSG.Gateway.FileSet::set_Files(string[])
		IL_00a1: ldloc.1
		IL_00a2: ldloc.s 5
		IL_00a4: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Add(!0)
		IL_00a9: newobj instance void Philips.PSG.Gateway.FileSet::.ctor()
		IL_00ae: stloc.s 5
		IL_00b0: ldloc.s 5
		IL_00b2: ldc.i4.5
		IL_00b3: callvirt instance void Philips.PSG.Gateway.FileSet::set_ID(valuetype Philips.PSG.Gateway.FileSet/FileSetID)
		IL_00b8: ldloc.s 5
		IL_00ba: ldc.i4.0
		IL_00bb: callvirt instance void Philips.PSG.Gateway.FileSet::set_Requirement(valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement)
		IL_00c0: ldloc.s 5
		IL_00c2: ldc.i4.0
		IL_00c3: callvirt instance void Philips.PSG.Gateway.FileSet::set_Usage(valuetype Philips.PSG.Gateway.FileSet/FileSetUsage)
		IL_00c8: ldloc.2
		IL_00c9: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Clear()
		IL_00ce: ldloc.2
		IL_00cf: ldstr "vseckeys.txt"
		IL_00d4: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_00d9: ldloc.s 5
		IL_00db: ldloc.2
		IL_00dc: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<string>::ToArray()
		IL_00e1: callvirt instance void Philips.PSG.Gateway.FileSet::set_Files(string[])
		IL_00e6: ldloc.1
		IL_00e7: ldloc.s 5
		IL_00e9: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Add(!0)
		IL_00ee: newobj instance void Philips.PSG.Gateway.FileSet::.ctor()
		IL_00f3: stloc.s 5
		IL_00f5: ldloc.s 5
		IL_00f7: ldc.i4.6
		IL_00f8: callvirt instance void Philips.PSG.Gateway.FileSet::set_ID(valuetype Philips.PSG.Gateway.FileSet/FileSetID)
		IL_00fd: ldloc.s 5
		IL_00ff: ldc.i4.0
		IL_0100: callvirt instance void Philips.PSG.Gateway.FileSet::set_Requirement(valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement)
		IL_0105: ldloc.s 5
		IL_0107: ldc.i4.0
		IL_0108: callvirt instance void Philips.PSG.Gateway.FileSet::set_Usage(valuetype Philips.PSG.Gateway.FileSet/FileSetUsage)
		IL_010d: ldloc.s 5
		IL_010f: ldstr "upgrades"
		IL_0114: callvirt instance void Philips.PSG.Gateway.FileSet::set_PrefixFolder(string)
		IL_0119: ldloc.2
		IL_011a: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Clear()
		IL_011f: ldloc.2
		IL_0120: ldstr "StandbySW_Q555H_CFG62_*.upg"
		IL_0125: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_012a: ldloc.s 5
		IL_012c: ldloc.2
		IL_012d: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<string>::ToArray()
		IL_0132: callvirt instance void Philips.PSG.Gateway.FileSet::set_Files(string[])
		IL_0137: ldloc.1
		IL_0138: ldloc.s 5
		IL_013a: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Add(!0)
		IL_013f: newobj instance void Philips.PSG.Gateway.FileSet::.ctor()
		IL_0144: stloc.s 5
		IL_0146: ldloc.s 5
		IL_0148: ldc.i4.3
		IL_0149: callvirt instance void Philips.PSG.Gateway.FileSet::set_ID(valuetype Philips.PSG.Gateway.FileSet/FileSetID)
		IL_014e: ldloc.s 5
		IL_0150: ldc.i4.0
		IL_0151: callvirt instance void Philips.PSG.Gateway.FileSet::set_Requirement(valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement)
		IL_0156: ldloc.s 5
		IL_0158: ldc.i4.2
		IL_0159: callvirt instance void Philips.PSG.Gateway.FileSet::set_Usage(valuetype Philips.PSG.Gateway.FileSet/FileSetUsage)
		IL_015e: ldloc.s 5
		IL_0160: ldstr "NVM"
		IL_0165: callvirt instance void Philips.PSG.Gateway.FileSet::set_InterimFolder(string)
		IL_016a: ldloc.2
		IL_016b: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Clear()
		IL_0170: ldloc.2
		IL_0171: ldstr "BDSSettings.txt"
		IL_0176: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_017b: ldloc.2
		IL_017c: ldstr "InstallationSettings.txt"
		IL_0181: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0186: ldloc.2
		IL_0187: ldstr "LastStatus.txt"
		IL_018c: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0191: ldloc.2
		IL_0192: ldstr "smartdata"
		IL_0197: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_019c: ldloc.2
		IL_019d: ldstr "sourcelib_ActivityTable"
		IL_01a2: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_01a7: ldloc.2
		IL_01a8: ldstr "sourcelib_DevTable"
		IL_01ad: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_01b2: ldloc.2
		IL_01b3: ldstr "sourcelib_PhyExtTable"
		IL_01b8: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_01bd: ldloc.s 5
		IL_01bf: ldloc.2
		IL_01c0: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<string>::ToArray()
		IL_01c5: callvirt instance void Philips.PSG.Gateway.FileSet::set_Files(string[])
		IL_01ca: ldloc.1
		IL_01cb: ldloc.s 5
		IL_01cd: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Add(!0)
		IL_01d2: newobj instance void Philips.PSG.Gateway.FileSet::.ctor()
		IL_01d7: stloc.s 5
		IL_01d9: ldloc.s 5
		IL_01db: ldc.i4.2
		IL_01dc: callvirt instance void Philips.PSG.Gateway.FileSet::set_ID(valuetype Philips.PSG.Gateway.FileSet/FileSetID)
		IL_01e1: ldloc.s 5
		IL_01e3: ldstr "ChannelList\\channellib"
		IL_01e8: callvirt instance void Philips.PSG.Gateway.FileSet::set_PrefixFolder(string)
		IL_01ed: ldloc.s 5
		IL_01ef: ldc.i4.1
		IL_01f0: callvirt instance void Philips.PSG.Gateway.FileSet::set_Usage(valuetype Philips.PSG.Gateway.FileSet/FileSetUsage)
		IL_01f5: ldloc.s 5
		IL_01f7: ldc.i4.0
		IL_01f8: callvirt instance void Philips.PSG.Gateway.FileSet::set_Requirement(valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement)
		IL_01fd: ldloc.2
		IL_01fe: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Clear()
		IL_0203: ldloc.2
		IL_0204: ldstr "AntennaAnalogTable"
		IL_0209: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_020e: ldloc.2
		IL_020f: ldstr "AntennaDigSrvTable"
		IL_0214: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0219: ldloc.2
		IL_021a: ldstr "AntennaDigTSTable"
		IL_021f: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0224: ldloc.2
		IL_0225: ldstr "AntennaFrqMapTable"
		IL_022a: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_022f: ldloc.2
		IL_0230: ldstr "AntennaPresetTable"
		IL_0235: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_023a: ldloc.2
		IL_023b: ldstr "CableAnalogTable"
		IL_0240: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0245: ldloc.2
		IL_0246: ldstr "CableDigSrvTable"
		IL_024b: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0250: ldloc.2
		IL_0251: ldstr "CableDigTSTable"
		IL_0256: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_025b: ldloc.2
		IL_025c: ldstr "CableFrqMapTable"
		IL_0261: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0266: ldloc.2
		IL_0267: ldstr "CablePresetTable"
		IL_026c: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0271: ldloc.s 5
		IL_0273: ldloc.2
		IL_0274: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<string>::ToArray()
		IL_0279: callvirt instance void Philips.PSG.Gateway.FileSet::set_Files(string[])
		IL_027e: ldloc.1
		IL_027f: ldloc.s 5
		IL_0281: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Add(!0)
		IL_0286: newobj instance void Philips.PSG.Gateway.FileSet::.ctor()
		IL_028b: stloc.s 5
		IL_028d: ldloc.s 5
		IL_028f: ldc.i4.4
		IL_0290: callvirt instance void Philips.PSG.Gateway.FileSet::set_ID(valuetype Philips.PSG.Gateway.FileSet/FileSetID)
		IL_0295: ldloc.s 5
		IL_0297: ldstr "ThemeTV"
		IL_029c: callvirt instance void Philips.PSG.Gateway.FileSet::set_PrefixFolder(string)
		IL_02a1: ldloc.s 5
		IL_02a3: ldc.i4.1
		IL_02a4: callvirt instance void Philips.PSG.Gateway.FileSet::set_Usage(valuetype Philips.PSG.Gateway.FileSet/FileSetUsage)
		IL_02a9: ldloc.s 5
		IL_02ab: ldc.i4.0
		IL_02ac: callvirt instance void Philips.PSG.Gateway.FileSet::set_Requirement(valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement)
		IL_02b1: ldloc.2
		IL_02b2: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Clear()
		IL_02b7: ldloc.2
		IL_02b8: ldstr "background.png"
		IL_02bd: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_02c2: ldloc.2
		IL_02c3: ldstr "footer.png"
		IL_02c8: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_02cd: ldloc.2
		IL_02ce: ldstr "header.png"
		IL_02d3: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_02d8: ldloc.2
		IL_02d9: ldstr "R1.png"
		IL_02de: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_02e3: ldloc.2
		IL_02e4: ldstr "R1S.png"
		IL_02e9: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_02ee: ldloc.2
		IL_02ef: ldstr "R2.png"
		IL_02f4: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_02f9: ldloc.2
		IL_02fa: ldstr "R2S.png"
		IL_02ff: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0304: ldloc.2
		IL_0305: ldstr "R3.png"
		IL_030a: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_030f: ldloc.2
		IL_0310: ldstr "R3S.png"
		IL_0315: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_031a: ldloc.2
		IL_031b: ldstr "R4.png"
		IL_0320: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0325: ldloc.2
		IL_0326: ldstr "R4S.png"
		IL_032b: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0330: ldloc.2
		IL_0331: ldstr "R5.png"
		IL_0336: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_033b: ldloc.2
		IL_033c: ldstr "R5S.png"
		IL_0341: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0346: ldloc.2
		IL_0347: ldstr "R6.png"
		IL_034c: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0351: ldloc.2
		IL_0352: ldstr "R6S.png"
		IL_0357: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_035c: ldloc.2
		IL_035d: ldstr "R7.png"
		IL_0362: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0367: ldloc.2
		IL_0368: ldstr "R7S.png"
		IL_036d: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0372: ldloc.2
		IL_0373: ldstr "R8.png"
		IL_0378: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_037d: ldloc.2
		IL_037e: ldstr "R8S.png"
		IL_0383: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0388: ldloc.2
		IL_0389: ldstr "R9.png"
		IL_038e: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0393: ldloc.2
		IL_0394: ldstr "R9S.png"
		IL_0399: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_039e: ldloc.2
		IL_039f: ldstr "R10.png"
		IL_03a4: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_03a9: ldloc.2
		IL_03aa: ldstr "R10S.png"
		IL_03af: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_03b4: ldloc.2
		IL_03b5: ldstr "R11.png"
		IL_03ba: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_03bf: ldloc.2
		IL_03c0: ldstr "R11S.png"
		IL_03c5: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_03ca: ldloc.2
		IL_03cb: ldstr "R12.png"
		IL_03d0: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_03d5: ldloc.2
		IL_03d6: ldstr "R12S.png"
		IL_03db: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_03e0: ldloc.2
		IL_03e1: ldstr "R13.png"
		IL_03e6: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_03eb: ldloc.2
		IL_03ec: ldstr "R13S.png"
		IL_03f1: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_03f6: ldloc.2
		IL_03f7: ldstr "R14.png"
		IL_03fc: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0401: ldloc.2
		IL_0402: ldstr "R14S.png"
		IL_0407: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_040c: ldloc.2
		IL_040d: ldstr "R15.png"
		IL_0412: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0417: ldloc.2
		IL_0418: ldstr "R15S.png"
		IL_041d: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_0422: ldloc.s 5
		IL_0424: ldloc.2
		IL_0425: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<string>::ToArray()
		IL_042a: callvirt instance void Philips.PSG.Gateway.FileSet::set_Files(string[])
		IL_042f: ldloc.1
		IL_0430: ldloc.s 5
		IL_0432: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Add(!0)
		IL_0437: ldloc.s 4
		IL_0439: ldloc.1
		IL_043a: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::ToArray()
		IL_043f: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_FileSets(class Philips.PSG.Gateway.FileSet[])
		IL_0444: ldloc.3
		IL_0445: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Clear()
		IL_044a: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_044f: stloc.s 6
		IL_0451: ldloc.s 6
		IL_0453: ldstr "03:45:00"
		IL_0458: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_045d: ldloc.s 6
		IL_045f: ldstr "07:45:00"
		IL_0464: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_0469: ldloc.3
		IL_046a: ldloc.s 6
		IL_046c: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_0471: ldloc.s 4
		IL_0473: ldloc.3
		IL_0474: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::ToArray()
		IL_0479: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_DefaultScheduleEvents(class Philips.PSG.Gateway.ScheduleEvent[])
		IL_047e: newobj instance void Philips.PSG.Gateway.CatalogEntry::.ctor()
		IL_0483: stloc.s 4
		IL_0485: ldloc.s 4
		IL_0487: ldstr "2k10 Easy 26\""
		IL_048c: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_TargetTvModel(string)
		IL_0491: ldloc.s 4
		IL_0493: ldstr "2k10_Easy"
		IL_0498: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_BaseFolder(string)
		IL_049d: ldloc.s 4
		IL_049f: ldstr "2k10_Easy_SS.ts"
		IL_04a4: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_OutputFileName(string)
		IL_04a9: ldloc.s 4
		IL_04ab: ldc.i4.1
		IL_04ac: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_Action(valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction)
		IL_04b1: ldloc.0
		IL_04b2: ldloc.s 4
		IL_04b4: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::Add(!0)
		IL_04b9: ldloc.1
		IL_04ba: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Clear()
		IL_04bf: newobj instance void Philips.PSG.Gateway.FileSet::.ctor()
		IL_04c4: stloc.s 5
		IL_04c6: ldloc.s 5
		IL_04c8: ldc.i4.1
		IL_04c9: callvirt instance void Philips.PSG.Gateway.FileSet::set_ID(valuetype Philips.PSG.Gateway.FileSet/FileSetID)
		IL_04ce: ldloc.s 5
		IL_04d0: ldc.i4.0
		IL_04d1: callvirt instance void Philips.PSG.Gateway.FileSet::set_Requirement(valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement)
		IL_04d6: ldloc.s 5
		IL_04d8: ldc.i4.0
		IL_04d9: callvirt instance void Philips.PSG.Gateway.FileSet::set_Usage(valuetype Philips.PSG.Gateway.FileSet/FileSetUsage)
		IL_04de: ldloc.2
		IL_04df: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Clear()
		IL_04e4: ldloc.2
		IL_04e5: ldstr "2K10_ds_buh_ssu_*_*ONID.ts"
		IL_04ea: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_04ef: ldloc.s 5
		IL_04f1: ldloc.2
		IL_04f2: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<string>::ToArray()
		IL_04f7: callvirt instance void Philips.PSG.Gateway.FileSet::set_Files(string[])
		IL_04fc: ldloc.1
		IL_04fd: ldloc.s 5
		IL_04ff: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Add(!0)
		IL_0504: ldloc.s 4
		IL_0506: ldloc.1
		IL_0507: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::ToArray()
		IL_050c: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_FileSets(class Philips.PSG.Gateway.FileSet[])
		IL_0511: ldloc.3
		IL_0512: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Clear()
		IL_0517: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_051c: stloc.s 6
		IL_051e: ldloc.s 6
		IL_0520: ldstr "00:00:00"
		IL_0525: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_052a: ldloc.s 6
		IL_052c: ldstr "02:00:00"
		IL_0531: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_0536: ldloc.3
		IL_0537: ldloc.s 6
		IL_0539: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_053e: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_0543: stloc.s 6
		IL_0545: ldloc.s 6
		IL_0547: ldstr "04:00:00"
		IL_054c: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_0551: ldloc.s 6
		IL_0553: ldstr "06:00:00"
		IL_0558: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_055d: ldloc.3
		IL_055e: ldloc.s 6
		IL_0560: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_0565: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_056a: stloc.s 6
		IL_056c: ldloc.s 6
		IL_056e: ldstr "08:00:00"
		IL_0573: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_0578: ldloc.s 6
		IL_057a: ldstr "10:00:00"
		IL_057f: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_0584: ldloc.3
		IL_0585: ldloc.s 6
		IL_0587: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_058c: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_0591: stloc.s 6
		IL_0593: ldloc.s 6
		IL_0595: ldstr "12:00:00"
		IL_059a: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_059f: ldloc.s 6
		IL_05a1: ldstr "14:00:00"
		IL_05a6: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_05ab: ldloc.3
		IL_05ac: ldloc.s 6
		IL_05ae: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_05b3: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_05b8: stloc.s 6
		IL_05ba: ldloc.s 6
		IL_05bc: ldstr "16:00:00"
		IL_05c1: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_05c6: ldloc.s 6
		IL_05c8: ldstr "18:00:00"
		IL_05cd: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_05d2: ldloc.3
		IL_05d3: ldloc.s 6
		IL_05d5: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_05da: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_05df: stloc.s 6
		IL_05e1: ldloc.s 6
		IL_05e3: ldstr "20:00:00"
		IL_05e8: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_05ed: ldloc.s 6
		IL_05ef: ldstr "22:00:00"
		IL_05f4: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_05f9: ldloc.3
		IL_05fa: ldloc.s 6
		IL_05fc: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_0601: ldloc.s 4
		IL_0603: ldloc.3
		IL_0604: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::ToArray()
		IL_0609: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_DefaultScheduleEvents(class Philips.PSG.Gateway.ScheduleEvent[])
		IL_060e: newobj instance void Philips.PSG.Gateway.CatalogEntry::.ctor()
		IL_0613: stloc.s 4
		IL_0615: ldloc.s 4
		IL_0617: ldstr "2k10 Easy 32\""
		IL_061c: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_TargetTvModel(string)
		IL_0621: ldloc.s 4
		IL_0623: ldstr "2k10_Easy"
		IL_0628: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_BaseFolder(string)
		IL_062d: ldloc.s 4
		IL_062f: ldstr "2k10_Easy_LS.ts"
		IL_0634: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_OutputFileName(string)
		IL_0639: ldloc.s 4
		IL_063b: ldc.i4.1
		IL_063c: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_Action(valuetype Philips.PSG.Gateway.CatalogEntry/BuildAction)
		IL_0641: ldloc.0
		IL_0642: ldloc.s 4
		IL_0644: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::Add(!0)
		IL_0649: ldloc.1
		IL_064a: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Clear()
		IL_064f: newobj instance void Philips.PSG.Gateway.FileSet::.ctor()
		IL_0654: stloc.s 5
		IL_0656: ldloc.s 5
		IL_0658: ldc.i4.1
		IL_0659: callvirt instance void Philips.PSG.Gateway.FileSet::set_ID(valuetype Philips.PSG.Gateway.FileSet/FileSetID)
		IL_065e: ldloc.s 5
		IL_0660: ldc.i4.0
		IL_0661: callvirt instance void Philips.PSG.Gateway.FileSet::set_Requirement(valuetype Philips.PSG.Gateway.FileSet/FileSetRequirement)
		IL_0666: ldloc.s 5
		IL_0668: ldc.i4.0
		IL_0669: callvirt instance void Philips.PSG.Gateway.FileSet::set_Usage(valuetype Philips.PSG.Gateway.FileSet/FileSetUsage)
		IL_066e: ldloc.2
		IL_066f: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Clear()
		IL_0674: ldloc.2
		IL_0675: ldstr "2K10_dl_buh_ssu_*_*ONID.ts"
		IL_067a: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)
		IL_067f: ldloc.s 5
		IL_0681: ldloc.2
		IL_0682: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<string>::ToArray()
		IL_0687: callvirt instance void Philips.PSG.Gateway.FileSet::set_Files(string[])
		IL_068c: ldloc.1
		IL_068d: ldloc.s 5
		IL_068f: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::Add(!0)
		IL_0694: ldloc.s 4
		IL_0696: ldloc.1
		IL_0697: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.FileSet>::ToArray()
		IL_069c: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_FileSets(class Philips.PSG.Gateway.FileSet[])
		IL_06a1: ldloc.3
		IL_06a2: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Clear()
		IL_06a7: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_06ac: stloc.s 6
		IL_06ae: ldloc.s 6
		IL_06b0: ldstr "02:00:00"
		IL_06b5: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_06ba: ldloc.s 6
		IL_06bc: ldstr "04:00:00"
		IL_06c1: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_06c6: ldloc.3
		IL_06c7: ldloc.s 6
		IL_06c9: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_06ce: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_06d3: stloc.s 6
		IL_06d5: ldloc.s 6
		IL_06d7: ldstr "06:00:00"
		IL_06dc: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_06e1: ldloc.s 6
		IL_06e3: ldstr "08:00:00"
		IL_06e8: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_06ed: ldloc.3
		IL_06ee: ldloc.s 6
		IL_06f0: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_06f5: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_06fa: stloc.s 6
		IL_06fc: ldloc.s 6
		IL_06fe: ldstr "10:00:00"
		IL_0703: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_0708: ldloc.s 6
		IL_070a: ldstr "12:00:00"
		IL_070f: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_0714: ldloc.3
		IL_0715: ldloc.s 6
		IL_0717: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_071c: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_0721: stloc.s 6
		IL_0723: ldloc.s 6
		IL_0725: ldstr "14:00:00"
		IL_072a: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_072f: ldloc.s 6
		IL_0731: ldstr "16:00:00"
		IL_0736: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_073b: ldloc.3
		IL_073c: ldloc.s 6
		IL_073e: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_0743: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_0748: stloc.s 6
		IL_074a: ldloc.s 6
		IL_074c: ldstr "18:00:00"
		IL_0751: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_0756: ldloc.s 6
		IL_0758: ldstr "20:00:00"
		IL_075d: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_0762: ldloc.3
		IL_0763: ldloc.s 6
		IL_0765: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_076a: newobj instance void Philips.PSG.Gateway.ScheduleEvent::.ctor()
		IL_076f: stloc.s 6
		IL_0771: ldloc.s 6
		IL_0773: ldstr "22:00:00"
		IL_0778: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
		IL_077d: ldloc.s 6
		IL_077f: ldstr "1.00:00:00"
		IL_0784: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
		IL_0789: ldloc.3
		IL_078a: ldloc.s 6
		IL_078c: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Add(!0)
		IL_0791: ldloc.s 4
		IL_0793: ldloc.3
		IL_0794: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::ToArray()
		IL_0799: callvirt instance void Philips.PSG.Gateway.CatalogEntry::set_DefaultScheduleEvents(class Philips.PSG.Gateway.ScheduleEvent[])
		IL_079e: dup
		IL_079f: ldloc.0
		IL_07a0: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.CatalogEntry>::ToArray()
		IL_07a5: callvirt instance void Philips.PSG.Gateway.Catalog::set_Entries(class Philips.PSG.Gateway.CatalogEntry[])
		IL_07aa: callvirt instance void Philips.PSG.Gateway.Catalog::SaveToFile()
		IL_07af: ret
	} // end of method MainForm::SaveDefaultCatalog

	.method private hidebysig 
		instance void MainForm_Load (
			object sender,
			class [mscorlib]System.EventArgs e
		) cil managed 
	{
		// Method begins at RVA 0x7fcc
		// Header size: 1
		// Code size: 1 (0x1)
		.maxstack 8

		IL_0000: ret
	} // end of method MainForm::MainForm_Load

	.method family hidebysig virtual 
		instance void Dispose (
			bool disposing
		) cil managed 
	{
		// Method begins at RVA 0x7fce
		// Header size: 1
		// Code size: 30 (0x1e)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brfalse.s IL_0016

		IL_0003: ldarg.0
		IL_0004: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.MainForm::components
		IL_0009: brfalse.s IL_0016

		IL_000b: ldarg.0
		IL_000c: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.MainForm::components
		IL_0011: callvirt instance void [mscorlib]System.IDisposable::Dispose()

		IL_0016: ldarg.0
		IL_0017: ldarg.1
		IL_0018: call instance void [System.Windows.Forms]System.Windows.Forms.Form::Dispose(bool)
		IL_001d: ret
	} // end of method MainForm::Dispose

	.method private hidebysig 
		instance void InitializeComponent () cil managed 
	{
		// Method begins at RVA 0x7ff0
		// Header size: 12
		// Code size: 1479 (0x5c7)
		.maxstack 5
		.locals init (
			[0] class [System]System.ComponentModel.ComponentResourceManager
		)

		IL_0000: ldtoken Philips.PSG.Gateway.MainForm
		IL_0005: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_000a: newobj instance void [System]System.ComponentModel.ComponentResourceManager::.ctor(class [mscorlib]System.Type)
		IL_000f: stloc.0
		IL_0010: ldarg.0
		IL_0011: newobj instance void [System.Windows.Forms]System.Windows.Forms.MenuStrip::.ctor()
		IL_0016: stfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_001b: ldarg.0
		IL_001c: newobj instance void [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem::.ctor()
		IL_0021: stfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::fileToolStripMenuItem
		IL_0026: ldarg.0
		IL_0027: newobj instance void [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem::.ctor()
		IL_002c: stfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem
		IL_0031: ldarg.0
		IL_0032: newobj instance void [System.Windows.Forms]System.Windows.Forms.ToolStripSeparator::.ctor()
		IL_0037: stfld class [System.Windows.Forms]System.Windows.Forms.ToolStripSeparator Philips.PSG.Gateway.MainForm::separatorToolStripMenuItem
		IL_003c: ldarg.0
		IL_003d: newobj instance void [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem::.ctor()
		IL_0042: stfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::exitToolStripMenuItem
		IL_0047: ldarg.0
		IL_0048: newobj instance void [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem::.ctor()
		IL_004d: stfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::helpToolStripMenuItem
		IL_0052: ldarg.0
		IL_0053: newobj instance void [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem::.ctor()
		IL_0058: stfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::aboutToolStripMenuItem
		IL_005d: ldarg.0
		IL_005e: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_0063: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0068: ldarg.0
		IL_0069: newobj instance void [System.Windows.Forms]System.Windows.Forms.Button::.ctor()
		IL_006e: stfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_0073: ldarg.0
		IL_0074: newobj instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0079: stfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.MainForm::dekTecDeviceStatusLabel
		IL_007e: ldarg.0
		IL_007f: newobj instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::.ctor()
		IL_0084: stfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_0089: ldarg.0
		IL_008a: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_008f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::SuspendLayout()
		IL_0094: ldarg.0
		IL_0095: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_009a: callvirt instance void [System]System.ComponentModel.ISupportInitialize::BeginInit()
		IL_009f: ldarg.0
		IL_00a0: call instance void [System.Windows.Forms]System.Windows.Forms.Control::SuspendLayout()
		IL_00a5: ldarg.0
		IL_00a6: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_00ab: callvirt instance class [System.Windows.Forms]System.Windows.Forms.ToolStripItemCollection [System.Windows.Forms]System.Windows.Forms.ToolStrip::get_Items()
		IL_00b0: ldc.i4.2
		IL_00b1: newarr [System.Windows.Forms]System.Windows.Forms.ToolStripItem
		IL_00b6: dup
		IL_00b7: ldc.i4.0
		IL_00b8: ldarg.0
		IL_00b9: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::fileToolStripMenuItem
		IL_00be: stelem.ref
		IL_00bf: dup
		IL_00c0: ldc.i4.1
		IL_00c1: ldarg.0
		IL_00c2: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::helpToolStripMenuItem
		IL_00c7: stelem.ref
		IL_00c8: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItemCollection::AddRange(class [System.Windows.Forms]System.Windows.Forms.ToolStripItem[])
		IL_00cd: ldarg.0
		IL_00ce: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_00d3: ldc.i4.0
		IL_00d4: ldc.i4.0
		IL_00d5: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_00da: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_00df: ldarg.0
		IL_00e0: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_00e5: ldstr "menuStrip"
		IL_00ea: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_00ef: ldarg.0
		IL_00f0: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_00f5: ldc.i4 550
		IL_00fa: ldc.i4.s 24
		IL_00fc: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0101: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0106: ldarg.0
		IL_0107: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_010c: ldc.i4.0
		IL_010d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0112: ldarg.0
		IL_0113: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_0118: ldstr "menuStrip"
		IL_011d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0122: ldarg.0
		IL_0123: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::fileToolStripMenuItem
		IL_0128: callvirt instance class [System.Windows.Forms]System.Windows.Forms.ToolStripItemCollection [System.Windows.Forms]System.Windows.Forms.ToolStripDropDownItem::get_DropDownItems()
		IL_012d: ldc.i4.3
		IL_012e: newarr [System.Windows.Forms]System.Windows.Forms.ToolStripItem
		IL_0133: dup
		IL_0134: ldc.i4.0
		IL_0135: ldarg.0
		IL_0136: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem
		IL_013b: stelem.ref
		IL_013c: dup
		IL_013d: ldc.i4.1
		IL_013e: ldarg.0
		IL_013f: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripSeparator Philips.PSG.Gateway.MainForm::separatorToolStripMenuItem
		IL_0144: stelem.ref
		IL_0145: dup
		IL_0146: ldc.i4.2
		IL_0147: ldarg.0
		IL_0148: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::exitToolStripMenuItem
		IL_014d: stelem.ref
		IL_014e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItemCollection::AddRange(class [System.Windows.Forms]System.Windows.Forms.ToolStripItem[])
		IL_0153: ldarg.0
		IL_0154: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::fileToolStripMenuItem
		IL_0159: ldstr "fileToolStripMenuItem"
		IL_015e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Name(string)
		IL_0163: ldarg.0
		IL_0164: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::fileToolStripMenuItem
		IL_0169: ldc.i4.s 37
		IL_016b: ldc.i4.s 20
		IL_016d: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0172: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0177: ldarg.0
		IL_0178: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::fileToolStripMenuItem
		IL_017d: ldstr "File"
		IL_0182: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Text(string)
		IL_0187: ldarg.0
		IL_0188: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem
		IL_018d: ldstr "configurationToolStripMenuItem"
		IL_0192: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Name(string)
		IL_0197: ldarg.0
		IL_0198: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem
		IL_019d: ldc.i4 148
		IL_01a2: ldc.i4.s 22
		IL_01a4: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_01a9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_01ae: ldarg.0
		IL_01af: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem
		IL_01b4: ldstr "Configuration"
		IL_01b9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Text(string)
		IL_01be: ldarg.0
		IL_01bf: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem
		IL_01c4: ldarg.0
		IL_01c5: ldftn instance void Philips.PSG.Gateway.MainForm::configurationToolStripMenuItem_Click(object, class [mscorlib]System.EventArgs)
		IL_01cb: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_01d0: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::add_Click(class [mscorlib]System.EventHandler)
		IL_01d5: ldarg.0
		IL_01d6: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripSeparator Philips.PSG.Gateway.MainForm::separatorToolStripMenuItem
		IL_01db: ldstr "separatorToolStripMenuItem"
		IL_01e0: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Name(string)
		IL_01e5: ldarg.0
		IL_01e6: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripSeparator Philips.PSG.Gateway.MainForm::separatorToolStripMenuItem
		IL_01eb: ldc.i4 145
		IL_01f0: ldc.i4.6
		IL_01f1: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_01f6: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_01fb: ldarg.0
		IL_01fc: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::exitToolStripMenuItem
		IL_0201: ldstr "exitToolStripMenuItem"
		IL_0206: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Name(string)
		IL_020b: ldarg.0
		IL_020c: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::exitToolStripMenuItem
		IL_0211: ldc.i4 148
		IL_0216: ldc.i4.s 22
		IL_0218: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_021d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0222: ldarg.0
		IL_0223: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::exitToolStripMenuItem
		IL_0228: ldstr "Exit"
		IL_022d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Text(string)
		IL_0232: ldarg.0
		IL_0233: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::exitToolStripMenuItem
		IL_0238: ldarg.0
		IL_0239: ldftn instance void Philips.PSG.Gateway.MainForm::exitToolStripMenuItem_Click(object, class [mscorlib]System.EventArgs)
		IL_023f: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_0244: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::add_Click(class [mscorlib]System.EventHandler)
		IL_0249: ldarg.0
		IL_024a: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::helpToolStripMenuItem
		IL_024f: callvirt instance class [System.Windows.Forms]System.Windows.Forms.ToolStripItemCollection [System.Windows.Forms]System.Windows.Forms.ToolStripDropDownItem::get_DropDownItems()
		IL_0254: ldc.i4.1
		IL_0255: newarr [System.Windows.Forms]System.Windows.Forms.ToolStripItem
		IL_025a: dup
		IL_025b: ldc.i4.0
		IL_025c: ldarg.0
		IL_025d: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::aboutToolStripMenuItem
		IL_0262: stelem.ref
		IL_0263: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItemCollection::AddRange(class [System.Windows.Forms]System.Windows.Forms.ToolStripItem[])
		IL_0268: ldarg.0
		IL_0269: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::helpToolStripMenuItem
		IL_026e: ldstr "helpToolStripMenuItem"
		IL_0273: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Name(string)
		IL_0278: ldarg.0
		IL_0279: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::helpToolStripMenuItem
		IL_027e: ldc.i4.s 44
		IL_0280: ldc.i4.s 20
		IL_0282: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0287: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_028c: ldarg.0
		IL_028d: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::helpToolStripMenuItem
		IL_0292: ldstr "Help"
		IL_0297: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Text(string)
		IL_029c: ldarg.0
		IL_029d: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::aboutToolStripMenuItem
		IL_02a2: ldstr "aboutToolStripMenuItem"
		IL_02a7: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Name(string)
		IL_02ac: ldarg.0
		IL_02ad: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::aboutToolStripMenuItem
		IL_02b2: ldc.i4.s 107
		IL_02b4: ldc.i4.s 22
		IL_02b6: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_02bb: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_02c0: ldarg.0
		IL_02c1: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::aboutToolStripMenuItem
		IL_02c6: ldstr "About"
		IL_02cb: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::set_Text(string)
		IL_02d0: ldarg.0
		IL_02d1: ldfld class [System.Windows.Forms]System.Windows.Forms.ToolStripMenuItem Philips.PSG.Gateway.MainForm::aboutToolStripMenuItem
		IL_02d6: ldarg.0
		IL_02d7: ldftn instance void Philips.PSG.Gateway.MainForm::aboutToolStripMenuItem_Click(object, class [mscorlib]System.EventArgs)
		IL_02dd: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_02e2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ToolStripItem::add_Click(class [mscorlib]System.EventHandler)
		IL_02e7: ldarg.0
		IL_02e8: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_02ed: ldc.i4 129
		IL_02f2: ldc.i4.s 35
		IL_02f4: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_02f9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_02fe: ldarg.0
		IL_02ff: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0304: ldstr "runPauseButton"
		IL_0309: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_030e: ldarg.0
		IL_030f: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0314: ldc.i4 130
		IL_0319: ldc.i4.s 23
		IL_031b: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0320: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0325: ldarg.0
		IL_0326: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_032b: ldc.i4.1
		IL_032c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0331: ldarg.0
		IL_0332: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0337: ldstr "RunPause"
		IL_033c: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0341: ldarg.0
		IL_0342: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0347: ldc.i4.1
		IL_0348: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ButtonBase::set_UseVisualStyleBackColor(bool)
		IL_034d: ldarg.0
		IL_034e: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0353: ldarg.0
		IL_0354: ldftn instance void Philips.PSG.Gateway.MainForm::PlayPauseButton_Click(object, class [mscorlib]System.EventArgs)
		IL_035a: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_035f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::add_Click(class [mscorlib]System.EventHandler)
		IL_0364: ldarg.0
		IL_0365: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_036a: ldc.i4 291
		IL_036f: ldc.i4.s 35
		IL_0371: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0376: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_037b: ldarg.0
		IL_037c: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_0381: ldstr "buildButton"
		IL_0386: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_038b: ldarg.0
		IL_038c: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_0391: ldc.i4 130
		IL_0396: ldc.i4.s 23
		IL_0398: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_039d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_03a2: ldarg.0
		IL_03a3: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_03a8: ldc.i4.2
		IL_03a9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_03ae: ldarg.0
		IL_03af: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_03b4: ldstr "Build"
		IL_03b9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_03be: ldarg.0
		IL_03bf: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_03c4: ldc.i4.1
		IL_03c5: callvirt instance void [System.Windows.Forms]System.Windows.Forms.ButtonBase::set_UseVisualStyleBackColor(bool)
		IL_03ca: ldarg.0
		IL_03cb: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_03d0: ldarg.0
		IL_03d1: ldftn instance void Philips.PSG.Gateway.MainForm::BuildButton_Click(object, class [mscorlib]System.EventArgs)
		IL_03d7: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_03dc: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::add_Click(class [mscorlib]System.EventHandler)
		IL_03e1: ldarg.0
		IL_03e2: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.MainForm::dekTecDeviceStatusLabel
		IL_03e7: ldc.i4.1
		IL_03e8: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_AutoSize(bool)
		IL_03ed: ldarg.0
		IL_03ee: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.MainForm::dekTecDeviceStatusLabel
		IL_03f3: ldc.i4.s 24
		IL_03f5: ldc.i4 443
		IL_03fa: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_03ff: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_0404: ldarg.0
		IL_0405: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.MainForm::dekTecDeviceStatusLabel
		IL_040a: ldstr "dekTecDeviceStatusLabel"
		IL_040f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0414: ldarg.0
		IL_0415: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.MainForm::dekTecDeviceStatusLabel
		IL_041a: ldc.i4.s 110
		IL_041c: ldc.i4.s 13
		IL_041e: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_0423: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0428: ldarg.0
		IL_0429: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.MainForm::dekTecDeviceStatusLabel
		IL_042e: ldc.i4.4
		IL_042f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_TabIndex(int32)
		IL_0434: ldarg.0
		IL_0435: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.MainForm::dekTecDeviceStatusLabel
		IL_043a: ldstr "DekTecDeviceStatus"
		IL_043f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0444: ldarg.0
		IL_0445: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_044a: ldc.i4 457
		IL_044f: ldc.i4 442
		IL_0454: newobj instance void [System.Drawing]System.Drawing.Point::.ctor(int32, int32)
		IL_0459: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Location(valuetype [System.Drawing]System.Drawing.Point)
		IL_045e: ldarg.0
		IL_045f: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_0464: ldstr "philipsPictureBox"
		IL_0469: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_046e: ldarg.0
		IL_046f: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_0474: ldc.i4.s 65
		IL_0476: ldc.i4.s 14
		IL_0478: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_047d: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Size(valuetype [System.Drawing]System.Drawing.Size)
		IL_0482: ldarg.0
		IL_0483: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_0488: ldc.i4.3
		IL_0489: callvirt instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::set_SizeMode(valuetype [System.Windows.Forms]System.Windows.Forms.PictureBoxSizeMode)
		IL_048e: ldarg.0
		IL_048f: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_0494: ldc.i4.s 19
		IL_0496: callvirt instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::set_TabIndex(int32)
		IL_049b: ldarg.0
		IL_049c: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_04a1: ldc.i4.0
		IL_04a2: callvirt instance void [System.Windows.Forms]System.Windows.Forms.PictureBox::set_TabStop(bool)
		IL_04a7: ldarg.0
		IL_04a8: ldc.i4.0
		IL_04a9: call instance void [System.Windows.Forms]System.Windows.Forms.ContainerControl::set_AutoScaleMode(valuetype [System.Windows.Forms]System.Windows.Forms.AutoScaleMode)
		IL_04ae: ldarg.0
		IL_04af: ldc.i4 550
		IL_04b4: ldc.i4 468
		IL_04b9: newobj instance void [System.Drawing]System.Drawing.Size::.ctor(int32, int32)
		IL_04be: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_ClientSize(valuetype [System.Drawing]System.Drawing.Size)
		IL_04c3: ldarg.0
		IL_04c4: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_04c9: ldarg.0
		IL_04ca: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_04cf: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_04d4: ldarg.0
		IL_04d5: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_04da: ldarg.0
		IL_04db: ldfld class [System.Windows.Forms]System.Windows.Forms.Label Philips.PSG.Gateway.MainForm::dekTecDeviceStatusLabel
		IL_04e0: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_04e5: ldarg.0
		IL_04e6: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_04eb: ldarg.0
		IL_04ec: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::buildButton
		IL_04f1: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_04f6: ldarg.0
		IL_04f7: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_04fc: ldarg.0
		IL_04fd: ldfld class [System.Windows.Forms]System.Windows.Forms.Button Philips.PSG.Gateway.MainForm::runPauseButton
		IL_0502: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0507: ldarg.0
		IL_0508: call instance class [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection [System.Windows.Forms]System.Windows.Forms.Control::get_Controls()
		IL_050d: ldarg.0
		IL_050e: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_0513: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control/ControlCollection::Add(class [System.Windows.Forms]System.Windows.Forms.Control)
		IL_0518: ldarg.0
		IL_0519: ldc.i4.1
		IL_051a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_DoubleBuffered(bool)
		IL_051f: ldarg.0
		IL_0520: ldc.i4.0
		IL_0521: call instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Enabled(bool)
		IL_0526: ldarg.0
		IL_0527: ldc.i4.1
		IL_0528: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_FormBorderStyle(valuetype [System.Windows.Forms]System.Windows.Forms.FormBorderStyle)
		IL_052d: ldarg.0
		IL_052e: ldloc.0
		IL_052f: ldstr "$this.Icon"
		IL_0534: callvirt instance object [mscorlib]System.Resources.ResourceManager::GetObject(string)
		IL_0539: castclass [System.Drawing]System.Drawing.Icon
		IL_053e: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_Icon(class [System.Drawing]System.Drawing.Icon)
		IL_0543: ldarg.0
		IL_0544: ldarg.0
		IL_0545: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_054a: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_MainMenuStrip(class [System.Windows.Forms]System.Windows.Forms.MenuStrip)
		IL_054f: ldarg.0
		IL_0550: ldc.i4.0
		IL_0551: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_MaximizeBox(bool)
		IL_0556: ldarg.0
		IL_0557: ldstr "MainForm"
		IL_055c: call instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Name(string)
		IL_0561: ldarg.0
		IL_0562: ldc.i4.1
		IL_0563: call instance void [System.Windows.Forms]System.Windows.Forms.Form::set_StartPosition(valuetype [System.Windows.Forms]System.Windows.Forms.FormStartPosition)
		IL_0568: ldarg.0
		IL_0569: ldstr "MainForm"
		IL_056e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0573: ldarg.0
		IL_0574: ldarg.0
		IL_0575: ldftn instance void Philips.PSG.Gateway.MainForm::MainForm_Load(object, class [mscorlib]System.EventArgs)
		IL_057b: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_0580: call instance void [System.Windows.Forms]System.Windows.Forms.Form::add_Load(class [mscorlib]System.EventHandler)
		IL_0585: ldarg.0
		IL_0586: ldarg.0
		IL_0587: ldftn instance void Philips.PSG.Gateway.MainForm::MainForm_FormClosing(object, class [System.Windows.Forms]System.Windows.Forms.FormClosingEventArgs)
		IL_058d: newobj instance void [System.Windows.Forms]System.Windows.Forms.FormClosingEventHandler::.ctor(object, native int)
		IL_0592: call instance void [System.Windows.Forms]System.Windows.Forms.Form::add_FormClosing(class [System.Windows.Forms]System.Windows.Forms.FormClosingEventHandler)
		IL_0597: ldarg.0
		IL_0598: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_059d: ldc.i4.0
		IL_059e: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::ResumeLayout(bool)
		IL_05a3: ldarg.0
		IL_05a4: ldfld class [System.Windows.Forms]System.Windows.Forms.MenuStrip Philips.PSG.Gateway.MainForm::menuStrip
		IL_05a9: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::PerformLayout()
		IL_05ae: ldarg.0
		IL_05af: ldfld class [System.Windows.Forms]System.Windows.Forms.PictureBox Philips.PSG.Gateway.MainForm::philipsPictureBox
		IL_05b4: callvirt instance void [System]System.ComponentModel.ISupportInitialize::EndInit()
		IL_05b9: ldarg.0
		IL_05ba: ldc.i4.0
		IL_05bb: call instance void [System.Windows.Forms]System.Windows.Forms.Control::ResumeLayout(bool)
		IL_05c0: ldarg.0
		IL_05c1: call instance void [System.Windows.Forms]System.Windows.Forms.Control::PerformLayout()
		IL_05c6: ret
	} // end of method MainForm::InitializeComponent

} // end of class Philips.PSG.Gateway.MainForm

.class public auto ansi beforefieldinit Philips.PSG.Gateway.InstanceManager
	extends [mscorlib]System.Object
{
	// Nested Types
	.class nested public auto ansi sealed RunningState
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.InstanceManager/RunningState NotRunning = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.InstanceManager/RunningState RunningByThisUser = int32(1)
		.field public static literal valuetype Philips.PSG.Gateway.InstanceManager/RunningState RunningByOtherUser = int32(2)

	} // end of class RunningState

	.class nested private auto ansi abstract sealed beforefieldinit SingleInstanceImplementor
		extends [mscorlib]System.Object
	{
		// Fields
		.field private static literal uint32 WM_SYSCOMMAND = uint32(274)
		.field private static literal uint32 SC_RESTORE = uint32(61728)
		.field private static class [mscorlib]System.Threading.Mutex appMutex

		// Methods
		.method private hidebysig static pinvokeimpl("user32.dll" winapi) 
			int32 SetForegroundWindow (
				native int hWnd
			) cil managed preservesig 
		{
		} // end of method SingleInstanceImplementor::SetForegroundWindow

		.method private hidebysig static pinvokeimpl("user32.dll" winapi) 
			int32 PostMessage (
				native int hWnd,
				uint32 sadf,
				uint32 l,
				uint32 w
			) cil managed preservesig 
		{
		} // end of method SingleInstanceImplementor::PostMessage

		.method private hidebysig static pinvokeimpl("user32.dll" winapi) 
			native int FindWindow (
				string s,
				string s2
			) cil managed preservesig 
		{
		} // end of method SingleInstanceImplementor::FindWindow

		.method private hidebysig static pinvokeimpl("user32.dll" winapi) 
			uint32 GetWindowThreadProcessId (
				native int hWnd,
				[out] uint32& pid
			) cil managed preservesig 
		{
		} // end of method SingleInstanceImplementor::GetWindowThreadProcessId

		.method public hidebysig static 
			valuetype Philips.PSG.Gateway.InstanceManager/RunningState GetRunningState () cil managed 
		{
			// Method begins at RVA 0x9ed4
			// Header size: 12
			// Code size: 55 (0x37)
			.maxstack 3
			.locals init (
				[0] valuetype Philips.PSG.Gateway.InstanceManager/RunningState,
				[1] bool,
				[2] string
			)

			IL_0000: ldc.i4.1
			IL_0001: stloc.1
			IL_0002: ldstr "Global\\"
			IL_0007: call string Philips.PSG.Gateway.InstanceManager/SingleInstanceImplementor::GetMyUniqueStringID()
			IL_000c: call string [mscorlib]System.String::Concat(string, string)
			IL_0011: stloc.2
			.try
			{
				IL_0012: ldc.i4.1
				IL_0013: ldloc.2
				IL_0014: ldloca.s 1
				IL_0016: newobj instance void [mscorlib]System.Threading.Mutex::.ctor(bool, string, bool&)
				IL_001b: stsfld class [mscorlib]System.Threading.Mutex Philips.PSG.Gateway.InstanceManager/SingleInstanceImplementor::appMutex
				IL_0020: ldloc.1
				IL_0021: brfalse.s IL_0027

				IL_0023: ldc.i4.0
				IL_0024: stloc.0
				IL_0025: br.s IL_0029

				IL_0027: ldc.i4.1
				IL_0028: stloc.0

				IL_0029: leave.s IL_0035
			} // end .try
			catch [mscorlib]System.UnauthorizedAccessException
			{
				IL_002b: pop
				IL_002c: ldc.i4.2
				IL_002d: stloc.0
				IL_002e: leave.s IL_0035
			} // end handler
			catch [mscorlib]System.Exception
			{
				IL_0030: pop
				IL_0031: ldc.i4.0
				IL_0032: stloc.0
				IL_0033: leave.s IL_0035
			} // end handler

			IL_0035: ldloc.0
			IL_0036: ret
		} // end of method SingleInstanceImplementor::GetRunningState

		.method private hidebysig static 
			string GetMyUniqueStringID () cil managed 
		{
			// Method begins at RVA 0x9f34
			// Header size: 1
			// Code size: 31 (0x1f)
			.maxstack 8

			IL_0000: call class [mscorlib]System.Reflection.Assembly [mscorlib]System.Reflection.Assembly::GetExecutingAssembly()
			IL_0005: ldtoken [mscorlib]System.Runtime.InteropServices.GuidAttribute
			IL_000a: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
			IL_000f: call class [mscorlib]System.Attribute [mscorlib]System.Attribute::GetCustomAttribute(class [mscorlib]System.Reflection.Assembly, class [mscorlib]System.Type)
			IL_0014: castclass [mscorlib]System.Runtime.InteropServices.GuidAttribute
			IL_0019: callvirt instance string [mscorlib]System.Runtime.InteropServices.GuidAttribute::get_Value()
			IL_001e: ret
		} // end of method SingleInstanceImplementor::GetMyUniqueStringID

		.method public hidebysig static 
			void SwitchToExistingInstance () cil managed 
		{
			// Method begins at RVA 0x9f54
			// Header size: 12
			// Code size: 45 (0x2d)
			.maxstack 4
			.locals init (
				[0] native int
			)

			IL_0000: call native int Philips.PSG.Gateway.InstanceManager/SingleInstanceImplementor::GetExistingWindowHandle()
			IL_0005: stloc.0
			IL_0006: ldloc.0
			IL_0007: ldsfld native int [mscorlib]System.IntPtr::Zero
			IL_000c: call bool [mscorlib]System.IntPtr::op_Inequality(native int, native int)
			IL_0011: brfalse.s IL_002c

			IL_0013: ldloc.0
			IL_0014: ldc.i4 274
			IL_0019: ldc.i4 61728
			IL_001e: ldc.i4.0
			IL_001f: call int32 Philips.PSG.Gateway.InstanceManager/SingleInstanceImplementor::PostMessage(native int, uint32, uint32, uint32)
			IL_0024: pop
			IL_0025: ldloc.0
			IL_0026: call int32 Philips.PSG.Gateway.InstanceManager/SingleInstanceImplementor::SetForegroundWindow(native int)
			IL_002b: pop

			IL_002c: ret
		} // end of method SingleInstanceImplementor::SwitchToExistingInstance

		.method private hidebysig static 
			native int GetExistingWindowHandle () cil managed 
		{
			// Method begins at RVA 0x9f90
			// Header size: 12
			// Code size: 178 (0xb2)
			.maxstack 2
			.locals init (
				[0] native int,
				[1] class [System]System.Diagnostics.Process,
				[2] class [System]System.Diagnostics.Process[],
				[3] int32,
				[4] class [System]System.Diagnostics.Process,
				[5] native int,
				[6] uint32
			)

			IL_0000: ldsfld native int [mscorlib]System.IntPtr::Zero
			IL_0005: stloc.0
			IL_0006: call class [System]System.Diagnostics.Process [System]System.Diagnostics.Process::GetCurrentProcess()
			IL_000b: stloc.1
			IL_000c: ldloc.1
			IL_000d: callvirt instance string [System]System.Diagnostics.Process::get_ProcessName()
			IL_0012: call class [System]System.Diagnostics.Process[] [System]System.Diagnostics.Process::GetProcessesByName(string)
			IL_0017: stloc.2
			IL_0018: ldc.i4.0
			IL_0019: stloc.3
			IL_001a: br IL_00a7
			// loop start (head: IL_00a7)
				IL_001f: ldloc.2
				IL_0020: ldloc.3
				IL_0021: ldelem.ref
				IL_0022: stloc.s 4
				IL_0024: ldloc.s 4
				IL_0026: ldloc.1
				IL_0027: callvirt instance bool [mscorlib]System.Object::Equals(object)
				IL_002c: brtrue.s IL_00a3

				IL_002e: ldloc.s 4
				IL_0030: callvirt instance class [System]System.Diagnostics.ProcessModule [System]System.Diagnostics.Process::get_MainModule()
				IL_0035: callvirt instance string [System]System.Diagnostics.ProcessModule::get_FileName()
				IL_003a: ldloc.1
				IL_003b: callvirt instance class [System]System.Diagnostics.ProcessModule [System]System.Diagnostics.Process::get_MainModule()
				IL_0040: callvirt instance string [System]System.Diagnostics.ProcessModule::get_FileName()
				IL_0045: callvirt instance bool [mscorlib]System.String::Equals(string)
				IL_004a: brfalse.s IL_00a3

				IL_004c: ldloc.s 4
				IL_004e: callvirt instance native int [System]System.Diagnostics.Process::get_MainWindowHandle()
				IL_0053: stloc.0
				IL_0054: ldloc.0
				IL_0055: ldsfld native int [mscorlib]System.IntPtr::Zero
				IL_005a: call bool [mscorlib]System.IntPtr::op_Equality(native int, native int)
				IL_005f: brfalse.s IL_0096

				IL_0061: ldnull
				IL_0062: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
				IL_0067: call native int Philips.PSG.Gateway.InstanceManager/SingleInstanceImplementor::FindWindow(string, string)
				IL_006c: stloc.s 5
				IL_006e: ldloc.s 5
				IL_0070: ldsfld native int [mscorlib]System.IntPtr::Zero
				IL_0075: call bool [mscorlib]System.IntPtr::op_Inequality(native int, native int)
				IL_007a: brfalse.s IL_0096

				IL_007c: ldloc.s 5
				IL_007e: ldloca.s 6
				IL_0080: call uint32 Philips.PSG.Gateway.InstanceManager/SingleInstanceImplementor::GetWindowThreadProcessId(native int, uint32&)
				IL_0085: pop
				IL_0086: ldloc.s 6
				IL_0088: conv.u8
				IL_0089: ldloc.s 4
				IL_008b: callvirt instance int32 [System]System.Diagnostics.Process::get_Id()
				IL_0090: conv.i8
				IL_0091: bne.un.s IL_0096

				IL_0093: ldloc.s 5
				IL_0095: stloc.0

				IL_0096: ldloc.0
				IL_0097: ldsfld native int [mscorlib]System.IntPtr::Zero
				IL_009c: call bool [mscorlib]System.IntPtr::op_Inequality(native int, native int)
				IL_00a1: brtrue.s IL_00b0

				IL_00a3: ldloc.3
				IL_00a4: ldc.i4.1
				IL_00a5: add
				IL_00a6: stloc.3

				IL_00a7: ldloc.3
				IL_00a8: ldloc.2
				IL_00a9: ldlen
				IL_00aa: conv.i4
				IL_00ab: blt IL_001f
			// end loop

			IL_00b0: ldloc.0
			IL_00b1: ret
		} // end of method SingleInstanceImplementor::GetExistingWindowHandle

	} // end of class SingleInstanceImplementor


	// Methods
	.method public hidebysig static 
		valuetype Philips.PSG.Gateway.InstanceManager/RunningState GetRunningState () cil managed 
	{
		// Method begins at RVA 0x85c4
		// Header size: 12
		// Code size: 15 (0xf)
		.maxstack 1
		.locals init (
			[0] valuetype Philips.PSG.Gateway.InstanceManager/RunningState
		)

		IL_0000: ldc.i4.0
		IL_0001: stloc.0
		.try
		{
			IL_0002: call valuetype Philips.PSG.Gateway.InstanceManager/RunningState Philips.PSG.Gateway.InstanceManager/SingleInstanceImplementor::GetRunningState()
			IL_0007: stloc.0
			IL_0008: leave.s IL_000d
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_000a: pop
			IL_000b: leave.s IL_000d
		} // end handler

		IL_000d: ldloc.0
		IL_000e: ret
	} // end of method InstanceManager::GetRunningState

	.method public hidebysig static 
		void SwitchToExistingInstance () cil managed 
	{
		// Method begins at RVA 0x85f0
		// Header size: 12
		// Code size: 11 (0xb)
		.maxstack 1

		.try
		{
			IL_0000: call void Philips.PSG.Gateway.InstanceManager/SingleInstanceImplementor::SwitchToExistingInstance()
			IL_0005: leave.s IL_000a
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_0007: pop
			IL_0008: leave.s IL_000a
		} // end handler

		IL_000a: ret
	} // end of method InstanceManager::SwitchToExistingInstance

	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x8618
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [mscorlib]System.Object::.ctor()
		IL_0006: ret
	} // end of method InstanceManager::.ctor

} // end of class Philips.PSG.Gateway.InstanceManager

.class private auto ansi abstract sealed beforefieldinit Philips.PSG.Gateway.Program
	extends [mscorlib]System.Object
{
	// Methods
	.method private hidebysig static pinvokeimpl("kernel32.dll" winapi) 
		bool AllocConsole () cil managed preservesig 
	{
	} // end of method Program::AllocConsole

	.method private hidebysig static 
		void Main (
			string[] args
		) cil managed 
	{
		.custom instance void [mscorlib]System.STAThreadAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x8620
		// Header size: 12
		// Code size: 207 (0xcf)
		.maxstack 3
		.entrypoint
		.locals init (
			[0] class Philips.PSG.Gateway.MainForm
		)

		IL_0000: call void [System.Windows.Forms]System.Windows.Forms.Application::EnableVisualStyles()
		IL_0005: ldc.i4.0
		IL_0006: call void [System.Windows.Forms]System.Windows.Forms.Application::SetCompatibleTextRenderingDefault(bool)
		IL_000b: call valuetype Philips.PSG.Gateway.InstanceManager/RunningState Philips.PSG.Gateway.InstanceManager::GetRunningState()
		IL_0010: pop
		IL_0011: ldarg.0
		IL_0012: ldlen
		IL_0013: brtrue.s IL_002a

		IL_0015: ldstr "Please enter a valid command."
		IL_001a: call void [mscorlib]System.Console::WriteLine(string)
		IL_001f: ldstr "Press any key to continue"
		IL_0024: call void [mscorlib]System.Console::WriteLine(string)
		IL_0029: ret

		IL_002a: ldarg.0
		IL_002b: ldlen
		IL_002c: conv.i4
		IL_002d: ldc.i4.1
		IL_002e: bne.un IL_00ba

		IL_0033: ldarg.0
		IL_0034: ldc.i4.0
		IL_0035: ldelem.ref
		IL_0036: callvirt instance string [mscorlib]System.Object::ToString()
		IL_003b: callvirt instance string [mscorlib]System.String::ToUpper()
		IL_0040: callvirt instance string [mscorlib]System.String::Trim()
		IL_0045: ldstr "BUILD"
		IL_004a: call bool [mscorlib]System.String::op_Equality(string, string)
		IL_004f: brtrue.s IL_006f

		IL_0051: ldarg.0
		IL_0052: ldc.i4.0
		IL_0053: ldelem.ref
		IL_0054: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0059: callvirt instance string [mscorlib]System.String::ToUpper()
		IL_005e: callvirt instance string [mscorlib]System.String::Trim()
		IL_0063: ldstr "PLAY"
		IL_0068: call bool [mscorlib]System.String::op_Equality(string, string)
		IL_006d: brfalse.s IL_00a5

		IL_006f: newobj instance void Philips.PSG.Gateway.MainForm::.ctor()
		IL_0074: stloc.0
		IL_0075: ldarg.0
		IL_0076: ldc.i4.0
		IL_0077: ldelem.ref
		IL_0078: callvirt instance string [mscorlib]System.Object::ToString()
		IL_007d: callvirt instance string [mscorlib]System.String::ToUpper()
		IL_0082: callvirt instance string [mscorlib]System.String::Trim()
		IL_0087: ldstr "BUILD"
		IL_008c: call bool [mscorlib]System.String::op_Equality(string, string)
		IL_0091: brfalse.s IL_009c

		IL_0093: ldloc.0
		IL_0094: ldnull
		IL_0095: ldnull
		IL_0096: callvirt instance void Philips.PSG.Gateway.MainForm::BuildButton_Click(object, class [mscorlib]System.EventArgs)
		IL_009b: ret

		IL_009c: ldloc.0
		IL_009d: ldnull
		IL_009e: ldnull
		IL_009f: callvirt instance void Philips.PSG.Gateway.MainForm::PlayPauseButton_Click(object, class [mscorlib]System.EventArgs)
		IL_00a4: ret

		IL_00a5: ldstr "Please enter a valid command."
		IL_00aa: call void [mscorlib]System.Console::WriteLine(string)
		IL_00af: ldstr "Press any key to continue"
		IL_00b4: call void [mscorlib]System.Console::WriteLine(string)
		IL_00b9: ret

		IL_00ba: ldstr "Please enter a valid command."
		IL_00bf: call void [mscorlib]System.Console::WriteLine(string)
		IL_00c4: ldstr "Press any key to continue"
		IL_00c9: call void [mscorlib]System.Console::WriteLine(string)
		IL_00ce: ret
	} // end of method Program::Main

} // end of class Philips.PSG.Gateway.Program

.class private auto ansi beforefieldinit Philips.PSG.Gateway.Resources
	extends [mscorlib]System.Object
{
	.custom instance void [System]System.CodeDom.Compiler.GeneratedCodeAttribute::.ctor(string, string) = (
		01 00 33 53 79 73 74 65 6d 2e 52 65 73 6f 75 72
		63 65 73 2e 54 6f 6f 6c 73 2e 53 74 72 6f 6e 67
		6c 79 54 79 70 65 64 52 65 73 6f 75 72 63 65 42
		75 69 6c 64 65 72 08 31 35 2e 30 2e 30 2e 30 00 00
	)
	.custom instance void [mscorlib]System.Diagnostics.DebuggerNonUserCodeAttribute::.ctor() = (
		01 00 00 00
	)
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	// Fields
	.field private static class [mscorlib]System.Resources.ResourceManager resourceMan
	.field private static class [mscorlib]System.Globalization.CultureInfo resourceCulture

	// Methods
	.method assembly hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x8618
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [mscorlib]System.Object::.ctor()
		IL_0006: ret
	} // end of method Resources::.ctor

	.method assembly hidebysig specialname static 
		class [mscorlib]System.Resources.ResourceManager get_ResourceManager () cil managed 
	{
		// Method begins at RVA 0x86fb
		// Header size: 1
		// Code size: 43 (0x2b)
		.maxstack 8

		IL_0000: ldsfld class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::resourceMan
		IL_0005: brtrue.s IL_0025

		IL_0007: ldstr "Philips.PSG.Gateway.Resources"
		IL_000c: ldtoken Philips.PSG.Gateway.Resources
		IL_0011: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_0016: callvirt instance class [mscorlib]System.Reflection.Assembly [mscorlib]System.Type::get_Assembly()
		IL_001b: newobj instance void [mscorlib]System.Resources.ResourceManager::.ctor(string, class [mscorlib]System.Reflection.Assembly)
		IL_0020: stsfld class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::resourceMan

		IL_0025: ldsfld class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::resourceMan
		IL_002a: ret
	} // end of method Resources::get_ResourceManager

	.method assembly hidebysig specialname static 
		class [mscorlib]System.Globalization.CultureInfo get_Culture () cil managed 
	{
		// Method begins at RVA 0x8727
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_0005: ret
	} // end of method Resources::get_Culture

	.method assembly hidebysig specialname static 
		void set_Culture (
			class [mscorlib]System.Globalization.CultureInfo 'value'
		) cil managed 
	{
		// Method begins at RVA 0x872e
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: stsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_0006: ret
	} // end of method Resources::set_Culture

	.method assembly hidebysig specialname static 
		string get_BTN_Browse () cil managed 
	{
		// Method begins at RVA 0x8736
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_Browse"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_Browse

	.method assembly hidebysig specialname static 
		string get_BTN_BuildSchedule () cil managed 
	{
		// Method begins at RVA 0x874c
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_BuildSchedule"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_BuildSchedule

	.method assembly hidebysig specialname static 
		string get_BTN_BuildTs () cil managed 
	{
		// Method begins at RVA 0x8762
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_BuildTs"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_BuildTs

	.method assembly hidebysig specialname static 
		string get_BTN_Cancel () cil managed 
	{
		// Method begins at RVA 0x8778
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_Cancel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_Cancel

	.method assembly hidebysig specialname static 
		string get_BTN_CopyScheduleEvent () cil managed 
	{
		// Method begins at RVA 0x878e
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_CopyScheduleEvent"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_CopyScheduleEvent

	.method assembly hidebysig specialname static 
		string get_BTN_DeleteScheduleEvent () cil managed 
	{
		// Method begins at RVA 0x87a4
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_DeleteScheduleEvent"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_DeleteScheduleEvent

	.method assembly hidebysig specialname static 
		string get_BTN_EditScheduleEvent () cil managed 
	{
		// Method begins at RVA 0x87ba
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_EditScheduleEvent"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_EditScheduleEvent

	.method assembly hidebysig specialname static 
		string get_BTN_MoveScheduleEventDown () cil managed 
	{
		// Method begins at RVA 0x87d0
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_MoveScheduleEventDown"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_MoveScheduleEventDown

	.method assembly hidebysig specialname static 
		string get_BTN_MoveScheduleEventUp () cil managed 
	{
		// Method begins at RVA 0x87e6
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_MoveScheduleEventUp"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_MoveScheduleEventUp

	.method assembly hidebysig specialname static 
		string get_BTN_OK () cil managed 
	{
		// Method begins at RVA 0x87fc
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_OK"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_OK

	.method assembly hidebysig specialname static 
		string get_BTN_PauseSchedule () cil managed 
	{
		// Method begins at RVA 0x8812
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_PauseSchedule"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_PauseSchedule

	.method assembly hidebysig specialname static 
		string get_BTN_PlayTs () cil managed 
	{
		// Method begins at RVA 0x8828
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_PlayTs"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_PlayTs

	.method assembly hidebysig specialname static 
		string get_BTN_RunSchedule () cil managed 
	{
		// Method begins at RVA 0x883e
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_RunSchedule"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_RunSchedule

	.method assembly hidebysig specialname static 
		string get_BTN_StopTs () cil managed 
	{
		// Method begins at RVA 0x8854
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_StopTs"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_StopTs

	.method assembly hidebysig specialname static 
		string get_BTN_ViewBuildLog () cil managed 
	{
		// Method begins at RVA 0x886a
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "BTN_ViewBuildLog"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_BTN_ViewBuildLog

	.method assembly hidebysig specialname static 
		string get_DLG_AboutTitle () cil managed 
	{
		// Method begins at RVA 0x8880
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_AboutTitle"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_AboutTitle

	.method assembly hidebysig specialname static 
		string get_DLG_AboutVersion () cil managed 
	{
		// Method begins at RVA 0x8896
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_AboutVersion"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_AboutVersion

	.method assembly hidebysig specialname static 
		string get_DLG_BuildActionCopyingMessage () cil managed 
	{
		// Method begins at RVA 0x88ac
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildActionCopyingMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildActionCopyingMessage

	.method assembly hidebysig specialname static 
		string get_DLG_BuildActionGeneratingTransportStreamMessage () cil managed 
	{
		// Method begins at RVA 0x88c2
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildActionGeneratingTransportStreamMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildActionGeneratingTransportStreamMessage

	.method assembly hidebysig specialname static 
		string get_DLG_BuildActionMergingTransportStreamMessage () cil managed 
	{
		// Method begins at RVA 0x88d8
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildActionMergingTransportStreamMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildActionMergingTransportStreamMessage

	.method assembly hidebysig specialname static 
		string get_DLG_BuildActionPreparingChannelTableMessage () cil managed 
	{
		// Method begins at RVA 0x88ee
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildActionPreparingChannelTableMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildActionPreparingChannelTableMessage

	.method assembly hidebysig specialname static 
		string get_DLG_BuildActionPreparingFoldersMessage () cil managed 
	{
		// Method begins at RVA 0x8904
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildActionPreparingFoldersMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildActionPreparingFoldersMessage

	.method assembly hidebysig specialname static 
		string get_DLG_BuildActionPreparingNvmMessage () cil managed 
	{
		// Method begins at RVA 0x891a
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildActionPreparingNvmMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildActionPreparingNvmMessage

	.method assembly hidebysig specialname static 
		string get_DLG_BuildActionPreparingThemeMessage () cil managed 
	{
		// Method begins at RVA 0x8930
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildActionPreparingThemeMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildActionPreparingThemeMessage

	.method assembly hidebysig specialname static 
		string get_DLG_BuildActionStarting () cil managed 
	{
		// Method begins at RVA 0x8946
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildActionStarting"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildActionStarting

	.method assembly hidebysig specialname static 
		string get_DLG_BuildFailedMessage () cil managed 
	{
		// Method begins at RVA 0x895c
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildFailedMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildFailedMessage

	.method assembly hidebysig specialname static 
		string get_DLG_BuildProgressTitle () cil managed 
	{
		// Method begins at RVA 0x8972
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildProgressTitle"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildProgressTitle

	.method assembly hidebysig specialname static 
		string get_DLG_BuildSuccessfulMessage () cil managed 
	{
		// Method begins at RVA 0x8988
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildSuccessfulMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildSuccessfulMessage

	.method assembly hidebysig specialname static 
		string get_DLG_BuildTargetTvModelMessage () cil managed 
	{
		// Method begins at RVA 0x899e
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_BuildTargetTvModelMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_BuildTargetTvModelMessage

	.method assembly hidebysig specialname static 
		string get_DLG_CatalogCreateFailedMessage () cil managed 
	{
		// Method begins at RVA 0x89b4
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_CatalogCreateFailedMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_CatalogCreateFailedMessage

	.method assembly hidebysig specialname static 
		string get_DLG_CatalogEntryInvalidMultipleFiles () cil managed 
	{
		// Method begins at RVA 0x89ca
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_CatalogEntryInvalidMultipleFiles"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_CatalogEntryInvalidMultipleFiles

	.method assembly hidebysig specialname static 
		string get_DLG_CatalogEntryInvalidPartialFileSet () cil managed 
	{
		// Method begins at RVA 0x89e0
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_CatalogEntryInvalidPartialFileSet"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_CatalogEntryInvalidPartialFileSet

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigCreateFailedMessage () cil managed 
	{
		// Method begins at RVA 0x89f6
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigCreateFailedMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigCreateFailedMessage

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigSaveFailedMessage () cil managed 
	{
		// Method begins at RVA 0x8a0c
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigSaveFailedMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigSaveFailedMessage

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationBuildRootFolderLabel () cil managed 
	{
		// Method begins at RVA 0x8a22
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationBuildRootFolderLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationBuildRootFolderLabel

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationConfirmOutputChannelMessage () cil managed 
	{
		// Method begins at RVA 0x8a38
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationConfirmOutputChannelMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationConfirmOutputChannelMessage

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationConstellationLabel () cil managed 
	{
		// Method begins at RVA 0x8a4e
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationConstellationLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationConstellationLabel

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationFrequencyFormat () cil managed 
	{
		// Method begins at RVA 0x8a64
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationFrequencyFormat"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationFrequencyFormat

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationFrequencyLabel () cil managed 
	{
		// Method begins at RVA 0x8a7a
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationFrequencyLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationFrequencyLabel

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationFrequencyUnit () cil managed 
	{
		// Method begins at RVA 0x8a90
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationFrequencyUnit"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationFrequencyUnit

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationNotDefaultOutputChannelLabel () cil managed 
	{
		// Method begins at RVA 0x8aa6
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationNotDefaultOutputChannelLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationNotDefaultOutputChannelLabel

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationNotDefaultOutputFrequencyLabel () cil managed 
	{
		// Method begins at RVA 0x8abc
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationNotDefaultOutputFrequencyLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationNotDefaultOutputFrequencyLabel

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationOutputChannelLabel () cil managed 
	{
		// Method begins at RVA 0x8ad2
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationOutputChannelLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationOutputChannelLabel

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationSelectBuildRootFolderMessage () cil managed 
	{
		// Method begins at RVA 0x8ae8
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationSelectBuildRootFolderMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationSelectBuildRootFolderMessage

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationSelectWorkingRootFolderMessage () cil managed 
	{
		// Method begins at RVA 0x8afe
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationSelectWorkingRootFolderMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationSelectWorkingRootFolderMessage

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationTitle () cil managed 
	{
		// Method begins at RVA 0x8b14
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationTitle"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationTitle

	.method assembly hidebysig specialname static 
		string get_DLG_ConfigurationWorkingRootFolderLabel () cil managed 
	{
		// Method begins at RVA 0x8b2a
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ConfigurationWorkingRootFolderLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ConfigurationWorkingRootFolderLabel

	.method assembly hidebysig specialname static 
		string get_DLG_DekTecConfigurationCreateFailedMessage () cil managed 
	{
		// Method begins at RVA 0x8b40
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_DekTecConfigurationCreateFailedMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_DekTecConfigurationCreateFailedMessage

	.method assembly hidebysig specialname static 
		string get_DLG_DekTecDeviceAttachFailedMessage () cil managed 
	{
		// Method begins at RVA 0x8b56
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_DekTecDeviceAttachFailedMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_DekTecDeviceAttachFailedMessage

	.method assembly hidebysig specialname static 
		string get_DLG_DekTecDeviceNotAvailable () cil managed 
	{
		// Method begins at RVA 0x8b6c
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_DekTecDeviceNotAvailable"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_DekTecDeviceNotAvailable

	.method assembly hidebysig specialname static 
		string get_DLG_DekTecDeviceScanFailedMessage () cil managed 
	{
		// Method begins at RVA 0x8b82
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_DekTecDeviceScanFailedMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_DekTecDeviceScanFailedMessage

	.method assembly hidebysig specialname static 
		string get_DLG_EditEndTimeLabel () cil managed 
	{
		// Method begins at RVA 0x8b98
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_EditEndTimeLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_EditEndTimeLabel

	.method assembly hidebysig specialname static 
		string get_DLG_EditEventTitle () cil managed 
	{
		// Method begins at RVA 0x8bae
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_EditEventTitle"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_EditEventTitle

	.method assembly hidebysig specialname static 
		string get_DLG_EditInvalidTimesMessage () cil managed 
	{
		// Method begins at RVA 0x8bc4
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_EditInvalidTimesMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_EditInvalidTimesMessage

	.method assembly hidebysig specialname static 
		string get_DLG_EditPlaybackFileLabel () cil managed 
	{
		// Method begins at RVA 0x8bda
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_EditPlaybackFileLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_EditPlaybackFileLabel

	.method assembly hidebysig specialname static 
		string get_DLG_EditStartTimeLabel () cil managed 
	{
		// Method begins at RVA 0x8bf0
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_EditStartTimeLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_EditStartTimeLabel

	.method assembly hidebysig specialname static 
		string get_DLG_EditTvModelLabel () cil managed 
	{
		// Method begins at RVA 0x8c06
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_EditTvModelLabel"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_EditTvModelLabel

	.method assembly hidebysig specialname static 
		string get_DLG_InvalidScheduleFooterMessage () cil managed 
	{
		// Method begins at RVA 0x8c1c
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_InvalidScheduleFooterMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_InvalidScheduleFooterMessage

	.method assembly hidebysig specialname static 
		string get_DLG_InvalidScheduleHeaderMessage () cil managed 
	{
		// Method begins at RVA 0x8c32
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_InvalidScheduleHeaderMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_InvalidScheduleHeaderMessage

	.method assembly hidebysig specialname static 
		string get_DLG_MissingAssemblyOrLibraryMessage () cil managed 
	{
		// Method begins at RVA 0x8c48
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_MissingAssemblyOrLibraryMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_MissingAssemblyOrLibraryMessage

	.method assembly hidebysig specialname static 
		string get_DLG_PreBuildFailedMessage () cil managed 
	{
		// Method begins at RVA 0x8c5e
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_PreBuildFailedMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_PreBuildFailedMessage

	.method assembly hidebysig specialname static 
		string get_DLG_RunningByOtherUserMessage () cil managed 
	{
		// Method begins at RVA 0x8c74
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_RunningByOtherUserMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_RunningByOtherUserMessage

	.method assembly hidebysig specialname static 
		string get_DLG_ScheduleCreateFailedMessage () cil managed 
	{
		// Method begins at RVA 0x8c8a
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ScheduleCreateFailedMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ScheduleCreateFailedMessage

	.method assembly hidebysig specialname static 
		string get_DLG_ScheduleSaveFailedMessage () cil managed 
	{
		// Method begins at RVA 0x8ca0
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "DLG_ScheduleSaveFailedMessage"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_DLG_ScheduleSaveFailedMessage

	.method assembly hidebysig specialname static 
		string get_GLOBAL_AppName () cil managed 
	{
		// Method begins at RVA 0x8cb6
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "GLOBAL_AppName"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_GLOBAL_AppName

	.method assembly hidebysig specialname static 
		string get_GRID_EndTimeColumnHeader () cil managed 
	{
		// Method begins at RVA 0x8ccc
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "GRID_EndTimeColumnHeader"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_GRID_EndTimeColumnHeader

	.method assembly hidebysig specialname static 
		string get_GRID_PlaybackFileColumnHeader () cil managed 
	{
		// Method begins at RVA 0x8ce2
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "GRID_PlaybackFileColumnHeader"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_GRID_PlaybackFileColumnHeader

	.method assembly hidebysig specialname static 
		string get_GRID_StartTimeColumnHeader () cil managed 
	{
		// Method begins at RVA 0x8cf8
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "GRID_StartTimeColumnHeader"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_GRID_StartTimeColumnHeader

	.method assembly hidebysig specialname static 
		string get_GRID_TvModelColumnHeader () cil managed 
	{
		// Method begins at RVA 0x8d0e
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "GRID_TvModelColumnHeader"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_GRID_TvModelColumnHeader

	.method assembly hidebysig specialname static 
		class [System.Drawing]System.Drawing.Icon get_Icon () cil managed 
	{
		// Method begins at RVA 0x8d24
		// Header size: 1
		// Code size: 26 (0x1a)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "Icon"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance object [mscorlib]System.Resources.ResourceManager::GetObject(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: castclass [System.Drawing]System.Drawing.Icon
		IL_0019: ret
	} // end of method Resources::get_Icon

	.method assembly hidebysig specialname static 
		string get_LBL_DekTecDeviceAvailable () cil managed 
	{
		// Method begins at RVA 0x8d3f
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "LBL_DekTecDeviceAvailable"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_LBL_DekTecDeviceAvailable

	.method assembly hidebysig specialname static 
		string get_LBL_DekTecDeviceConfiguration () cil managed 
	{
		// Method begins at RVA 0x8d55
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "LBL_DekTecDeviceConfiguration"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_LBL_DekTecDeviceConfiguration

	.method assembly hidebysig specialname static 
		string get_LBL_DekTecDeviceNotAvailable () cil managed 
	{
		// Method begins at RVA 0x8d6b
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "LBL_DekTecDeviceNotAvailable"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_LBL_DekTecDeviceNotAvailable

	.method assembly hidebysig specialname static 
		string get_LBL_SchedulePriorityAxis () cil managed 
	{
		// Method begins at RVA 0x8d81
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "LBL_SchedulePriorityAxis"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_LBL_SchedulePriorityAxis

	.method assembly hidebysig specialname static 
		class [System.Drawing]System.Drawing.Bitmap get_Logo () cil managed 
	{
		// Method begins at RVA 0x8d97
		// Header size: 1
		// Code size: 26 (0x1a)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "Logo"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance object [mscorlib]System.Resources.ResourceManager::GetObject(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: castclass [System.Drawing]System.Drawing.Bitmap
		IL_0019: ret
	} // end of method Resources::get_Logo

	.method assembly hidebysig specialname static 
		string get_MENU_File () cil managed 
	{
		// Method begins at RVA 0x8db2
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "MENU_File"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_MENU_File

	.method assembly hidebysig specialname static 
		string get_MENU_FileConfiguration () cil managed 
	{
		// Method begins at RVA 0x8dc8
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "MENU_FileConfiguration"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_MENU_FileConfiguration

	.method assembly hidebysig specialname static 
		string get_MENU_FileExit () cil managed 
	{
		// Method begins at RVA 0x8dde
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "MENU_FileExit"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_MENU_FileExit

	.method assembly hidebysig specialname static 
		string get_MENU_Help () cil managed 
	{
		// Method begins at RVA 0x8df4
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "MENU_Help"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_MENU_Help

	.method assembly hidebysig specialname static 
		string get_MENU_HelpAbout () cil managed 
	{
		// Method begins at RVA 0x8e0a
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "MENU_HelpAbout"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance string [mscorlib]System.Resources.ResourceManager::GetString(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: ret
	} // end of method Resources::get_MENU_HelpAbout

	.method assembly hidebysig specialname static 
		class [System.Drawing]System.Drawing.Bitmap get_Philips () cil managed 
	{
		// Method begins at RVA 0x8e20
		// Header size: 1
		// Code size: 26 (0x1a)
		.maxstack 8

		IL_0000: call class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
		IL_0005: ldstr "Philips"
		IL_000a: ldsfld class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::resourceCulture
		IL_000f: callvirt instance object [mscorlib]System.Resources.ResourceManager::GetObject(string, class [mscorlib]System.Globalization.CultureInfo)
		IL_0014: castclass [System.Drawing]System.Drawing.Bitmap
		IL_0019: ret
	} // end of method Resources::get_Philips

	// Properties
	.property class [mscorlib]System.Resources.ResourceManager ResourceManager()
	{
		.custom instance void [System]System.ComponentModel.EditorBrowsableAttribute::.ctor(valuetype [System]System.ComponentModel.EditorBrowsableState) = (
			01 00 02 00 00 00 00 00
		)
		.get class [mscorlib]System.Resources.ResourceManager Philips.PSG.Gateway.Resources::get_ResourceManager()
	}
	.property class [mscorlib]System.Globalization.CultureInfo Culture()
	{
		.custom instance void [System]System.ComponentModel.EditorBrowsableAttribute::.ctor(valuetype [System]System.ComponentModel.EditorBrowsableState) = (
			01 00 02 00 00 00 00 00
		)
		.get class [mscorlib]System.Globalization.CultureInfo Philips.PSG.Gateway.Resources::get_Culture()
		.set void Philips.PSG.Gateway.Resources::set_Culture(class [mscorlib]System.Globalization.CultureInfo)
	}
	.property string BTN_Browse()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_Browse()
	}
	.property string BTN_BuildSchedule()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_BuildSchedule()
	}
	.property string BTN_BuildTs()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_BuildTs()
	}
	.property string BTN_Cancel()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_Cancel()
	}
	.property string BTN_CopyScheduleEvent()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_CopyScheduleEvent()
	}
	.property string BTN_DeleteScheduleEvent()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_DeleteScheduleEvent()
	}
	.property string BTN_EditScheduleEvent()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_EditScheduleEvent()
	}
	.property string BTN_MoveScheduleEventDown()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_MoveScheduleEventDown()
	}
	.property string BTN_MoveScheduleEventUp()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_MoveScheduleEventUp()
	}
	.property string BTN_OK()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_OK()
	}
	.property string BTN_PauseSchedule()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_PauseSchedule()
	}
	.property string BTN_PlayTs()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_PlayTs()
	}
	.property string BTN_RunSchedule()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_RunSchedule()
	}
	.property string BTN_StopTs()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_StopTs()
	}
	.property string BTN_ViewBuildLog()
	{
		.get string Philips.PSG.Gateway.Resources::get_BTN_ViewBuildLog()
	}
	.property string DLG_AboutTitle()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_AboutTitle()
	}
	.property string DLG_AboutVersion()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_AboutVersion()
	}
	.property string DLG_BuildActionCopyingMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildActionCopyingMessage()
	}
	.property string DLG_BuildActionGeneratingTransportStreamMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildActionGeneratingTransportStreamMessage()
	}
	.property string DLG_BuildActionMergingTransportStreamMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildActionMergingTransportStreamMessage()
	}
	.property string DLG_BuildActionPreparingChannelTableMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildActionPreparingChannelTableMessage()
	}
	.property string DLG_BuildActionPreparingFoldersMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildActionPreparingFoldersMessage()
	}
	.property string DLG_BuildActionPreparingNvmMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildActionPreparingNvmMessage()
	}
	.property string DLG_BuildActionPreparingThemeMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildActionPreparingThemeMessage()
	}
	.property string DLG_BuildActionStarting()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildActionStarting()
	}
	.property string DLG_BuildFailedMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildFailedMessage()
	}
	.property string DLG_BuildProgressTitle()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildProgressTitle()
	}
	.property string DLG_BuildSuccessfulMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildSuccessfulMessage()
	}
	.property string DLG_BuildTargetTvModelMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_BuildTargetTvModelMessage()
	}
	.property string DLG_CatalogCreateFailedMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_CatalogCreateFailedMessage()
	}
	.property string DLG_CatalogEntryInvalidMultipleFiles()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_CatalogEntryInvalidMultipleFiles()
	}
	.property string DLG_CatalogEntryInvalidPartialFileSet()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_CatalogEntryInvalidPartialFileSet()
	}
	.property string DLG_ConfigCreateFailedMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigCreateFailedMessage()
	}
	.property string DLG_ConfigSaveFailedMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigSaveFailedMessage()
	}
	.property string DLG_ConfigurationBuildRootFolderLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationBuildRootFolderLabel()
	}
	.property string DLG_ConfigurationConfirmOutputChannelMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationConfirmOutputChannelMessage()
	}
	.property string DLG_ConfigurationConstellationLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationConstellationLabel()
	}
	.property string DLG_ConfigurationFrequencyFormat()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationFrequencyFormat()
	}
	.property string DLG_ConfigurationFrequencyLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationFrequencyLabel()
	}
	.property string DLG_ConfigurationFrequencyUnit()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationFrequencyUnit()
	}
	.property string DLG_ConfigurationNotDefaultOutputChannelLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationNotDefaultOutputChannelLabel()
	}
	.property string DLG_ConfigurationNotDefaultOutputFrequencyLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationNotDefaultOutputFrequencyLabel()
	}
	.property string DLG_ConfigurationOutputChannelLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationOutputChannelLabel()
	}
	.property string DLG_ConfigurationSelectBuildRootFolderMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationSelectBuildRootFolderMessage()
	}
	.property string DLG_ConfigurationSelectWorkingRootFolderMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationSelectWorkingRootFolderMessage()
	}
	.property string DLG_ConfigurationTitle()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationTitle()
	}
	.property string DLG_ConfigurationWorkingRootFolderLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ConfigurationWorkingRootFolderLabel()
	}
	.property string DLG_DekTecConfigurationCreateFailedMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_DekTecConfigurationCreateFailedMessage()
	}
	.property string DLG_DekTecDeviceAttachFailedMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_DekTecDeviceAttachFailedMessage()
	}
	.property string DLG_DekTecDeviceNotAvailable()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_DekTecDeviceNotAvailable()
	}
	.property string DLG_DekTecDeviceScanFailedMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_DekTecDeviceScanFailedMessage()
	}
	.property string DLG_EditEndTimeLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_EditEndTimeLabel()
	}
	.property string DLG_EditEventTitle()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_EditEventTitle()
	}
	.property string DLG_EditInvalidTimesMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_EditInvalidTimesMessage()
	}
	.property string DLG_EditPlaybackFileLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_EditPlaybackFileLabel()
	}
	.property string DLG_EditStartTimeLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_EditStartTimeLabel()
	}
	.property string DLG_EditTvModelLabel()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_EditTvModelLabel()
	}
	.property string DLG_InvalidScheduleFooterMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_InvalidScheduleFooterMessage()
	}
	.property string DLG_InvalidScheduleHeaderMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_InvalidScheduleHeaderMessage()
	}
	.property string DLG_MissingAssemblyOrLibraryMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_MissingAssemblyOrLibraryMessage()
	}
	.property string DLG_PreBuildFailedMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_PreBuildFailedMessage()
	}
	.property string DLG_RunningByOtherUserMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_RunningByOtherUserMessage()
	}
	.property string DLG_ScheduleCreateFailedMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ScheduleCreateFailedMessage()
	}
	.property string DLG_ScheduleSaveFailedMessage()
	{
		.get string Philips.PSG.Gateway.Resources::get_DLG_ScheduleSaveFailedMessage()
	}
	.property string GLOBAL_AppName()
	{
		.get string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
	}
	.property string GRID_EndTimeColumnHeader()
	{
		.get string Philips.PSG.Gateway.Resources::get_GRID_EndTimeColumnHeader()
	}
	.property string GRID_PlaybackFileColumnHeader()
	{
		.get string Philips.PSG.Gateway.Resources::get_GRID_PlaybackFileColumnHeader()
	}
	.property string GRID_StartTimeColumnHeader()
	{
		.get string Philips.PSG.Gateway.Resources::get_GRID_StartTimeColumnHeader()
	}
	.property string GRID_TvModelColumnHeader()
	{
		.get string Philips.PSG.Gateway.Resources::get_GRID_TvModelColumnHeader()
	}
	.property class [System.Drawing]System.Drawing.Icon Icon()
	{
		.get class [System.Drawing]System.Drawing.Icon Philips.PSG.Gateway.Resources::get_Icon()
	}
	.property string LBL_DekTecDeviceAvailable()
	{
		.get string Philips.PSG.Gateway.Resources::get_LBL_DekTecDeviceAvailable()
	}
	.property string LBL_DekTecDeviceConfiguration()
	{
		.get string Philips.PSG.Gateway.Resources::get_LBL_DekTecDeviceConfiguration()
	}
	.property string LBL_DekTecDeviceNotAvailable()
	{
		.get string Philips.PSG.Gateway.Resources::get_LBL_DekTecDeviceNotAvailable()
	}
	.property string LBL_SchedulePriorityAxis()
	{
		.get string Philips.PSG.Gateway.Resources::get_LBL_SchedulePriorityAxis()
	}
	.property class [System.Drawing]System.Drawing.Bitmap Logo()
	{
		.get class [System.Drawing]System.Drawing.Bitmap Philips.PSG.Gateway.Resources::get_Logo()
	}
	.property string MENU_File()
	{
		.get string Philips.PSG.Gateway.Resources::get_MENU_File()
	}
	.property string MENU_FileConfiguration()
	{
		.get string Philips.PSG.Gateway.Resources::get_MENU_FileConfiguration()
	}
	.property string MENU_FileExit()
	{
		.get string Philips.PSG.Gateway.Resources::get_MENU_FileExit()
	}
	.property string MENU_Help()
	{
		.get string Philips.PSG.Gateway.Resources::get_MENU_Help()
	}
	.property string MENU_HelpAbout()
	{
		.get string Philips.PSG.Gateway.Resources::get_MENU_HelpAbout()
	}
	.property class [System.Drawing]System.Drawing.Bitmap Philips()
	{
		.get class [System.Drawing]System.Drawing.Bitmap Philips.PSG.Gateway.Resources::get_Philips()
	}

} // end of class Philips.PSG.Gateway.Resources

.class public auto ansi sealed beforefieldinit Philips.PSG.Gateway.ScheduleManager
	extends [mscorlib]System.Object
{
	// Nested Types
	.class nested public auto ansi sealed ScheduleState
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState NoScheduledEvents = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Paused = int32(1)
		.field public static literal valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Running = int32(2)

	} // end of class ScheduleState

	.class nested public auto ansi sealed ScheduleManagerScheduleChangedDelegate
		extends [mscorlib]System.MulticastDelegate
	{
		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor (
				object 'object',
				native int 'method'
			) runtime managed 
		{
		} // end of method ScheduleManagerScheduleChangedDelegate::.ctor

		.method public hidebysig newslot virtual 
			instance void Invoke (
				class Philips.PSG.Gateway.Schedule schedule
			) runtime managed 
		{
		} // end of method ScheduleManagerScheduleChangedDelegate::Invoke

		.method public hidebysig newslot virtual 
			instance class [mscorlib]System.IAsyncResult BeginInvoke (
				class Philips.PSG.Gateway.Schedule schedule,
				class [mscorlib]System.AsyncCallback callback,
				object 'object'
			) runtime managed 
		{
		} // end of method ScheduleManagerScheduleChangedDelegate::BeginInvoke

		.method public hidebysig newslot virtual 
			instance void EndInvoke (
				class [mscorlib]System.IAsyncResult result
			) runtime managed 
		{
		} // end of method ScheduleManagerScheduleChangedDelegate::EndInvoke

	} // end of class ScheduleManagerScheduleChangedDelegate

	.class nested public auto ansi sealed ScheduleManagerStateChangedDelegate
		extends [mscorlib]System.MulticastDelegate
	{
		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor (
				object 'object',
				native int 'method'
			) runtime managed 
		{
		} // end of method ScheduleManagerStateChangedDelegate::.ctor

		.method public hidebysig newslot virtual 
			instance void Invoke (
				valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState state
			) runtime managed 
		{
		} // end of method ScheduleManagerStateChangedDelegate::Invoke

		.method public hidebysig newslot virtual 
			instance class [mscorlib]System.IAsyncResult BeginInvoke (
				valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState state,
				class [mscorlib]System.AsyncCallback callback,
				object 'object'
			) runtime managed 
		{
		} // end of method ScheduleManagerStateChangedDelegate::BeginInvoke

		.method public hidebysig newslot virtual 
			instance void EndInvoke (
				class [mscorlib]System.IAsyncResult result
			) runtime managed 
		{
		} // end of method ScheduleManagerStateChangedDelegate::EndInvoke

	} // end of class ScheduleManagerStateChangedDelegate

	.class nested public auto ansi sealed ScheduleManagerCurrentEventChangedDelegate
		extends [mscorlib]System.MulticastDelegate
	{
		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor (
				object 'object',
				native int 'method'
			) runtime managed 
		{
		} // end of method ScheduleManagerCurrentEventChangedDelegate::.ctor

		.method public hidebysig newslot virtual 
			instance void Invoke (
				class Philips.PSG.Gateway.ScheduleEvent currentEvent
			) runtime managed 
		{
		} // end of method ScheduleManagerCurrentEventChangedDelegate::Invoke

		.method public hidebysig newslot virtual 
			instance class [mscorlib]System.IAsyncResult BeginInvoke (
				class Philips.PSG.Gateway.ScheduleEvent currentEvent,
				class [mscorlib]System.AsyncCallback callback,
				object 'object'
			) runtime managed 
		{
		} // end of method ScheduleManagerCurrentEventChangedDelegate::BeginInvoke

		.method public hidebysig newslot virtual 
			instance void EndInvoke (
				class [mscorlib]System.IAsyncResult result
			) runtime managed 
		{
		} // end of method ScheduleManagerCurrentEventChangedDelegate::EndInvoke

	} // end of class ScheduleManagerCurrentEventChangedDelegate


	// Fields
	.field private static literal int32 TIMER_TICK_INTERVAL_MS = int32(5000)
	.field private static initonly class Philips.PSG.Gateway.ScheduleManager _instance
	.field private class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate ScheduleChanged
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate StateChanged
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate CurrentEventChanged
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.field private class Philips.PSG.Gateway.Schedule _schedule
	.field private class Philips.PSG.Gateway.ScheduleEvent _currentEvent
	.field private valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState _state
	.field private class [System.Windows.Forms]System.Windows.Forms.Timer _timer

	// Methods
	.method public hidebysig specialname 
		instance void add_ScheduleChanged (
			class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x8e3c
		// Header size: 12
		// Code size: 41 (0x29)
		.maxstack 3
		.locals init (
			[0] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate,
			[1] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate,
			[2] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate Philips.PSG.Gateway.ScheduleManager::ScheduleChanged
		IL_0006: stloc.0
		// loop start (head: IL_0007)
			IL_0007: ldloc.0
			IL_0008: stloc.1
			IL_0009: ldloc.1
			IL_000a: ldarg.1
			IL_000b: call class [mscorlib]System.Delegate [mscorlib]System.Delegate::Combine(class [mscorlib]System.Delegate, class [mscorlib]System.Delegate)
			IL_0010: castclass Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate
			IL_0015: stloc.2
			IL_0016: ldarg.0
			IL_0017: ldflda class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate Philips.PSG.Gateway.ScheduleManager::ScheduleChanged
			IL_001c: ldloc.2
			IL_001d: ldloc.1
			IL_001e: call !!0 [mscorlib]System.Threading.Interlocked::CompareExchange<class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate>(!!0&, !!0, !!0)
			IL_0023: stloc.0
			IL_0024: ldloc.0
			IL_0025: ldloc.1
			IL_0026: bne.un.s IL_0007
		// end loop
		IL_0028: ret
	} // end of method ScheduleManager::add_ScheduleChanged

	.method public hidebysig specialname 
		instance void remove_ScheduleChanged (
			class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x8e74
		// Header size: 12
		// Code size: 41 (0x29)
		.maxstack 3
		.locals init (
			[0] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate,
			[1] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate,
			[2] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate Philips.PSG.Gateway.ScheduleManager::ScheduleChanged
		IL_0006: stloc.0
		// loop start (head: IL_0007)
			IL_0007: ldloc.0
			IL_0008: stloc.1
			IL_0009: ldloc.1
			IL_000a: ldarg.1
			IL_000b: call class [mscorlib]System.Delegate [mscorlib]System.Delegate::Remove(class [mscorlib]System.Delegate, class [mscorlib]System.Delegate)
			IL_0010: castclass Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate
			IL_0015: stloc.2
			IL_0016: ldarg.0
			IL_0017: ldflda class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate Philips.PSG.Gateway.ScheduleManager::ScheduleChanged
			IL_001c: ldloc.2
			IL_001d: ldloc.1
			IL_001e: call !!0 [mscorlib]System.Threading.Interlocked::CompareExchange<class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate>(!!0&, !!0, !!0)
			IL_0023: stloc.0
			IL_0024: ldloc.0
			IL_0025: ldloc.1
			IL_0026: bne.un.s IL_0007
		// end loop
		IL_0028: ret
	} // end of method ScheduleManager::remove_ScheduleChanged

	.method public hidebysig specialname 
		instance void add_StateChanged (
			class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x8eac
		// Header size: 12
		// Code size: 41 (0x29)
		.maxstack 3
		.locals init (
			[0] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate,
			[1] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate,
			[2] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate Philips.PSG.Gateway.ScheduleManager::StateChanged
		IL_0006: stloc.0
		// loop start (head: IL_0007)
			IL_0007: ldloc.0
			IL_0008: stloc.1
			IL_0009: ldloc.1
			IL_000a: ldarg.1
			IL_000b: call class [mscorlib]System.Delegate [mscorlib]System.Delegate::Combine(class [mscorlib]System.Delegate, class [mscorlib]System.Delegate)
			IL_0010: castclass Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate
			IL_0015: stloc.2
			IL_0016: ldarg.0
			IL_0017: ldflda class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate Philips.PSG.Gateway.ScheduleManager::StateChanged
			IL_001c: ldloc.2
			IL_001d: ldloc.1
			IL_001e: call !!0 [mscorlib]System.Threading.Interlocked::CompareExchange<class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate>(!!0&, !!0, !!0)
			IL_0023: stloc.0
			IL_0024: ldloc.0
			IL_0025: ldloc.1
			IL_0026: bne.un.s IL_0007
		// end loop
		IL_0028: ret
	} // end of method ScheduleManager::add_StateChanged

	.method public hidebysig specialname 
		instance void remove_StateChanged (
			class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x8ee4
		// Header size: 12
		// Code size: 41 (0x29)
		.maxstack 3
		.locals init (
			[0] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate,
			[1] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate,
			[2] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate Philips.PSG.Gateway.ScheduleManager::StateChanged
		IL_0006: stloc.0
		// loop start (head: IL_0007)
			IL_0007: ldloc.0
			IL_0008: stloc.1
			IL_0009: ldloc.1
			IL_000a: ldarg.1
			IL_000b: call class [mscorlib]System.Delegate [mscorlib]System.Delegate::Remove(class [mscorlib]System.Delegate, class [mscorlib]System.Delegate)
			IL_0010: castclass Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate
			IL_0015: stloc.2
			IL_0016: ldarg.0
			IL_0017: ldflda class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate Philips.PSG.Gateway.ScheduleManager::StateChanged
			IL_001c: ldloc.2
			IL_001d: ldloc.1
			IL_001e: call !!0 [mscorlib]System.Threading.Interlocked::CompareExchange<class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate>(!!0&, !!0, !!0)
			IL_0023: stloc.0
			IL_0024: ldloc.0
			IL_0025: ldloc.1
			IL_0026: bne.un.s IL_0007
		// end loop
		IL_0028: ret
	} // end of method ScheduleManager::remove_StateChanged

	.method public hidebysig specialname 
		instance void add_CurrentEventChanged (
			class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x8f1c
		// Header size: 12
		// Code size: 41 (0x29)
		.maxstack 3
		.locals init (
			[0] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate,
			[1] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate,
			[2] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate Philips.PSG.Gateway.ScheduleManager::CurrentEventChanged
		IL_0006: stloc.0
		// loop start (head: IL_0007)
			IL_0007: ldloc.0
			IL_0008: stloc.1
			IL_0009: ldloc.1
			IL_000a: ldarg.1
			IL_000b: call class [mscorlib]System.Delegate [mscorlib]System.Delegate::Combine(class [mscorlib]System.Delegate, class [mscorlib]System.Delegate)
			IL_0010: castclass Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate
			IL_0015: stloc.2
			IL_0016: ldarg.0
			IL_0017: ldflda class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate Philips.PSG.Gateway.ScheduleManager::CurrentEventChanged
			IL_001c: ldloc.2
			IL_001d: ldloc.1
			IL_001e: call !!0 [mscorlib]System.Threading.Interlocked::CompareExchange<class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate>(!!0&, !!0, !!0)
			IL_0023: stloc.0
			IL_0024: ldloc.0
			IL_0025: ldloc.1
			IL_0026: bne.un.s IL_0007
		// end loop
		IL_0028: ret
	} // end of method ScheduleManager::add_CurrentEventChanged

	.method public hidebysig specialname 
		instance void remove_CurrentEventChanged (
			class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate 'value'
		) cil managed 
	{
		.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
			01 00 00 00
		)
		// Method begins at RVA 0x8f54
		// Header size: 12
		// Code size: 41 (0x29)
		.maxstack 3
		.locals init (
			[0] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate,
			[1] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate,
			[2] class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate Philips.PSG.Gateway.ScheduleManager::CurrentEventChanged
		IL_0006: stloc.0
		// loop start (head: IL_0007)
			IL_0007: ldloc.0
			IL_0008: stloc.1
			IL_0009: ldloc.1
			IL_000a: ldarg.1
			IL_000b: call class [mscorlib]System.Delegate [mscorlib]System.Delegate::Remove(class [mscorlib]System.Delegate, class [mscorlib]System.Delegate)
			IL_0010: castclass Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate
			IL_0015: stloc.2
			IL_0016: ldarg.0
			IL_0017: ldflda class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate Philips.PSG.Gateway.ScheduleManager::CurrentEventChanged
			IL_001c: ldloc.2
			IL_001d: ldloc.1
			IL_001e: call !!0 [mscorlib]System.Threading.Interlocked::CompareExchange<class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate>(!!0&, !!0, !!0)
			IL_0023: stloc.0
			IL_0024: ldloc.0
			IL_0025: ldloc.1
			IL_0026: bne.un.s IL_0007
		// end loop
		IL_0028: ret
	} // end of method ScheduleManager::remove_CurrentEventChanged

	.method private hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x8f8c
		// Header size: 12
		// Code size: 125 (0x7d)
		.maxstack 3

		IL_0000: ldarg.0
		IL_0001: call instance void [mscorlib]System.Object::.ctor()
		IL_0006: ldarg.0
		IL_0007: ldnull
		IL_0008: stfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate Philips.PSG.Gateway.ScheduleManager::ScheduleChanged
		IL_000d: ldarg.0
		IL_000e: ldnull
		IL_000f: stfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate Philips.PSG.Gateway.ScheduleManager::StateChanged
		IL_0014: ldarg.0
		IL_0015: ldnull
		IL_0016: stfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate Philips.PSG.Gateway.ScheduleManager::CurrentEventChanged
		IL_001b: ldarg.0
		IL_001c: newobj instance void [System.Windows.Forms]System.Windows.Forms.Timer::.ctor()
		IL_0021: stfld class [System.Windows.Forms]System.Windows.Forms.Timer Philips.PSG.Gateway.ScheduleManager::_timer
		IL_0026: ldarg.0
		IL_0027: ldfld class [System.Windows.Forms]System.Windows.Forms.Timer Philips.PSG.Gateway.ScheduleManager::_timer
		IL_002c: ldarg.0
		IL_002d: ldftn instance void Philips.PSG.Gateway.ScheduleManager::Timer_Tick(object, class [mscorlib]System.EventArgs)
		IL_0033: newobj instance void [mscorlib]System.EventHandler::.ctor(object, native int)
		IL_0038: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Timer::add_Tick(class [mscorlib]System.EventHandler)
		IL_003d: ldarg.0
		IL_003e: ldfld class [System.Windows.Forms]System.Windows.Forms.Timer Philips.PSG.Gateway.ScheduleManager::_timer
		IL_0043: ldc.i4 5000
		IL_0048: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Timer::set_Interval(int32)
		IL_004d: ldarg.0
		IL_004e: call class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.Schedule::CreateFromFile()
		IL_0053: stfld class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::_schedule
		IL_0058: ldarg.0
		IL_0059: ldnull
		IL_005a: stfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_005f: ldarg.0
		IL_0060: ldfld class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::_schedule
		IL_0065: callvirt instance int32 Philips.PSG.Gateway.Schedule::get_NumberOfEvents()
		IL_006a: ldc.i4.0
		IL_006b: ble.s IL_0075

		IL_006d: ldarg.0
		IL_006e: ldc.i4.1
		IL_006f: stfld valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Philips.PSG.Gateway.ScheduleManager::_state
		IL_0074: ret

		IL_0075: ldarg.0
		IL_0076: ldc.i4.0
		IL_0077: stfld valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Philips.PSG.Gateway.ScheduleManager::_state
		IL_007c: ret
	} // end of method ScheduleManager::.ctor

	.method public hidebysig specialname static 
		class Philips.PSG.Gateway.ScheduleManager get_Instance () cil managed 
	{
		// Method begins at RVA 0x9015
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldsfld class Philips.PSG.Gateway.ScheduleManager Philips.PSG.Gateway.ScheduleManager::_instance
		IL_0005: ret
	} // end of method ScheduleManager::get_Instance

	.method public hidebysig specialname 
		instance class Philips.PSG.Gateway.Schedule get_Schedule () cil managed 
	{
		// Method begins at RVA 0x901c
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::_schedule
		IL_0006: ret
	} // end of method ScheduleManager::get_Schedule

	.method public hidebysig specialname 
		instance void set_Schedule (
			class Philips.PSG.Gateway.Schedule 'value'
		) cil managed 
	{
		// Method begins at RVA 0x9024
		// Header size: 12
		// Code size: 85 (0x55)
		.maxstack 2

		IL_0000: ldarg.0
		IL_0001: ldc.i4.0
		IL_0002: call instance void Philips.PSG.Gateway.ScheduleManager::Pause(bool)
		IL_0007: ldarg.1
		IL_0008: brtrue.s IL_0017

		IL_000a: ldarg.0
		IL_000b: newobj instance void Philips.PSG.Gateway.Schedule::.ctor()
		IL_0010: stfld class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::_schedule
		IL_0015: br.s IL_001e

		IL_0017: ldarg.0
		IL_0018: ldarg.1
		IL_0019: stfld class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::_schedule

		IL_001e: ldarg.0
		IL_001f: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate Philips.PSG.Gateway.ScheduleManager::ScheduleChanged
		IL_0024: brfalse.s IL_0037

		IL_0026: ldarg.0
		IL_0027: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate Philips.PSG.Gateway.ScheduleManager::ScheduleChanged
		IL_002c: ldarg.0
		IL_002d: ldfld class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::_schedule
		IL_0032: callvirt instance void Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate::Invoke(class Philips.PSG.Gateway.Schedule)

		IL_0037: ldarg.0
		IL_0038: ldfld class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::_schedule
		IL_003d: callvirt instance int32 Philips.PSG.Gateway.Schedule::get_NumberOfEvents()
		IL_0042: ldc.i4.0
		IL_0043: ble.s IL_004d

		IL_0045: ldarg.0
		IL_0046: ldc.i4.1
		IL_0047: call instance void Philips.PSG.Gateway.ScheduleManager::SetState(valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState)
		IL_004c: ret

		IL_004d: ldarg.0
		IL_004e: ldc.i4.0
		IL_004f: call instance void Philips.PSG.Gateway.ScheduleManager::SetState(valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState)
		IL_0054: ret
	} // end of method ScheduleManager::set_Schedule

	.method public hidebysig specialname 
		instance valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState get_State () cil managed 
	{
		// Method begins at RVA 0x9085
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Philips.PSG.Gateway.ScheduleManager::_state
		IL_0006: ret
	} // end of method ScheduleManager::get_State

	.method public hidebysig specialname 
		instance class Philips.PSG.Gateway.ScheduleEvent get_CurrentEvent () cil managed 
	{
		// Method begins at RVA 0x908d
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_0006: ret
	} // end of method ScheduleManager::get_CurrentEvent

	.method private hidebysig 
		instance void SetState (
			valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState state
		) cil managed 
	{
		// Method begins at RVA 0x9095
		// Header size: 1
		// Code size: 42 (0x2a)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Philips.PSG.Gateway.ScheduleManager::_state
		IL_0006: ldarg.1
		IL_0007: beq.s IL_0029

		IL_0009: ldarg.0
		IL_000a: ldarg.1
		IL_000b: stfld valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Philips.PSG.Gateway.ScheduleManager::_state
		IL_0010: ldarg.0
		IL_0011: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate Philips.PSG.Gateway.ScheduleManager::StateChanged
		IL_0016: brfalse.s IL_0029

		IL_0018: ldarg.0
		IL_0019: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate Philips.PSG.Gateway.ScheduleManager::StateChanged
		IL_001e: ldarg.0
		IL_001f: ldfld valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Philips.PSG.Gateway.ScheduleManager::_state
		IL_0024: callvirt instance void Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate::Invoke(valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState)

		IL_0029: ret
	} // end of method ScheduleManager::SetState

	.method private hidebysig 
		instance void SetCurrentEvent (
			class Philips.PSG.Gateway.ScheduleEvent currentEvent
		) cil managed 
	{
		// Method begins at RVA 0x90c0
		// Header size: 12
		// Code size: 124 (0x7c)
		.maxstack 2
		.locals init (
			[0] class Philips.PSG.Gateway.ScheduleEvent
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_0006: ldarg.1
		IL_0007: beq.s IL_007b

		IL_0009: ldarg.0
		IL_000a: ldfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_000f: stloc.0
		IL_0010: ldarg.0
		IL_0011: ldarg.1
		IL_0012: stfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_0017: ldarg.0
		IL_0018: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate Philips.PSG.Gateway.ScheduleManager::CurrentEventChanged
		IL_001d: brfalse.s IL_0030

		IL_001f: ldarg.0
		IL_0020: ldfld class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate Philips.PSG.Gateway.ScheduleManager::CurrentEventChanged
		IL_0025: ldarg.0
		IL_0026: ldfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_002b: callvirt instance void Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate::Invoke(class Philips.PSG.Gateway.ScheduleEvent)

		IL_0030: ldarg.0
		IL_0031: ldfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_0036: brfalse.s IL_0047

		IL_0038: ldloc.0
		IL_0039: brtrue.s IL_0047

		IL_003b: ldarg.0
		IL_003c: ldfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_0041: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::StartPlayback()
		IL_0046: ret

		IL_0047: ldarg.0
		IL_0048: ldfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_004d: brfalse.s IL_006a

		IL_004f: ldarg.0
		IL_0050: ldfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_0055: ldloc.0
		IL_0056: beq.s IL_006a

		IL_0058: ldloc.0
		IL_0059: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::StopPlayback()
		IL_005e: ldarg.0
		IL_005f: ldfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_0064: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::StartPlayback()
		IL_0069: ret

		IL_006a: ldarg.0
		IL_006b: ldfld class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::_currentEvent
		IL_0070: brtrue.s IL_007b

		IL_0072: ldloc.0
		IL_0073: brfalse.s IL_007b

		IL_0075: ldloc.0
		IL_0076: callvirt instance void Philips.PSG.Gateway.ScheduleEvent::StopPlayback()

		IL_007b: ret
	} // end of method ScheduleManager::SetCurrentEvent

	.method public hidebysig 
		instance void ReloadSchedule () cil managed 
	{
		// Method begins at RVA 0x9148
		// Header size: 12
		// Code size: 14 (0xe)
		.maxstack 2
		.locals init (
			[0] class Philips.PSG.Gateway.Schedule
		)

		IL_0000: call class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.Schedule::CreateFromFile()
		IL_0005: stloc.0
		IL_0006: ldarg.0
		IL_0007: ldloc.0
		IL_0008: call instance void Philips.PSG.Gateway.ScheduleManager::set_Schedule(class Philips.PSG.Gateway.Schedule)
		IL_000d: ret
	} // end of method ScheduleManager::ReloadSchedule

	.method public hidebysig 
		instance bool Run () cil managed 
	{
		// Method begins at RVA 0x9164
		// Header size: 12
		// Code size: 104 (0x68)
		.maxstack 3

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Philips.PSG.Gateway.ScheduleManager::_state
		IL_0006: ldc.i4.1
		IL_0007: bne.un.s IL_0016

		IL_0009: ldarg.0
		IL_000a: ldfld class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::_schedule
		IL_000f: callvirt instance bool Philips.PSG.Gateway.Schedule::Validate()
		IL_0014: brtrue.s IL_0018

		IL_0016: ldc.i4.0
		IL_0017: ret

		IL_0018: call class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager::get_Instance()
		IL_001d: callvirt instance string Philips.PSG.Gateway.DekTecManager::get_DeviceType()
		IL_0022: call bool [mscorlib]System.String::IsNullOrEmpty(string)
		IL_0027: brfalse.s IL_004e

		IL_0029: call string Philips.PSG.Gateway.Resources::get_DLG_DekTecDeviceNotAvailable()
		IL_002e: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0033: ldstr " "
		IL_0038: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
		IL_003d: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0042: call string [mscorlib]System.String::Concat(string, string, string)
		IL_0047: call void [mscorlib]System.Console::WriteLine(string)
		IL_004c: ldc.i4.0
		IL_004d: ret

		IL_004e: ldarg.0
		IL_004f: ldc.i4.2
		IL_0050: call instance void Philips.PSG.Gateway.ScheduleManager::SetState(valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState)
		IL_0055: ldarg.0
		IL_0056: call instance void Philips.PSG.Gateway.ScheduleManager::CheckScheduledEvent()
		IL_005b: ldarg.0
		IL_005c: ldfld class [System.Windows.Forms]System.Windows.Forms.Timer Philips.PSG.Gateway.ScheduleManager::_timer
		IL_0061: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Timer::Start()
		IL_0066: ldc.i4.1
		IL_0067: ret
	} // end of method ScheduleManager::Run

	.method private hidebysig 
		instance void Pause (
			bool enableStateChange
		) cil managed 
	{
		// Method begins at RVA 0x91d8
		// Header size: 1
		// Code size: 37 (0x25)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Philips.PSG.Gateway.ScheduleManager::_state
		IL_0006: ldc.i4.2
		IL_0007: bne.un.s IL_0024

		IL_0009: ldarg.0
		IL_000a: ldfld class [System.Windows.Forms]System.Windows.Forms.Timer Philips.PSG.Gateway.ScheduleManager::_timer
		IL_000f: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Timer::Stop()
		IL_0014: ldarg.0
		IL_0015: call instance void Philips.PSG.Gateway.ScheduleManager::HaltScheduledEvent()
		IL_001a: ldarg.1
		IL_001b: brfalse.s IL_0024

		IL_001d: ldarg.0
		IL_001e: ldc.i4.1
		IL_001f: call instance void Philips.PSG.Gateway.ScheduleManager::SetState(valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState)

		IL_0024: ret
	} // end of method ScheduleManager::Pause

	.method public hidebysig 
		instance void Pause () cil managed 
	{
		// Method begins at RVA 0x91fe
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldc.i4.1
		IL_0002: call instance void Philips.PSG.Gateway.ScheduleManager::Pause(bool)
		IL_0007: ret
	} // end of method ScheduleManager::Pause

	.method private hidebysig 
		instance void CheckScheduledEvent () cil managed 
	{
		// Method begins at RVA 0x9208
		// Header size: 12
		// Code size: 33 (0x21)
		.maxstack 2
		.locals init (
			[0] class Philips.PSG.Gateway.ScheduleEvent
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::_schedule
		IL_0006: callvirt instance bool Philips.PSG.Gateway.Schedule::Validate()
		IL_000b: brfalse.s IL_0020

		IL_000d: ldarg.0
		IL_000e: ldfld class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::_schedule
		IL_0013: callvirt instance class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.Schedule::GetCurrentEvent()
		IL_0018: stloc.0
		IL_0019: ldarg.0
		IL_001a: ldloc.0
		IL_001b: call instance void Philips.PSG.Gateway.ScheduleManager::SetCurrentEvent(class Philips.PSG.Gateway.ScheduleEvent)

		IL_0020: ret
	} // end of method ScheduleManager::CheckScheduledEvent

	.method private hidebysig 
		instance void HaltScheduledEvent () cil managed 
	{
		// Method begins at RVA 0x9235
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldnull
		IL_0002: call instance void Philips.PSG.Gateway.ScheduleManager::SetCurrentEvent(class Philips.PSG.Gateway.ScheduleEvent)
		IL_0007: ret
	} // end of method ScheduleManager::HaltScheduledEvent

	.method private hidebysig 
		instance void Timer_Tick (
			object sender,
			class [mscorlib]System.EventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x923e
		// Header size: 1
		// Code size: 16 (0x10)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Philips.PSG.Gateway.ScheduleManager::_state
		IL_0006: ldc.i4.2
		IL_0007: bne.un.s IL_000f

		IL_0009: ldarg.0
		IL_000a: call instance void Philips.PSG.Gateway.ScheduleManager::CheckScheduledEvent()

		IL_000f: ret
	} // end of method ScheduleManager::Timer_Tick

	.method private hidebysig specialname rtspecialname static 
		void .cctor () cil managed 
	{
		// Method begins at RVA 0x924f
		// Header size: 1
		// Code size: 11 (0xb)
		.maxstack 8

		IL_0000: newobj instance void Philips.PSG.Gateway.ScheduleManager::.ctor()
		IL_0005: stsfld class Philips.PSG.Gateway.ScheduleManager Philips.PSG.Gateway.ScheduleManager::_instance
		IL_000a: ret
	} // end of method ScheduleManager::.cctor

	// Events
	.event Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate ScheduleChanged
	{
		.addon instance void Philips.PSG.Gateway.ScheduleManager::add_ScheduleChanged(class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate)
		.removeon instance void Philips.PSG.Gateway.ScheduleManager::remove_ScheduleChanged(class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerScheduleChangedDelegate)
	}

	.event Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate StateChanged
	{
		.addon instance void Philips.PSG.Gateway.ScheduleManager::add_StateChanged(class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate)
		.removeon instance void Philips.PSG.Gateway.ScheduleManager::remove_StateChanged(class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerStateChangedDelegate)
	}

	.event Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate CurrentEventChanged
	{
		.addon instance void Philips.PSG.Gateway.ScheduleManager::add_CurrentEventChanged(class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate)
		.removeon instance void Philips.PSG.Gateway.ScheduleManager::remove_CurrentEventChanged(class Philips.PSG.Gateway.ScheduleManager/ScheduleManagerCurrentEventChangedDelegate)
	}


	// Properties
	.property class Philips.PSG.Gateway.ScheduleManager Instance()
	{
		.get class Philips.PSG.Gateway.ScheduleManager Philips.PSG.Gateway.ScheduleManager::get_Instance()
	}
	.property instance class Philips.PSG.Gateway.Schedule Schedule()
	{
		.get instance class Philips.PSG.Gateway.Schedule Philips.PSG.Gateway.ScheduleManager::get_Schedule()
		.set instance void Philips.PSG.Gateway.ScheduleManager::set_Schedule(class Philips.PSG.Gateway.Schedule)
	}
	.property instance valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState State()
	{
		.get instance valuetype Philips.PSG.Gateway.ScheduleManager/ScheduleState Philips.PSG.Gateway.ScheduleManager::get_State()
	}
	.property instance class Philips.PSG.Gateway.ScheduleEvent CurrentEvent()
	{
		.get instance class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.ScheduleManager::get_CurrentEvent()
	}

} // end of class Philips.PSG.Gateway.ScheduleManager

.class public auto ansi serializable beforefieldinit Philips.PSG.Gateway.ScheduleEvent
	extends [mscorlib]System.Object
{
	// Fields
	.field private static valuetype [mscorlib]System.TimeSpan START_TIME_MIN
	.field private static valuetype [mscorlib]System.TimeSpan START_TIME_MAX
	.field private static valuetype [mscorlib]System.TimeSpan END_TIME_MIN
	.field private static valuetype [mscorlib]System.TimeSpan END_TIME_MAX
	.field private string _transportStreamFile
	.field private string _targetTvModel
	.field private valuetype [mscorlib]System.TimeSpan _startTime
	.field private valuetype [mscorlib]System.TimeSpan _endTime
	.field private bool _enabled

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x925b
		// Header size: 1
		// Code size: 58 (0x3a)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [mscorlib]System.Object::.ctor()
		IL_0006: ldarg.0
		IL_0007: ldsfld string [mscorlib]System.String::Empty
		IL_000c: stfld string Philips.PSG.Gateway.ScheduleEvent::_transportStreamFile
		IL_0011: ldarg.0
		IL_0012: ldsfld string [mscorlib]System.String::Empty
		IL_0017: stfld string Philips.PSG.Gateway.ScheduleEvent::_targetTvModel
		IL_001c: ldarg.0
		IL_001d: ldsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::START_TIME_MIN
		IL_0022: stfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_startTime
		IL_0027: ldarg.0
		IL_0028: ldsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::END_TIME_MIN
		IL_002d: stfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_endTime
		IL_0032: ldarg.0
		IL_0033: ldc.i4.1
		IL_0034: stfld bool Philips.PSG.Gateway.ScheduleEvent::_enabled
		IL_0039: ret
	} // end of method ScheduleEvent::.ctor

	.method public hidebysig 
		instance class Philips.PSG.Gateway.ScheduleEvent Clone () cil managed 
	{
		// Method begins at RVA 0x9298
		// Header size: 12
		// Code size: 61 (0x3d)
		.maxstack 3
		.locals init (
			[0] class Philips.PSG.Gateway.ScheduleEvent,
			[1] class [mscorlib]System.Runtime.Serialization.Formatters.Binary.BinaryFormatter,
			[2] class [mscorlib]System.IO.Stream
		)

		IL_0000: ldnull
		IL_0001: stloc.0
		IL_0002: newobj instance void [mscorlib]System.Runtime.Serialization.Formatters.Binary.BinaryFormatter::.ctor()
		IL_0007: stloc.1
		IL_0008: newobj instance void [mscorlib]System.IO.MemoryStream::.ctor()
		IL_000d: stloc.2
		.try
		{
			.try
			{
				IL_000e: ldloc.1
				IL_000f: ldloc.2
				IL_0010: ldarg.0
				IL_0011: callvirt instance void [mscorlib]System.Runtime.Serialization.Formatters.Binary.BinaryFormatter::Serialize(class [mscorlib]System.IO.Stream, object)
				IL_0016: ldloc.2
				IL_0017: ldc.i4.0
				IL_0018: conv.i8
				IL_0019: ldc.i4.0
				IL_001a: callvirt instance int64 [mscorlib]System.IO.Stream::Seek(int64, valuetype [mscorlib]System.IO.SeekOrigin)
				IL_001f: pop
				IL_0020: ldloc.1
				IL_0021: ldloc.2
				IL_0022: callvirt instance object [mscorlib]System.Runtime.Serialization.Formatters.Binary.BinaryFormatter::Deserialize(class [mscorlib]System.IO.Stream)
				IL_0027: castclass Philips.PSG.Gateway.ScheduleEvent
				IL_002c: stloc.0
				IL_002d: leave.s IL_003b
			} // end .try
			catch [mscorlib]System.Exception
			{
				IL_002f: pop
				IL_0030: ldnull
				IL_0031: stloc.0
				IL_0032: leave.s IL_003b
			} // end handler
		} // end .try
		finally
		{
			IL_0034: ldloc.2
			IL_0035: callvirt instance void [mscorlib]System.IO.Stream::Close()
			IL_003a: endfinally
		} // end handler

		IL_003b: ldloc.0
		IL_003c: ret
	} // end of method ScheduleEvent::Clone

	.method public hidebysig specialname 
		instance string get_TargetTvModel () cil managed 
	{
		// Method begins at RVA 0x9300
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.ScheduleEvent::_targetTvModel
		IL_0006: ret
	} // end of method ScheduleEvent::get_TargetTvModel

	.method public hidebysig specialname 
		instance void set_TargetTvModel (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x9308
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brtrue.s IL_000f

		IL_0003: ldarg.0
		IL_0004: ldsfld string [mscorlib]System.String::Empty
		IL_0009: stfld string Philips.PSG.Gateway.ScheduleEvent::_targetTvModel
		IL_000e: ret

		IL_000f: ldarg.0
		IL_0010: ldarg.1
		IL_0011: stfld string Philips.PSG.Gateway.ScheduleEvent::_targetTvModel
		IL_0016: ret
	} // end of method ScheduleEvent::set_TargetTvModel

	.method public hidebysig specialname 
		instance valuetype [mscorlib]System.TimeSpan get_StartTime () cil managed 
	{
		// Method begins at RVA 0x9320
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_startTime
		IL_0006: ret
	} // end of method ScheduleEvent::get_StartTime

	.method public hidebysig specialname 
		instance void set_StartTime (
			valuetype [mscorlib]System.TimeSpan 'value'
		) cil managed 
	{
		// Method begins at RVA 0x9328
		// Header size: 12
		// Code size: 66 (0x42)
		.maxstack 5

		IL_0000: ldarg.0
		IL_0001: ldarg.0
		IL_0002: ldarg.1
		IL_0003: ldsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::START_TIME_MIN
		IL_0008: ldsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::START_TIME_MAX
		IL_000d: call instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::ClipTimeSpanValue(valuetype [mscorlib]System.TimeSpan, valuetype [mscorlib]System.TimeSpan, valuetype [mscorlib]System.TimeSpan)
		IL_0012: stfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_startTime
		IL_0017: ldarg.0
		IL_0018: ldflda valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_startTime
		IL_001d: ldarg.0
		IL_001e: ldfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_endTime
		IL_0023: call instance int32 [mscorlib]System.TimeSpan::CompareTo(valuetype [mscorlib]System.TimeSpan)
		IL_0028: ldc.i4.0
		IL_0029: blt.s IL_0041

		IL_002b: ldarg.0
		IL_002c: ldarg.0
		IL_002d: ldflda valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_startTime
		IL_0032: call valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.Settings::get_ScheduleTimeGranularity()
		IL_0037: call instance valuetype [mscorlib]System.TimeSpan [mscorlib]System.TimeSpan::Add(valuetype [mscorlib]System.TimeSpan)
		IL_003c: stfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_endTime

		IL_0041: ret
	} // end of method ScheduleEvent::set_StartTime

	.method public hidebysig specialname 
		instance string get_StartTimeAsString () cil managed 
	{
		// Method begins at RVA 0x9378
		// Header size: 12
		// Code size: 21 (0x15)
		.maxstack 1
		.locals init (
			[0] valuetype [mscorlib]System.TimeSpan
		)

		IL_0000: ldarg.0
		IL_0001: call instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::get_StartTime()
		IL_0006: stloc.0
		IL_0007: ldloca.s 0
		IL_0009: constrained. [mscorlib]System.TimeSpan
		IL_000f: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0014: ret
	} // end of method ScheduleEvent::get_StartTimeAsString

	.method public hidebysig specialname 
		instance void set_StartTimeAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x939c
		// Header size: 12
		// Code size: 29 (0x1d)
		.maxstack 2

		.try
		{
			IL_0000: ldarg.0
			IL_0001: ldarg.1
			IL_0002: call valuetype [mscorlib]System.TimeSpan [mscorlib]System.TimeSpan::Parse(string)
			IL_0007: call instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTime(valuetype [mscorlib]System.TimeSpan)
			IL_000c: leave.s IL_001c
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_000e: pop
			IL_000f: ldarg.0
			IL_0010: ldsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::START_TIME_MIN
			IL_0015: call instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTime(valuetype [mscorlib]System.TimeSpan)
			IL_001a: leave.s IL_001c
		} // end handler

		IL_001c: ret
	} // end of method ScheduleEvent::set_StartTimeAsString

	.method public hidebysig specialname 
		instance valuetype [mscorlib]System.TimeSpan get_EndTime () cil managed 
	{
		// Method begins at RVA 0x93d8
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_endTime
		IL_0006: ret
	} // end of method ScheduleEvent::get_EndTime

	.method public hidebysig specialname 
		instance void set_EndTime (
			valuetype [mscorlib]System.TimeSpan 'value'
		) cil managed 
	{
		// Method begins at RVA 0x93e0
		// Header size: 12
		// Code size: 66 (0x42)
		.maxstack 5

		IL_0000: ldarg.0
		IL_0001: ldarg.0
		IL_0002: ldarg.1
		IL_0003: ldsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::END_TIME_MIN
		IL_0008: ldsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::END_TIME_MAX
		IL_000d: call instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::ClipTimeSpanValue(valuetype [mscorlib]System.TimeSpan, valuetype [mscorlib]System.TimeSpan, valuetype [mscorlib]System.TimeSpan)
		IL_0012: stfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_endTime
		IL_0017: ldarg.0
		IL_0018: ldflda valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_endTime
		IL_001d: ldarg.0
		IL_001e: ldfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_startTime
		IL_0023: call instance int32 [mscorlib]System.TimeSpan::CompareTo(valuetype [mscorlib]System.TimeSpan)
		IL_0028: ldc.i4.0
		IL_0029: bgt.s IL_0041

		IL_002b: ldarg.0
		IL_002c: ldarg.0
		IL_002d: ldflda valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_endTime
		IL_0032: call valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.Settings::get_ScheduleTimeGranularity()
		IL_0037: call instance valuetype [mscorlib]System.TimeSpan [mscorlib]System.TimeSpan::Subtract(valuetype [mscorlib]System.TimeSpan)
		IL_003c: stfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_startTime

		IL_0041: ret
	} // end of method ScheduleEvent::set_EndTime

	.method public hidebysig specialname 
		instance string get_EndTimeAsString () cil managed 
	{
		// Method begins at RVA 0x9430
		// Header size: 12
		// Code size: 21 (0x15)
		.maxstack 1
		.locals init (
			[0] valuetype [mscorlib]System.TimeSpan
		)

		IL_0000: ldarg.0
		IL_0001: call instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::get_EndTime()
		IL_0006: stloc.0
		IL_0007: ldloca.s 0
		IL_0009: constrained. [mscorlib]System.TimeSpan
		IL_000f: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0014: ret
	} // end of method ScheduleEvent::get_EndTimeAsString

	.method public hidebysig specialname 
		instance void set_EndTimeAsString (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x9454
		// Header size: 12
		// Code size: 29 (0x1d)
		.maxstack 2

		.try
		{
			IL_0000: ldarg.0
			IL_0001: ldarg.1
			IL_0002: call valuetype [mscorlib]System.TimeSpan [mscorlib]System.TimeSpan::Parse(string)
			IL_0007: call instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTime(valuetype [mscorlib]System.TimeSpan)
			IL_000c: leave.s IL_001c
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_000e: pop
			IL_000f: ldarg.0
			IL_0010: ldsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::END_TIME_MIN
			IL_0015: call instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTime(valuetype [mscorlib]System.TimeSpan)
			IL_001a: leave.s IL_001c
		} // end handler

		IL_001c: ret
	} // end of method ScheduleEvent::set_EndTimeAsString

	.method public hidebysig specialname 
		instance string get_TransportStreamFile () cil managed 
	{
		// Method begins at RVA 0x9490
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.ScheduleEvent::_transportStreamFile
		IL_0006: ret
	} // end of method ScheduleEvent::get_TransportStreamFile

	.method public hidebysig specialname 
		instance void set_TransportStreamFile (
			string 'value'
		) cil managed 
	{
		// Method begins at RVA 0x9498
		// Header size: 1
		// Code size: 23 (0x17)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brtrue.s IL_000f

		IL_0003: ldarg.0
		IL_0004: ldsfld string [mscorlib]System.String::Empty
		IL_0009: stfld string Philips.PSG.Gateway.ScheduleEvent::_transportStreamFile
		IL_000e: ret

		IL_000f: ldarg.0
		IL_0010: ldarg.1
		IL_0011: stfld string Philips.PSG.Gateway.ScheduleEvent::_transportStreamFile
		IL_0016: ret
	} // end of method ScheduleEvent::set_TransportStreamFile

	.method public hidebysig specialname 
		instance bool get_Enabled () cil managed 
	{
		// Method begins at RVA 0x94b0
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld bool Philips.PSG.Gateway.ScheduleEvent::_enabled
		IL_0006: ret
	} // end of method ScheduleEvent::get_Enabled

	.method public hidebysig specialname 
		instance void set_Enabled (
			bool 'value'
		) cil managed 
	{
		// Method begins at RVA 0x94b8
		// Header size: 1
		// Code size: 8 (0x8)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.1
		IL_0002: stfld bool Philips.PSG.Gateway.ScheduleEvent::_enabled
		IL_0007: ret
	} // end of method ScheduleEvent::set_Enabled

	.method private hidebysig 
		instance valuetype [mscorlib]System.TimeSpan ClipTimeSpanValue (
			valuetype [mscorlib]System.TimeSpan 'value',
			valuetype [mscorlib]System.TimeSpan min,
			valuetype [mscorlib]System.TimeSpan max
		) cil managed 
	{
		// Method begins at RVA 0x94c1
		// Header size: 1
		// Code size: 28 (0x1c)
		.maxstack 8

		IL_0000: ldarga.s 'value'
		IL_0002: ldarg.2
		IL_0003: call instance int32 [mscorlib]System.TimeSpan::CompareTo(valuetype [mscorlib]System.TimeSpan)
		IL_0008: ldc.i4.0
		IL_0009: bge.s IL_000d

		IL_000b: ldarg.2
		IL_000c: ret

		IL_000d: ldarga.s 'value'
		IL_000f: ldarg.3
		IL_0010: call instance int32 [mscorlib]System.TimeSpan::CompareTo(valuetype [mscorlib]System.TimeSpan)
		IL_0015: ldc.i4.0
		IL_0016: ble.s IL_001a

		IL_0018: ldarg.3
		IL_0019: ret

		IL_001a: ldarg.1
		IL_001b: ret
	} // end of method ScheduleEvent::ClipTimeSpanValue

	.method public hidebysig virtual 
		instance string ToString () cil managed 
	{
		// Method begins at RVA 0x94e0
		// Header size: 12
		// Code size: 83 (0x53)
		.maxstack 6

		IL_0000: ldtoken Philips.PSG.Gateway.ScheduleEvent
		IL_0005: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_000a: callvirt instance string [mscorlib]System.Reflection.MemberInfo::get_Name()
		IL_000f: ldstr "[TV Model={0}, Start={1}, End={2}, TS File={3}]"
		IL_0014: ldc.i4.4
		IL_0015: newarr [mscorlib]System.Object
		IL_001a: dup
		IL_001b: ldc.i4.0
		IL_001c: ldarg.0
		IL_001d: ldfld string Philips.PSG.Gateway.ScheduleEvent::_targetTvModel
		IL_0022: stelem.ref
		IL_0023: dup
		IL_0024: ldc.i4.1
		IL_0025: ldarg.0
		IL_0026: ldfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_startTime
		IL_002b: box [mscorlib]System.TimeSpan
		IL_0030: stelem.ref
		IL_0031: dup
		IL_0032: ldc.i4.2
		IL_0033: ldarg.0
		IL_0034: ldfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::_endTime
		IL_0039: box [mscorlib]System.TimeSpan
		IL_003e: stelem.ref
		IL_003f: dup
		IL_0040: ldc.i4.3
		IL_0041: ldarg.0
		IL_0042: ldfld string Philips.PSG.Gateway.ScheduleEvent::_transportStreamFile
		IL_0047: stelem.ref
		IL_0048: call string [mscorlib]System.String::Format(string, object[])
		IL_004d: call string [mscorlib]System.String::Concat(string, string)
		IL_0052: ret
	} // end of method ScheduleEvent::ToString

	.method public hidebysig 
		instance void StartPlayback () cil managed 
	{
		// Method begins at RVA 0x953f
		// Header size: 1
		// Code size: 13 (0xd)
		.maxstack 8

		IL_0000: call class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager::get_Instance()
		IL_0005: ldarg.0
		IL_0006: callvirt instance bool Philips.PSG.Gateway.DekTecManager::StartPlayback(class Philips.PSG.Gateway.ScheduleEvent)
		IL_000b: pop
		IL_000c: ret
	} // end of method ScheduleEvent::StartPlayback

	.method public hidebysig 
		instance void StopPlayback () cil managed 
	{
		// Method begins at RVA 0x954d
		// Header size: 1
		// Code size: 11 (0xb)
		.maxstack 8

		IL_0000: call class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager::get_Instance()
		IL_0005: callvirt instance void Philips.PSG.Gateway.DekTecManager::StopPlayback()
		IL_000a: ret
	} // end of method ScheduleEvent::StopPlayback

	.method private hidebysig specialname rtspecialname static 
		void .cctor () cil managed 
	{
		// Method begins at RVA 0x955c
		// Header size: 12
		// Code size: 62 (0x3e)
		.maxstack 3
		.locals init (
			[0] valuetype [mscorlib]System.TimeSpan
		)

		IL_0000: ldsfld valuetype [mscorlib]System.TimeSpan [mscorlib]System.TimeSpan::Zero
		IL_0005: stsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::START_TIME_MIN
		IL_000a: ldc.i4.s 24
		IL_000c: ldc.i4.0
		IL_000d: ldc.i4.0
		IL_000e: newobj instance void [mscorlib]System.TimeSpan::.ctor(int32, int32, int32)
		IL_0013: stloc.0
		IL_0014: ldloca.s 0
		IL_0016: call valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.Settings::get_ScheduleTimeGranularity()
		IL_001b: call instance valuetype [mscorlib]System.TimeSpan [mscorlib]System.TimeSpan::Subtract(valuetype [mscorlib]System.TimeSpan)
		IL_0020: stsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::START_TIME_MAX
		IL_0025: call valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.Settings::get_ScheduleTimeGranularity()
		IL_002a: stsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::END_TIME_MIN
		IL_002f: ldc.i4.s 24
		IL_0031: ldc.i4.0
		IL_0032: ldc.i4.0
		IL_0033: newobj instance void [mscorlib]System.TimeSpan::.ctor(int32, int32, int32)
		IL_0038: stsfld valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::END_TIME_MAX
		IL_003d: ret
	} // end of method ScheduleEvent::.cctor

	// Properties
	.property instance string TargetTvModel()
	{
		.get instance string Philips.PSG.Gateway.ScheduleEvent::get_TargetTvModel()
		.set instance void Philips.PSG.Gateway.ScheduleEvent::set_TargetTvModel(string)
	}
	.property instance valuetype [mscorlib]System.TimeSpan StartTime()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::get_StartTime()
		.set instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTime(valuetype [mscorlib]System.TimeSpan)
	}
	.property instance string StartTimeAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 09 53 74 61 72 74 54 69 6d 65 00 00
		)
		.get instance string Philips.PSG.Gateway.ScheduleEvent::get_StartTimeAsString()
		.set instance void Philips.PSG.Gateway.ScheduleEvent::set_StartTimeAsString(string)
	}
	.property instance valuetype [mscorlib]System.TimeSpan EndTime()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::get_EndTime()
		.set instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTime(valuetype [mscorlib]System.TimeSpan)
	}
	.property instance string EndTimeAsString()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlElementAttribute::.ctor(string) = (
			01 00 07 45 6e 64 54 69 6d 65 00 00
		)
		.get instance string Philips.PSG.Gateway.ScheduleEvent::get_EndTimeAsString()
		.set instance void Philips.PSG.Gateway.ScheduleEvent::set_EndTimeAsString(string)
	}
	.property instance string TransportStreamFile()
	{
		.get instance string Philips.PSG.Gateway.ScheduleEvent::get_TransportStreamFile()
		.set instance void Philips.PSG.Gateway.ScheduleEvent::set_TransportStreamFile(string)
	}
	.property instance bool Enabled()
	{
		.get instance bool Philips.PSG.Gateway.ScheduleEvent::get_Enabled()
		.set instance void Philips.PSG.Gateway.ScheduleEvent::set_Enabled(bool)
	}

} // end of class Philips.PSG.Gateway.ScheduleEvent

.class public auto ansi serializable beforefieldinit Philips.PSG.Gateway.Schedule
	extends [mscorlib]System.Object
{
	// Fields
	.field private class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> _events

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x95a6
		// Header size: 1
		// Code size: 18 (0x12)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [mscorlib]System.Object::.ctor()
		IL_0006: ldarg.0
		IL_0007: newobj instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::.ctor()
		IL_000c: stfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.Schedule::_events
		IL_0011: ret
	} // end of method Schedule::.ctor

	.method public hidebysig specialname 
		instance class Philips.PSG.Gateway.ScheduleEvent[] get_Events () cil managed 
	{
		// Method begins at RVA 0x95b9
		// Header size: 1
		// Code size: 12 (0xc)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.Schedule::_events
		IL_0006: callvirt instance !0[] class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::ToArray()
		IL_000b: ret
	} // end of method Schedule::get_Events

	.method public hidebysig specialname 
		instance void set_Events (
			class Philips.PSG.Gateway.ScheduleEvent[] 'value'
		) cil managed 
	{
		// Method begins at RVA 0x95c6
		// Header size: 1
		// Code size: 27 (0x1b)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.Schedule::_events
		IL_0006: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::Clear()
		IL_000b: ldarg.1
		IL_000c: brfalse.s IL_001a

		IL_000e: ldarg.0
		IL_000f: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.Schedule::_events
		IL_0014: ldarg.1
		IL_0015: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::AddRange(class [mscorlib]System.Collections.Generic.IEnumerable`1<!0>)

		IL_001a: ret
	} // end of method Schedule::set_Events

	.method public hidebysig specialname 
		instance int32 get_NumberOfEvents () cil managed 
	{
		// Method begins at RVA 0x95e2
		// Header size: 1
		// Code size: 12 (0xc)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.Schedule::_events
		IL_0006: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::get_Count()
		IL_000b: ret
	} // end of method Schedule::get_NumberOfEvents

	.method public hidebysig 
		instance void SaveToFile () cil managed 
	{
		// Method begins at RVA 0x95f0
		// Header size: 12
		// Code size: 89 (0x59)
		.maxstack 3
		.locals init (
			[0] class [System.Xml]System.Xml.Serialization.XmlSerializer,
			[1] string,
			[2] class [mscorlib]System.IO.StreamWriter,
			[3] class [mscorlib]System.Exception
		)

		IL_0000: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0005: callvirt instance string Philips.PSG.Gateway.Config::get_ScheduleDataFile()
		IL_000a: stloc.1
		IL_000b: ldnull
		IL_000c: stloc.2
		IL_000d: ldtoken Philips.PSG.Gateway.Schedule
		IL_0012: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_0017: newobj instance void [System.Xml]System.Xml.Serialization.XmlSerializer::.ctor(class [mscorlib]System.Type)
		IL_001c: stloc.0
		.try
		{
			.try
			{
				IL_001d: ldloc.1
				IL_001e: call string [mscorlib]System.IO.Path::GetDirectoryName(string)
				IL_0023: call class [mscorlib]System.IO.DirectoryInfo [mscorlib]System.IO.Directory::CreateDirectory(string)
				IL_0028: pop
				IL_0029: ldloc.1
				IL_002a: call class [mscorlib]System.IO.StreamWriter [mscorlib]System.IO.File::CreateText(string)
				IL_002f: stloc.2
				IL_0030: ldloc.0
				IL_0031: ldloc.2
				IL_0032: ldarg.0
				IL_0033: callvirt instance void [System.Xml]System.Xml.Serialization.XmlSerializer::Serialize(class [mscorlib]System.IO.TextWriter, object)
				IL_0038: ldloc.2
				IL_0039: callvirt instance void [mscorlib]System.IO.TextWriter::Flush()
				IL_003e: leave.s IL_0058
			} // end .try
			catch [mscorlib]System.Exception
			{
				IL_0040: stloc.3
				IL_0041: ldstr "Schedule: Error - failed to save schedule to file: {0}"
				IL_0046: ldloc.3
				IL_0047: call void [mscorlib]System.Console::WriteLine(string, object)
				IL_004c: leave.s IL_0058
			} // end handler
		} // end .try
		finally
		{
			IL_004e: ldloc.2
			IL_004f: brfalse.s IL_0057

			IL_0051: ldloc.2
			IL_0052: callvirt instance void [mscorlib]System.IO.TextWriter::Close()

			IL_0057: endfinally
		} // end handler

		IL_0058: ret
	} // end of method Schedule::SaveToFile

	.method public hidebysig static 
		class Philips.PSG.Gateway.Schedule CreateFromFile () cil managed 
	{
		// Method begins at RVA 0x9674
		// Header size: 12
		// Code size: 103 (0x67)
		.maxstack 2
		.locals init (
			[0] class [System.Xml]System.Xml.Serialization.XmlSerializer,
			[1] class [mscorlib]System.IO.StreamReader,
			[2] class Philips.PSG.Gateway.Schedule,
			[3] string,
			[4] class [mscorlib]System.Exception
		)

		IL_0000: ldnull
		IL_0001: stloc.1
		IL_0002: ldnull
		IL_0003: stloc.2
		IL_0004: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0009: callvirt instance string Philips.PSG.Gateway.Config::get_ScheduleDataFile()
		IL_000e: stloc.3
		IL_000f: ldtoken Philips.PSG.Gateway.Schedule
		IL_0014: call class [mscorlib]System.Type [mscorlib]System.Type::GetTypeFromHandle(valuetype [mscorlib]System.RuntimeTypeHandle)
		IL_0019: newobj instance void [System.Xml]System.Xml.Serialization.XmlSerializer::.ctor(class [mscorlib]System.Type)
		IL_001e: stloc.0
		.try
		{
			.try
			{
				IL_001f: ldloc.3
				IL_0020: call bool [mscorlib]System.IO.File::Exists(string)
				IL_0025: brfalse.s IL_003d

				IL_0027: ldloc.3
				IL_0028: call class [mscorlib]System.IO.StreamReader [mscorlib]System.IO.File::OpenText(string)
				IL_002d: stloc.1
				IL_002e: ldloc.0
				IL_002f: ldloc.1
				IL_0030: callvirt instance object [System.Xml]System.Xml.Serialization.XmlSerializer::Deserialize(class [mscorlib]System.IO.TextReader)
				IL_0035: castclass Philips.PSG.Gateway.Schedule
				IL_003a: stloc.2
				IL_003b: br.s IL_0043

				IL_003d: newobj instance void Philips.PSG.Gateway.Schedule::.ctor()
				IL_0042: stloc.2

				IL_0043: leave.s IL_0065
			} // end .try
			catch [mscorlib]System.Exception
			{
				IL_0045: stloc.s 4
				IL_0047: ldstr "Schedule: Error - failed to create schedule from file: {0}"
				IL_004c: ldloc.s 4
				IL_004e: call void [mscorlib]System.Console::WriteLine(string, object)
				IL_0053: newobj instance void Philips.PSG.Gateway.Schedule::.ctor()
				IL_0058: stloc.2
				IL_0059: leave.s IL_0065
			} // end handler
		} // end .try
		finally
		{
			IL_005b: ldloc.1
			IL_005c: brfalse.s IL_0064

			IL_005e: ldloc.1
			IL_005f: callvirt instance void [mscorlib]System.IO.TextReader::Close()

			IL_0064: endfinally
		} // end handler

		IL_0065: ldloc.2
		IL_0066: ret
	} // end of method Schedule::CreateFromFile

	.method public hidebysig 
		instance class Philips.PSG.Gateway.ScheduleEvent GetEventAtTime (
			valuetype [mscorlib]System.TimeSpan time
		) cil managed 
	{
		// Method begins at RVA 0x9704
		// Header size: 12
		// Code size: 95 (0x5f)
		.maxstack 2
		.locals init (
			[0] valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>,
			[1] class Philips.PSG.Gateway.ScheduleEvent,
			[2] class Philips.PSG.Gateway.ScheduleEvent
		)

		IL_0000: ldarg.0
		IL_0001: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.Schedule::_events
		IL_0006: callvirt instance valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<!0> class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::GetEnumerator()
		IL_000b: stloc.0
		.try
		{
			IL_000c: br.s IL_0042
			// loop start (head: IL_0042)
				IL_000e: ldloca.s 0
				IL_0010: call instance !0 valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>::get_Current()
				IL_0015: stloc.1
				IL_0016: ldloc.1
				IL_0017: callvirt instance bool Philips.PSG.Gateway.ScheduleEvent::get_Enabled()
				IL_001c: brfalse.s IL_0042

				IL_001e: ldarga.s time
				IL_0020: ldloc.1
				IL_0021: callvirt instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::get_StartTime()
				IL_0026: call instance int32 [mscorlib]System.TimeSpan::CompareTo(valuetype [mscorlib]System.TimeSpan)
				IL_002b: ldc.i4.0
				IL_002c: blt.s IL_0042

				IL_002e: ldarga.s time
				IL_0030: ldloc.1
				IL_0031: callvirt instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.ScheduleEvent::get_EndTime()
				IL_0036: call instance int32 [mscorlib]System.TimeSpan::CompareTo(valuetype [mscorlib]System.TimeSpan)
				IL_003b: ldc.i4.0
				IL_003c: bge.s IL_0042

				IL_003e: ldloc.1
				IL_003f: stloc.2
				IL_0040: leave.s IL_005d

				IL_0042: ldloca.s 0
				IL_0044: call instance bool valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>::MoveNext()
				IL_0049: brtrue.s IL_000e
			// end loop

			IL_004b: leave.s IL_005b
		} // end .try
		finally
		{
			IL_004d: ldloca.s 0
			IL_004f: constrained. valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>
			IL_0055: callvirt instance void [mscorlib]System.IDisposable::Dispose()
			IL_005a: endfinally
		} // end handler

		IL_005b: ldnull
		IL_005c: ret

		IL_005d: ldloc.2
		IL_005e: ret
	} // end of method Schedule::GetEventAtTime

	.method public hidebysig 
		instance class Philips.PSG.Gateway.ScheduleEvent GetCurrentEvent () cil managed 
	{
		// Method begins at RVA 0x9780
		// Header size: 12
		// Code size: 22 (0x16)
		.maxstack 2
		.locals init (
			[0] valuetype [mscorlib]System.TimeSpan,
			[1] valuetype [mscorlib]System.DateTime
		)

		IL_0000: call valuetype [mscorlib]System.DateTime [mscorlib]System.DateTime::get_Now()
		IL_0005: stloc.1
		IL_0006: ldloca.s 1
		IL_0008: call instance valuetype [mscorlib]System.TimeSpan [mscorlib]System.DateTime::get_TimeOfDay()
		IL_000d: stloc.0
		IL_000e: ldarg.0
		IL_000f: ldloc.0
		IL_0010: call instance class Philips.PSG.Gateway.ScheduleEvent Philips.PSG.Gateway.Schedule::GetEventAtTime(valuetype [mscorlib]System.TimeSpan)
		IL_0015: ret
	} // end of method Schedule::GetCurrentEvent

	.method public hidebysig 
		instance bool Validate () cil managed 
	{
		// Method begins at RVA 0x97a4
		// Header size: 12
		// Code size: 217 (0xd9)
		.maxstack 3
		.locals init (
			[0] class [mscorlib]System.Collections.Generic.List`1<string>,
			[1] valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>,
			[2] string,
			[3] class [mscorlib]System.Text.StringBuilder,
			[4] valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>,
			[5] string
		)

		IL_0000: newobj instance void class [mscorlib]System.Collections.Generic.List`1<string>::.ctor()
		IL_0005: stloc.0
		IL_0006: ldarg.0
		IL_0007: ldfld class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent> Philips.PSG.Gateway.Schedule::_events
		IL_000c: callvirt instance valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<!0> class [mscorlib]System.Collections.Generic.List`1<class Philips.PSG.Gateway.ScheduleEvent>::GetEnumerator()
		IL_0011: stloc.1
		.try
		{
			IL_0012: br.s IL_0039
			// loop start (head: IL_0039)
				IL_0014: ldloca.s 1
				IL_0016: call instance !0 valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>::get_Current()
				IL_001b: callvirt instance string Philips.PSG.Gateway.ScheduleEvent::get_TransportStreamFile()
				IL_0020: stloc.2
				IL_0021: ldloc.2
				IL_0022: call bool [mscorlib]System.IO.File::Exists(string)
				IL_0027: brtrue.s IL_0039

				IL_0029: ldloc.0
				IL_002a: ldloc.2
				IL_002b: callvirt instance bool class [mscorlib]System.Collections.Generic.List`1<string>::Contains(!0)
				IL_0030: brtrue.s IL_0039

				IL_0032: ldloc.0
				IL_0033: ldloc.2
				IL_0034: callvirt instance void class [mscorlib]System.Collections.Generic.List`1<string>::Add(!0)

				IL_0039: ldloca.s 1
				IL_003b: call instance bool valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>::MoveNext()
				IL_0040: brtrue.s IL_0014
			// end loop

			IL_0042: leave.s IL_0052
		} // end .try
		finally
		{
			IL_0044: ldloca.s 1
			IL_0046: constrained. valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<class Philips.PSG.Gateway.ScheduleEvent>
			IL_004c: callvirt instance void [mscorlib]System.IDisposable::Dispose()
			IL_0051: endfinally
		} // end handler

		IL_0052: ldloc.0
		IL_0053: callvirt instance int32 class [mscorlib]System.Collections.Generic.List`1<string>::get_Count()
		IL_0058: ldc.i4.0
		IL_0059: ble.s IL_00d7

		IL_005b: call class Philips.PSG.Gateway.ScheduleManager Philips.PSG.Gateway.ScheduleManager::get_Instance()
		IL_0060: callvirt instance void Philips.PSG.Gateway.ScheduleManager::Pause()
		IL_0065: call string Philips.PSG.Gateway.Resources::get_DLG_InvalidScheduleHeaderMessage()
		IL_006a: newobj instance void [mscorlib]System.Text.StringBuilder::.ctor(string)
		IL_006f: stloc.3
		IL_0070: ldloc.0
		IL_0071: callvirt instance valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<!0> class [mscorlib]System.Collections.Generic.List`1<string>::GetEnumerator()
		IL_0076: stloc.s 4
		.try
		{
			IL_0078: br.s IL_0091
			// loop start (head: IL_0091)
				IL_007a: ldloca.s 4
				IL_007c: call instance !0 valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>::get_Current()
				IL_0081: stloc.s 5
				IL_0083: ldloc.3
				IL_0084: ldstr "{0}\n"
				IL_0089: ldloc.s 5
				IL_008b: callvirt instance class [mscorlib]System.Text.StringBuilder [mscorlib]System.Text.StringBuilder::AppendFormat(string, object)
				IL_0090: pop

				IL_0091: ldloca.s 4
				IL_0093: call instance bool valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>::MoveNext()
				IL_0098: brtrue.s IL_007a
			// end loop

			IL_009a: leave.s IL_00aa
		} // end .try
		finally
		{
			IL_009c: ldloca.s 4
			IL_009e: constrained. valuetype [mscorlib]System.Collections.Generic.List`1/Enumerator<string>
			IL_00a4: callvirt instance void [mscorlib]System.IDisposable::Dispose()
			IL_00a9: endfinally
		} // end handler

		IL_00aa: ldloc.3
		IL_00ab: call string Philips.PSG.Gateway.Resources::get_DLG_InvalidScheduleFooterMessage()
		IL_00b0: callvirt instance class [mscorlib]System.Text.StringBuilder [mscorlib]System.Text.StringBuilder::Append(string)
		IL_00b5: pop
		IL_00b6: ldloc.3
		IL_00b7: callvirt instance string [mscorlib]System.Object::ToString()
		IL_00bc: ldstr " "
		IL_00c1: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
		IL_00c6: callvirt instance string [mscorlib]System.Object::ToString()
		IL_00cb: call string [mscorlib]System.String::Concat(string, string, string)
		IL_00d0: call void [mscorlib]System.Console::WriteLine(string)
		IL_00d5: ldc.i4.0
		IL_00d6: ret

		IL_00d7: ldc.i4.1
		IL_00d8: ret
	} // end of method Schedule::Validate

	// Properties
	.property instance class Philips.PSG.Gateway.ScheduleEvent[] Events()
	{
		.get instance class Philips.PSG.Gateway.ScheduleEvent[] Philips.PSG.Gateway.Schedule::get_Events()
		.set instance void Philips.PSG.Gateway.Schedule::set_Events(class Philips.PSG.Gateway.ScheduleEvent[])
	}
	.property instance int32 NumberOfEvents()
	{
		.custom instance void [System.Xml]System.Xml.Serialization.XmlIgnoreAttribute::.ctor() = (
			01 00 00 00
		)
		.get instance int32 Philips.PSG.Gateway.Schedule::get_NumberOfEvents()
	}

} // end of class Philips.PSG.Gateway.Schedule

.class public auto ansi abstract sealed beforefieldinit Philips.PSG.Gateway.TSGen
	extends [mscorlib]System.Object
{
	// Nested Types
	.class nested public auto ansi sealed TS_Result
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_Result TS_RESULT_OK = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_Result TS_RESULT_ERROR_NO_FILES = int32(1)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_Result TS_RESULT_ZERO_LENGTH_FILES = int32(2)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_Result TS_RESULT_FAILED = int32(3)

	} // end of class TS_Result

	.class nested public auto ansi sealed TS_DemodType
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_DemodType TS_DEMOD_TYPE_VSB = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_DemodType TS_DEMOD_TYPE_QAM64 = int32(1)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_DemodType TS_DEMOD_TYPE_QAM256 = int32(2)

	} // end of class TS_DemodType

	.class nested public auto ansi sealed TS_ProgressMessage
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage TS_PROGRESS_MESSAGE_PREPARING_CHANNEL_TABLE = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage TS_PROGRESS_MESSAGE_PREPARING_NVM = int32(1)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage TS_PROGRESS_MESSAGE_PREPARING_THEME = int32(2)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage TS_PROGRESS_MESSAGE_VALIDATING_INPUT = int32(3)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage TS_PROGRESS_MESSAGE_GENERATING_TRANSPORT_STREAM = int32(4)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage TS_PROGRESS_MESSAGE_MERGING_TRANSPORT_STREAM = int32(5)

	} // end of class TS_ProgressMessage

	.class nested public auto ansi sealed TS_ProgressCallback
		extends [mscorlib]System.MulticastDelegate
	{
		// Methods
		.method public hidebysig specialname rtspecialname 
			instance void .ctor (
				object 'object',
				native int 'method'
			) runtime managed 
		{
		} // end of method TS_ProgressCallback::.ctor

		.method public hidebysig newslot virtual 
			instance void Invoke (
				valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage marshal(int32) message
			) runtime managed 
		{
		} // end of method TS_ProgressCallback::Invoke

		.method public hidebysig newslot virtual 
			instance class [mscorlib]System.IAsyncResult BeginInvoke (
				valuetype Philips.PSG.Gateway.TSGen/TS_ProgressMessage marshal(int32) message,
				class [mscorlib]System.AsyncCallback callback,
				object 'object'
			) runtime managed 
		{
		} // end of method TS_ProgressCallback::BeginInvoke

		.method public hidebysig newslot virtual 
			instance void EndInvoke (
				class [mscorlib]System.IAsyncResult result
			) runtime managed 
		{
		} // end of method TS_ProgressCallback::EndInvoke

	} // end of class TS_ProgressCallback

	.class nested public sequential ansi sealed beforefieldinit TS_FileVersionDetails
		extends [mscorlib]System.ValueType
	{
		// Fields
		.field public marshal(fixed sysstring[10]) string hardwareModel
		.field public marshal(fixed sysstring[10]) string hardwareVersion
		.field public marshal(fixed sysstring[10]) string softwareModel
		.field public marshal(fixed sysstring[10]) string softwareVersion

	} // end of class TS_FileVersionDetails

	.class nested public sequential ansi sealed beforefieldinit TS_FileDetails
		extends [mscorlib]System.ValueType
	{
		// Fields
		.field public marshal(fixed sysstring[256]) string filePath
		.field public marshal(int32) valuetype Philips.PSG.Gateway.TSGen/TS_FileType fileType
		.field public marshal(unsigned int32) uint32 room_number
		.field public marshal(fixed sysstring[256]) string room_number_string
		.field public marshal(fixed sysstring[17]) string ssb_identifier
		.field public marshal(fixed sysstring[85]) string channel_table_identifier
		.field public marshal(struct) valuetype Philips.PSG.Gateway.TSGen/TS_FileVersionDetails fileVersionDetails

	} // end of class TS_FileDetails

	.class nested public sequential ansi sealed beforefieldinit TS_NetworkDetails
		extends [mscorlib]System.ValueType
	{
		// Fields
		.field public marshal(unsigned int16) uint16 original_network_id
		.field public marshal(unsigned int32) uint32 centre_frequency
		.field public marshal(unsigned int16) uint16 bandwidth
		.field public marshal(unsigned int16) uint16 constellation
		.field public marshal(unsigned int16) uint16 guard_interval
		.field public marshal(unsigned int16) uint16 transmission_mode
		.field public marshal(unsigned int16) uint16 code_rate
		.field public marshal(unsigned int16) uint16 prognum
		.field public marshal(unsigned int16) uint16 pid
		.field public marshal(unsigned int32) uint32 oui
		.field public marshal(unsigned int32) uint32 txnid
		.field public marshal(unsigned int32) uint32 dwnldid
		.field public marshal(unsigned int16) uint16 network_id
		.field public marshal(unsigned int16) uint16 transport_stream_id
		.field public marshal(unsigned int16) uint16 pcrpid
		.field public marshal(unsigned int16) uint16 hwmodel
		.field public marshal(unsigned int16) uint16 hwver
		.field public marshal(unsigned int16) uint16 swmodel
		.field public marshal(unsigned int16) uint16 swver

	} // end of class TS_NetworkDetails

	.class nested public auto ansi sealed TS_FileType
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_FileType Clone = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_FileType Upgrade = int32(1)
		.field public static literal valuetype Philips.PSG.Gateway.TSGen/TS_FileType TransportStream = int32(2)

	} // end of class TS_FileType


	// Methods
	.method public hidebysig static pinvokeimpl("TSGen" winapi) 
		valuetype Philips.PSG.Gateway.TSGen/TS_Result marshal(int32) TS_Generate (
			class Philips.PSG.Gateway.TSGen/TS_ProgressCallback marshal(Func) progressCB,
			string marshal(lpstr) outputFile,
			valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails[] marshal([]) fileDetails,
			int32 marshal(int32) fileCount,
			valuetype Philips.PSG.Gateway.TSGen/TS_NetworkDetails marshal(struct) networkDetails
		) cil managed preservesig 
	{
	} // end of method TSGen::TS_Generate

	.method public hidebysig static pinvokeimpl("TSGen" winapi) 
		valuetype Philips.PSG.Gateway.TSGen/TS_Result marshal(int32) TS_Merge (
			class Philips.PSG.Gateway.TSGen/TS_ProgressCallback marshal(Func) progressCB,
			string marshal(lpstr) outputFile,
			valuetype Philips.PSG.Gateway.TSGen/TS_FileDetails[] marshal([]) fileDetails,
			int32 marshal(int32) fileCount,
			valuetype Philips.PSG.Gateway.TSGen/TS_NetworkDetails marshal(struct) networkDetails
		) cil managed preservesig 
	{
	} // end of method TSGen::TS_Merge

} // end of class Philips.PSG.Gateway.TSGen

.class public auto ansi abstract sealed beforefieldinit Philips.PSG.Gateway.Settings
	extends [mscorlib]System.Object
{
	// Fields
	.field private static literal string PSG_APP_DATA_FOLDER = "\\Philips\\PSG"
	.field private static literal string CONFIG_FILE_NAME = "Configuration.xml"
	.field private static literal string CATALOG_FILE_NAME = "Catalog.xml"
	.field private static literal string DEFAULT_BUILD_ROOT_FOLDER = "%SystemDrive%\\Philips\\HotelTV"
	.field private static literal string DEFAULT_WORKING_ROOT_FOLDER = "%SystemDrive%\\Philips\\HotelTV\\PSG"
	.field private static literal string SCHEDULE_DATA_FOLDER_NAME = "OutputFiles"
	.field private static literal string OUTPUT_DATA_FOLDER_NAME = "OutputFiles"
	.field private static literal string MULTIPLEXED_TS_DATA_FILE_NAME = "MultiplexedTS.ts"
	.field private static literal string PREVIOUS_SCHEDULE_DATA_FOLDER_NAME = "PreviousFiles"
	.field private static literal string PREVIOUS_OUTPUT_DATA_FOLDER_NAME = "PreviousFiles"
	.field private static literal string SCHEDULE_DATA_FILE_NAME = "Schedule.xml"
	.field private static literal string SCHEDULE_RUN_FILE_NAME = "Run"
	.field private static literal string TS_PLAY_FILE_NAME = "TsPlay"
	.field private static literal string BUILD_LOG_FILE_NAME = "BuildLog.txt"
	.field private static literal int32 SCHEDULE_TIME_GRANULARITY_MINS = int32(5)

	// Methods
	.method public hidebysig specialname static 
		string get_ConfigFile () cil managed 
	{
		// Method begins at RVA 0x98a8
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "C:\\\\Philips\\\\SIServer\\\\PSG\\\\Configuration.xml"
		IL_0005: ret
	} // end of method Settings::get_ConfigFile

	.method public hidebysig specialname static 
		string get_CatalogFile () cil managed 
	{
		// Method begins at RVA 0x98af
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "C:\\\\Philips\\\\SIServer\\\\PSG\\\\Catalog.xml"
		IL_0005: ret
	} // end of method Settings::get_CatalogFile

	.method public hidebysig specialname static 
		string get_DefaultBuildRootFolder () cil managed 
	{
		// Method begins at RVA 0x98b6
		// Header size: 1
		// Code size: 11 (0xb)
		.maxstack 8

		IL_0000: ldstr "%SystemDrive%\\Philips\\HotelTV"
		IL_0005: call string [mscorlib]System.Environment::ExpandEnvironmentVariables(string)
		IL_000a: ret
	} // end of method Settings::get_DefaultBuildRootFolder

	.method public hidebysig specialname static 
		string get_DefaultWorkingRootFolder () cil managed 
	{
		// Method begins at RVA 0x98c2
		// Header size: 1
		// Code size: 11 (0xb)
		.maxstack 8

		IL_0000: ldstr "%SystemDrive%\\Philips\\HotelTV\\PSG"
		IL_0005: call string [mscorlib]System.Environment::ExpandEnvironmentVariables(string)
		IL_000a: ret
	} // end of method Settings::get_DefaultWorkingRootFolder

	.method public hidebysig specialname static 
		string get_ScheduleDataFolderName () cil managed 
	{
		// Method begins at RVA 0x98ce
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "OutputFiles"
		IL_0005: ret
	} // end of method Settings::get_ScheduleDataFolderName

	.method public hidebysig specialname static 
		string get_OutputDataFolderName () cil managed 
	{
		// Method begins at RVA 0x98ce
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "OutputFiles"
		IL_0005: ret
	} // end of method Settings::get_OutputDataFolderName

	.method public hidebysig specialname static 
		string get_MultiplexedTsFileName () cil managed 
	{
		// Method begins at RVA 0x98d5
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "MultiplexedTS.ts"
		IL_0005: ret
	} // end of method Settings::get_MultiplexedTsFileName

	.method public hidebysig specialname static 
		string get_PreviousScheduleDataFolderName () cil managed 
	{
		// Method begins at RVA 0x98dc
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "PreviousFiles"
		IL_0005: ret
	} // end of method Settings::get_PreviousScheduleDataFolderName

	.method public hidebysig specialname static 
		string get_PreviousOutputDataFolderName () cil managed 
	{
		// Method begins at RVA 0x98dc
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "PreviousFiles"
		IL_0005: ret
	} // end of method Settings::get_PreviousOutputDataFolderName

	.method public hidebysig specialname static 
		string get_ScheduleDataFileName () cil managed 
	{
		// Method begins at RVA 0x98e3
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "Schedule.xml"
		IL_0005: ret
	} // end of method Settings::get_ScheduleDataFileName

	.method public hidebysig specialname static 
		string get_ScheduleRunFileName () cil managed 
	{
		// Method begins at RVA 0x98ea
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "Run"
		IL_0005: ret
	} // end of method Settings::get_ScheduleRunFileName

	.method public hidebysig specialname static 
		string get_TsPlayFileName () cil managed 
	{
		// Method begins at RVA 0x98f1
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "TsPlay"
		IL_0005: ret
	} // end of method Settings::get_TsPlayFileName

	.method public hidebysig specialname static 
		string get_BuildLogFileName () cil managed 
	{
		// Method begins at RVA 0x98f8
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldstr "BuildLog.txt"
		IL_0005: ret
	} // end of method Settings::get_BuildLogFileName

	.method public hidebysig specialname static 
		valuetype [mscorlib]System.TimeSpan get_ScheduleTimeGranularity () cil managed 
	{
		// Method begins at RVA 0x98ff
		// Header size: 1
		// Code size: 9 (0x9)
		.maxstack 8

		IL_0000: ldc.i4.0
		IL_0001: ldc.i4.5
		IL_0002: ldc.i4.0
		IL_0003: newobj instance void [mscorlib]System.TimeSpan::.ctor(int32, int32, int32)
		IL_0008: ret
	} // end of method Settings::get_ScheduleTimeGranularity

	// Properties
	.property string ConfigFile()
	{
		.get string Philips.PSG.Gateway.Settings::get_ConfigFile()
	}
	.property string CatalogFile()
	{
		.get string Philips.PSG.Gateway.Settings::get_CatalogFile()
	}
	.property string DefaultBuildRootFolder()
	{
		.get string Philips.PSG.Gateway.Settings::get_DefaultBuildRootFolder()
	}
	.property string DefaultWorkingRootFolder()
	{
		.get string Philips.PSG.Gateway.Settings::get_DefaultWorkingRootFolder()
	}
	.property string ScheduleDataFolderName()
	{
		.get string Philips.PSG.Gateway.Settings::get_ScheduleDataFolderName()
	}
	.property string OutputDataFolderName()
	{
		.get string Philips.PSG.Gateway.Settings::get_OutputDataFolderName()
	}
	.property string MultiplexedTsFileName()
	{
		.get string Philips.PSG.Gateway.Settings::get_MultiplexedTsFileName()
	}
	.property string PreviousScheduleDataFolderName()
	{
		.get string Philips.PSG.Gateway.Settings::get_PreviousScheduleDataFolderName()
	}
	.property string PreviousOutputDataFolderName()
	{
		.get string Philips.PSG.Gateway.Settings::get_PreviousOutputDataFolderName()
	}
	.property string ScheduleDataFileName()
	{
		.get string Philips.PSG.Gateway.Settings::get_ScheduleDataFileName()
	}
	.property string ScheduleRunFileName()
	{
		.get string Philips.PSG.Gateway.Settings::get_ScheduleRunFileName()
	}
	.property string TsPlayFileName()
	{
		.get string Philips.PSG.Gateway.Settings::get_TsPlayFileName()
	}
	.property string BuildLogFileName()
	{
		.get string Philips.PSG.Gateway.Settings::get_BuildLogFileName()
	}
	.property valuetype [mscorlib]System.TimeSpan ScheduleTimeGranularity()
	{
		.get valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.Settings::get_ScheduleTimeGranularity()
	}

} // end of class Philips.PSG.Gateway.Settings

.class public auto ansi beforefieldinit Philips.PSG.Gateway.TimeEntry
	extends [System.Windows.Forms]System.Windows.Forms.MaskedTextBox
{
	// Fields
	.field private class [System]System.ComponentModel.IContainer components

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x9909
		// Header size: 1
		// Code size: 13 (0xd)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::.ctor()
		IL_0006: ldarg.0
		IL_0007: call instance void Philips.PSG.Gateway.TimeEntry::InitializeComponent()
		IL_000c: ret
	} // end of method TimeEntry::.ctor

	.method public hidebysig specialname 
		instance valuetype [mscorlib]System.TimeSpan get_Value () cil managed 
	{
		// Method begins at RVA 0x9918
		// Header size: 12
		// Code size: 51 (0x33)
		.maxstack 4
		.locals init (
			[0] string,
			[1] valuetype [mscorlib]System.TimeSpan
		)

		IL_0000: ldarg.0
		IL_0001: callvirt instance string [System.Windows.Forms]System.Windows.Forms.Control::get_Text()
		IL_0006: stloc.0
		IL_0007: ldloc.0
		IL_0008: ldstr "24:00"
		IL_000d: callvirt instance bool [mscorlib]System.String::Equals(string)
		IL_0012: brfalse.s IL_001e

		IL_0014: ldc.i4.1
		IL_0015: ldc.i4.0
		IL_0016: ldc.i4.0
		IL_0017: ldc.i4.0
		IL_0018: newobj instance void [mscorlib]System.TimeSpan::.ctor(int32, int32, int32, int32)
		IL_001d: ret

		IL_001e: nop
		.try
		{
			IL_001f: ldloc.0
			IL_0020: call valuetype [mscorlib]System.TimeSpan [mscorlib]System.TimeSpan::Parse(string)
			IL_0025: stloc.1
			IL_0026: leave.s IL_0031
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_0028: pop
			IL_0029: ldsfld valuetype [mscorlib]System.TimeSpan [mscorlib]System.TimeSpan::Zero
			IL_002e: stloc.1
			IL_002f: leave.s IL_0031
		} // end handler

		IL_0031: ldloc.1
		IL_0032: ret
	} // end of method TimeEntry::get_Value

	.method public hidebysig specialname 
		instance void set_Value (
			valuetype [mscorlib]System.TimeSpan 'value'
		) cil managed 
	{
		// Method begins at RVA 0x9968
		// Header size: 12
		// Code size: 87 (0x57)
		.maxstack 5
		.locals init (
			[0] int32
		)

		IL_0000: ldarga.s 'value'
		IL_0002: ldc.i4.1
		IL_0003: ldc.i4.0
		IL_0004: ldc.i4.0
		IL_0005: ldc.i4.0
		IL_0006: newobj instance void [mscorlib]System.TimeSpan::.ctor(int32, int32, int32, int32)
		IL_000b: call instance bool [mscorlib]System.TimeSpan::Equals(valuetype [mscorlib]System.TimeSpan)
		IL_0010: brfalse.s IL_001e

		IL_0012: ldarg.0
		IL_0013: ldstr "24:00"
		IL_0018: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_001d: ret

		IL_001e: ldarg.0
		IL_001f: ldstr "{0}:{1}"
		IL_0024: ldarga.s 'value'
		IL_0026: call instance int32 [mscorlib]System.TimeSpan::get_Hours()
		IL_002b: stloc.0
		IL_002c: ldloca.s 0
		IL_002e: ldstr "D2"
		IL_0033: call instance string [mscorlib]System.Int32::ToString(string)
		IL_0038: ldarga.s 'value'
		IL_003a: call instance int32 [mscorlib]System.TimeSpan::get_Minutes()
		IL_003f: stloc.0
		IL_0040: ldloca.s 0
		IL_0042: ldstr "D2"
		IL_0047: call instance string [mscorlib]System.Int32::ToString(string)
		IL_004c: call string [mscorlib]System.String::Format(string, object, object)
		IL_0051: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0056: ret
	} // end of method TimeEntry::set_Value

	.method family hidebysig virtual 
		instance void OnKeyPress (
			class [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x99cc
		// Header size: 12
		// Code size: 408 (0x198)
		.maxstack 2
		.locals init (
			[0] string,
			[1] bool,
			[2] bool,
			[3] int32
		)

		IL_0000: ldarg.0
		IL_0001: callvirt instance string [System.Windows.Forms]System.Windows.Forms.Control::get_Text()
		IL_0006: stloc.0
		IL_0007: ldc.i4.0
		IL_0008: stloc.1
		IL_0009: ldc.i4.0
		IL_000a: stloc.2
		IL_000b: ldarg.0
		IL_000c: call instance int32 [System.Windows.Forms]System.Windows.Forms.TextBoxBase::get_SelectionStart()
		IL_0011: stloc.3
		IL_0012: ldloc.3
		IL_0013: switch (IL_0031, IL_009c, IL_0104, IL_0104, IL_013a)

		IL_002c: br IL_0178

		IL_0031: ldarg.1
		IL_0032: callvirt instance char [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs::get_KeyChar()
		IL_0037: ldc.i4.s 50
		IL_0039: ble.s IL_0044

		IL_003b: ldc.i4.1
		IL_003c: stloc.2
		IL_003d: ldc.i4.1
		IL_003e: stloc.1
		IL_003f: br IL_0178

		IL_0044: ldarg.1
		IL_0045: callvirt instance char [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs::get_KeyChar()
		IL_004a: ldc.i4.s 50
		IL_004c: bne.un IL_0178

		IL_0051: ldloc.0
		IL_0052: ldc.i4.1
		IL_0053: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_0058: ldc.i4.s 52
		IL_005a: bgt.s IL_0083

		IL_005c: ldloc.0
		IL_005d: ldc.i4.1
		IL_005e: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_0063: ldc.i4.s 52
		IL_0065: bne.un IL_0178

		IL_006a: ldloc.0
		IL_006b: ldc.i4.3
		IL_006c: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_0071: ldc.i4.s 48
		IL_0073: bgt.s IL_0083

		IL_0075: ldloc.0
		IL_0076: ldc.i4.4
		IL_0077: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_007c: ldc.i4.s 48
		IL_007e: ble IL_0178

		IL_0083: ldarg.0
		IL_0084: ldstr "24:00"
		IL_0089: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_008e: ldarg.0
		IL_008f: ldc.i4.1
		IL_0090: call instance void [System.Windows.Forms]System.Windows.Forms.TextBoxBase::set_SelectionStart(int32)
		IL_0095: ldc.i4.1
		IL_0096: stloc.1
		IL_0097: br IL_0178

		IL_009c: ldloc.0
		IL_009d: ldc.i4.0
		IL_009e: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_00a3: ldc.i4.s 50
		IL_00a5: bne.un.s IL_00ba

		IL_00a7: ldarg.1
		IL_00a8: callvirt instance char [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs::get_KeyChar()
		IL_00ad: ldc.i4.s 52
		IL_00af: ble.s IL_00ba

		IL_00b1: ldc.i4.1
		IL_00b2: stloc.2
		IL_00b3: ldc.i4.1
		IL_00b4: stloc.1
		IL_00b5: br IL_0178

		IL_00ba: ldloc.0
		IL_00bb: ldc.i4.0
		IL_00bc: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_00c1: ldc.i4.s 50
		IL_00c3: bne.un IL_0178

		IL_00c8: ldarg.1
		IL_00c9: callvirt instance char [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs::get_KeyChar()
		IL_00ce: ldc.i4.s 52
		IL_00d0: bne.un IL_0178

		IL_00d5: ldloc.0
		IL_00d6: ldc.i4.3
		IL_00d7: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_00dc: ldc.i4.s 48
		IL_00de: bgt.s IL_00ee

		IL_00e0: ldloc.0
		IL_00e1: ldc.i4.4
		IL_00e2: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_00e7: ldc.i4.s 48
		IL_00e9: ble IL_0178

		IL_00ee: ldarg.0
		IL_00ef: ldstr "24:00"
		IL_00f4: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_00f9: ldarg.0
		IL_00fa: ldc.i4.2
		IL_00fb: call instance void [System.Windows.Forms]System.Windows.Forms.TextBoxBase::set_SelectionStart(int32)
		IL_0100: ldc.i4.1
		IL_0101: stloc.1
		IL_0102: br.s IL_0178

		IL_0104: ldloc.0
		IL_0105: ldc.i4.0
		IL_0106: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_010b: ldc.i4.s 50
		IL_010d: bne.un.s IL_012a

		IL_010f: ldloc.0
		IL_0110: ldc.i4.1
		IL_0111: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_0116: ldc.i4.s 52
		IL_0118: bne.un.s IL_012a

		IL_011a: ldarg.1
		IL_011b: callvirt instance char [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs::get_KeyChar()
		IL_0120: ldc.i4.s 48
		IL_0122: beq.s IL_012a

		IL_0124: ldc.i4.1
		IL_0125: stloc.2
		IL_0126: ldc.i4.1
		IL_0127: stloc.1
		IL_0128: br.s IL_0178

		IL_012a: ldarg.1
		IL_012b: callvirt instance char [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs::get_KeyChar()
		IL_0130: ldc.i4.s 53
		IL_0132: ble.s IL_0178

		IL_0134: ldc.i4.1
		IL_0135: stloc.2
		IL_0136: ldc.i4.1
		IL_0137: stloc.1
		IL_0138: br.s IL_0178

		IL_013a: ldloc.0
		IL_013b: ldc.i4.0
		IL_013c: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_0141: ldc.i4.s 50
		IL_0143: bne.un.s IL_0160

		IL_0145: ldloc.0
		IL_0146: ldc.i4.1
		IL_0147: callvirt instance char [mscorlib]System.String::get_Chars(int32)
		IL_014c: ldc.i4.s 52
		IL_014e: bne.un.s IL_0160

		IL_0150: ldarg.1
		IL_0151: callvirt instance char [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs::get_KeyChar()
		IL_0156: ldc.i4.s 48
		IL_0158: beq.s IL_0160

		IL_015a: ldc.i4.1
		IL_015b: stloc.2
		IL_015c: ldc.i4.1
		IL_015d: stloc.1
		IL_015e: br.s IL_0178

		IL_0160: ldarg.1
		IL_0161: callvirt instance char [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs::get_KeyChar()
		IL_0166: ldc.i4.s 48
		IL_0168: beq.s IL_0178

		IL_016a: ldarg.1
		IL_016b: callvirt instance char [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs::get_KeyChar()
		IL_0170: ldc.i4.s 53
		IL_0172: beq.s IL_0178

		IL_0174: ldc.i4.1
		IL_0175: stloc.2
		IL_0176: ldc.i4.1
		IL_0177: stloc.1

		IL_0178: ldloc.2
		IL_0179: brfalse.s IL_0185

		IL_017b: call class [System]System.Media.SystemSound [System]System.Media.SystemSounds::get_Beep()
		IL_0180: callvirt instance void [System]System.Media.SystemSound::Play()

		IL_0185: ldloc.1
		IL_0186: brfalse.s IL_0190

		IL_0188: ldarg.1
		IL_0189: ldc.i4.1
		IL_018a: callvirt instance void [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs::set_Handled(bool)
		IL_018f: ret

		IL_0190: ldarg.0
		IL_0191: ldarg.1
		IL_0192: call instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::OnKeyPress(class [System.Windows.Forms]System.Windows.Forms.KeyPressEventArgs)
		IL_0197: ret
	} // end of method TimeEntry::OnKeyPress

	.method family hidebysig virtual 
		instance void Dispose (
			bool disposing
		) cil managed 
	{
		// Method begins at RVA 0x9b70
		// Header size: 1
		// Code size: 30 (0x1e)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brfalse.s IL_0016

		IL_0003: ldarg.0
		IL_0004: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.TimeEntry::components
		IL_0009: brfalse.s IL_0016

		IL_000b: ldarg.0
		IL_000c: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.TimeEntry::components
		IL_0011: callvirt instance void [mscorlib]System.IDisposable::Dispose()

		IL_0016: ldarg.0
		IL_0017: ldarg.1
		IL_0018: call instance void [System.Windows.Forms]System.Windows.Forms.Control::Dispose(bool)
		IL_001d: ret
	} // end of method TimeEntry::Dispose

	.method private hidebysig 
		instance void InitializeComponent () cil managed 
	{
		// Method begins at RVA 0x9b90
		// Header size: 12
		// Code size: 72 (0x48)
		.maxstack 2

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Control::SuspendLayout()
		IL_0006: ldarg.0
		IL_0007: ldc.i4.2
		IL_0008: call instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_InsertKeyMode(valuetype [System.Windows.Forms]System.Windows.Forms.InsertKeyMode)
		IL_000d: ldarg.0
		IL_000e: ldstr "00:00"
		IL_0013: call instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_Mask(string)
		IL_0018: ldarg.0
		IL_0019: ldc.i4.s 48
		IL_001b: call instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_PromptChar(char)
		IL_0020: ldarg.0
		IL_0021: ldc.i4.0
		IL_0022: call instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_ResetOnSpace(bool)
		IL_0027: ldarg.0
		IL_0028: ldc.i4.0
		IL_0029: callvirt instance void [System.Windows.Forms]System.Windows.Forms.TextBoxBase::set_ShortcutsEnabled(bool)
		IL_002e: ldarg.0
		IL_002f: ldstr "00:00"
		IL_0034: callvirt instance void [System.Windows.Forms]System.Windows.Forms.Control::set_Text(string)
		IL_0039: ldarg.0
		IL_003a: ldc.i4.3
		IL_003b: call instance void [System.Windows.Forms]System.Windows.Forms.MaskedTextBox::set_TextMaskFormat(valuetype [System.Windows.Forms]System.Windows.Forms.MaskFormat)
		IL_0040: ldarg.0
		IL_0041: ldc.i4.0
		IL_0042: call instance void [System.Windows.Forms]System.Windows.Forms.Control::ResumeLayout(bool)
		IL_0047: ret
	} // end of method TimeEntry::InitializeComponent

	// Properties
	.property instance valuetype [mscorlib]System.TimeSpan Value()
	{
		.get instance valuetype [mscorlib]System.TimeSpan Philips.PSG.Gateway.TimeEntry::get_Value()
		.set instance void Philips.PSG.Gateway.TimeEntry::set_Value(valuetype [mscorlib]System.TimeSpan)
	}

} // end of class Philips.PSG.Gateway.TimeEntry

.class public auto ansi beforefieldinit Philips.PSG.Gateway.VerticalLabel
	extends [System.Windows.Forms]System.Windows.Forms.Label
{
	// Fields
	.field private class [System]System.ComponentModel.IContainer components

	// Methods
	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x9be4
		// Header size: 1
		// Code size: 13 (0xd)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Label::.ctor()
		IL_0006: ldarg.0
		IL_0007: call instance void Philips.PSG.Gateway.VerticalLabel::InitializeComponent()
		IL_000c: ret
	} // end of method VerticalLabel::.ctor

	.method family hidebysig virtual 
		instance void OnPaint (
			class [System.Windows.Forms]System.Windows.Forms.PaintEventArgs eventArgs
		) cil managed 
	{
		// Method begins at RVA 0x9bf4
		// Header size: 12
		// Code size: 111 (0x6f)
		.maxstack 6
		.locals init (
			[0] class [System.Drawing]System.Drawing.Brush,
			[1] valuetype [System.Drawing]System.Drawing.SizeF
		)

		IL_0000: ldarg.1
		IL_0001: callvirt instance class [System.Drawing]System.Drawing.Graphics [System.Windows.Forms]System.Windows.Forms.PaintEventArgs::get_Graphics()
		IL_0006: ldarg.0
		IL_0007: callvirt instance valuetype [System.Drawing]System.Drawing.Color [System.Windows.Forms]System.Windows.Forms.Control::get_ForeColor()
		IL_000c: newobj instance void [System.Drawing]System.Drawing.SolidBrush::.ctor(valuetype [System.Drawing]System.Drawing.Color)
		IL_0011: stloc.0
		IL_0012: dup
		IL_0013: ldarg.0
		IL_0014: callvirt instance string [System.Windows.Forms]System.Windows.Forms.Control::get_Text()
		IL_0019: ldarg.0
		IL_001a: callvirt instance class [System.Drawing]System.Drawing.Font [System.Windows.Forms]System.Windows.Forms.Control::get_Font()
		IL_001f: callvirt instance valuetype [System.Drawing]System.Drawing.SizeF [System.Drawing]System.Drawing.Graphics::MeasureString(string, class [System.Drawing]System.Drawing.Font)
		IL_0024: stloc.1
		IL_0025: dup
		IL_0026: ldc.r4 0.0
		IL_002b: ldarg.0
		IL_002c: call instance int32 [System.Windows.Forms]System.Windows.Forms.Control::get_Height()
		IL_0031: conv.r4
		IL_0032: callvirt instance void [System.Drawing]System.Drawing.Graphics::TranslateTransform(float32, float32)
		IL_0037: dup
		IL_0038: ldc.r4 -90
		IL_003d: callvirt instance void [System.Drawing]System.Drawing.Graphics::RotateTransform(float32)
		IL_0042: ldarg.0
		IL_0043: callvirt instance string [System.Windows.Forms]System.Windows.Forms.Control::get_Text()
		IL_0048: ldarg.0
		IL_0049: callvirt instance class [System.Drawing]System.Drawing.Font [System.Windows.Forms]System.Windows.Forms.Control::get_Font()
		IL_004e: ldloc.0
		IL_004f: ldarg.0
		IL_0050: call instance int32 [System.Windows.Forms]System.Windows.Forms.Control::get_Height()
		IL_0055: conv.r4
		IL_0056: ldloca.s 1
		IL_0058: call instance float32 [System.Drawing]System.Drawing.SizeF::get_Width()
		IL_005d: sub
		IL_005e: ldc.r4 2
		IL_0063: div
		IL_0064: ldc.r4 0.0
		IL_0069: callvirt instance void [System.Drawing]System.Drawing.Graphics::DrawString(string, class [System.Drawing]System.Drawing.Font, class [System.Drawing]System.Drawing.Brush, float32, float32)
		IL_006e: ret
	} // end of method VerticalLabel::OnPaint

	.method family hidebysig virtual 
		instance void Dispose (
			bool disposing
		) cil managed 
	{
		// Method begins at RVA 0x9c6f
		// Header size: 1
		// Code size: 30 (0x1e)
		.maxstack 8

		IL_0000: ldarg.1
		IL_0001: brfalse.s IL_0016

		IL_0003: ldarg.0
		IL_0004: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.VerticalLabel::components
		IL_0009: brfalse.s IL_0016

		IL_000b: ldarg.0
		IL_000c: ldfld class [System]System.ComponentModel.IContainer Philips.PSG.Gateway.VerticalLabel::components
		IL_0011: callvirt instance void [mscorlib]System.IDisposable::Dispose()

		IL_0016: ldarg.0
		IL_0017: ldarg.1
		IL_0018: call instance void [System.Windows.Forms]System.Windows.Forms.Label::Dispose(bool)
		IL_001d: ret
	} // end of method VerticalLabel::Dispose

	.method private hidebysig 
		instance void InitializeComponent () cil managed 
	{
		// Method begins at RVA 0x9c8e
		// Header size: 1
		// Code size: 14 (0xe)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System.Windows.Forms]System.Windows.Forms.Control::SuspendLayout()
		IL_0006: ldarg.0
		IL_0007: ldc.i4.0
		IL_0008: call instance void [System.Windows.Forms]System.Windows.Forms.Control::ResumeLayout(bool)
		IL_000d: ret
	} // end of method VerticalLabel::InitializeComponent

} // end of class Philips.PSG.Gateway.VerticalLabel

.class private auto ansi sealed beforefieldinit Philips.PSG.Gateway.Properties.Settings
	extends [System]System.Configuration.ApplicationSettingsBase
{
	.custom instance void [mscorlib]System.Runtime.CompilerServices.CompilerGeneratedAttribute::.ctor() = (
		01 00 00 00
	)
	.custom instance void [System]System.CodeDom.Compiler.GeneratedCodeAttribute::.ctor(string, string) = (
		01 00 4b 4d 69 63 72 6f 73 6f 66 74 2e 56 69 73
		75 61 6c 53 74 75 64 69 6f 2e 45 64 69 74 6f 72
		73 2e 53 65 74 74 69 6e 67 73 44 65 73 69 67 6e
		65 72 2e 53 65 74 74 69 6e 67 73 53 69 6e 67 6c
		65 46 69 6c 65 47 65 6e 65 72 61 74 6f 72 08 31
		35 2e 39 2e 30 2e 30 00 00
	)
	// Fields
	.field private static class Philips.PSG.Gateway.Properties.Settings defaultInstance

	// Methods
	.method public hidebysig specialname static 
		class Philips.PSG.Gateway.Properties.Settings get_Default () cil managed 
	{
		// Method begins at RVA 0x9c9d
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldsfld class Philips.PSG.Gateway.Properties.Settings Philips.PSG.Gateway.Properties.Settings::defaultInstance
		IL_0005: ret
	} // end of method Settings::get_Default

	.method public hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x9ca4
		// Header size: 1
		// Code size: 7 (0x7)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [System]System.Configuration.ApplicationSettingsBase::.ctor()
		IL_0006: ret
	} // end of method Settings::.ctor

	.method private hidebysig specialname rtspecialname static 
		void .cctor () cil managed 
	{
		// Method begins at RVA 0x9cac
		// Header size: 1
		// Code size: 21 (0x15)
		.maxstack 8

		IL_0000: newobj instance void Philips.PSG.Gateway.Properties.Settings::.ctor()
		IL_0005: call class [System]System.Configuration.SettingsBase [System]System.Configuration.SettingsBase::Synchronized(class [System]System.Configuration.SettingsBase)
		IL_000a: castclass Philips.PSG.Gateway.Properties.Settings
		IL_000f: stsfld class Philips.PSG.Gateway.Properties.Settings Philips.PSG.Gateway.Properties.Settings::defaultInstance
		IL_0014: ret
	} // end of method Settings::.cctor

	// Properties
	.property class Philips.PSG.Gateway.Properties.Settings Default()
	{
		.get class Philips.PSG.Gateway.Properties.Settings Philips.PSG.Gateway.Properties.Settings::get_Default()
	}

} // end of class Philips.PSG.Gateway.Properties.Settings

.class public auto ansi sealed beforefieldinit Philips.PSG.Gateway.Data.TsPlayer
	extends [mscorlib]System.Object
{
	// Nested Types
	.class nested public auto ansi sealed TsPlayerState
		extends [mscorlib]System.Enum
	{
		// Fields
		.field public specialname rtspecialname int32 value__
		.field public static literal valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState NoTs = int32(0)
		.field public static literal valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Paused = int32(1)
		.field public static literal valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Running = int32(2)

	} // end of class TsPlayerState


	// Fields
	.field private static initonly class Philips.PSG.Gateway.Data.TsPlayer _instance
	.field private valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState _state
	.field private string _transportStreamFile

	// Methods
	.method private hidebysig specialname rtspecialname 
		instance void .ctor () cil managed 
	{
		// Method begins at RVA 0x9cc2
		// Header size: 1
		// Code size: 39 (0x27)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: call instance void [mscorlib]System.Object::.ctor()
		IL_0006: ldstr "TSPlayer: initializing"
		IL_000b: call void [mscorlib]System.Console::WriteLine(string)
		IL_0010: ldarg.0
		IL_0011: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0016: callvirt instance string Philips.PSG.Gateway.Config::get_MultiplexedTsFile()
		IL_001b: stfld string Philips.PSG.Gateway.Data.TsPlayer::_transportStreamFile
		IL_0020: ldarg.0
		IL_0021: call instance void Philips.PSG.Gateway.Data.TsPlayer::SetInitialState()
		IL_0026: ret
	} // end of method TsPlayer::.ctor

	.method public hidebysig specialname static 
		class Philips.PSG.Gateway.Data.TsPlayer get_Instance () cil managed 
	{
		// Method begins at RVA 0x9cea
		// Header size: 1
		// Code size: 6 (0x6)
		.maxstack 8

		IL_0000: ldsfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.Data.TsPlayer::_instance
		IL_0005: ret
	} // end of method TsPlayer::get_Instance

	.method public hidebysig specialname 
		instance valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState get_State () cil managed 
	{
		// Method begins at RVA 0x9cf1
		// Header size: 1
		// Code size: 34 (0x22)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::_state
		IL_0006: ldc.i4.2
		IL_0007: beq.s IL_001b

		IL_0009: ldarg.0
		IL_000a: ldarg.0
		IL_000b: call instance bool Philips.PSG.Gateway.Data.TsPlayer::IsMultiplexedTsExists()
		IL_0010: brtrue.s IL_0015

		IL_0012: ldc.i4.0
		IL_0013: br.s IL_0016

		IL_0015: ldc.i4.1

		IL_0016: stfld valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::_state

		IL_001b: ldarg.0
		IL_001c: ldfld valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::_state
		IL_0021: ret
	} // end of method TsPlayer::get_State

	.method public hidebysig 
		instance bool WasTsPlayInProgress () cil managed 
	{
		// Method begins at RVA 0x9d14
		// Header size: 1
		// Code size: 16 (0x10)
		.maxstack 8

		IL_0000: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
		IL_0005: callvirt instance string Philips.PSG.Gateway.Config::get_TsPlayFile()
		IL_000a: call bool [mscorlib]System.IO.File::Exists(string)
		IL_000f: ret
	} // end of method TsPlayer::WasTsPlayInProgress

	.method public hidebysig 
		instance bool Play (
			class Philips.PSG.Gateway.PlayStreamHandlerErrored onErrorPlayStreamCallback
		) cil managed 
	{
		// Method begins at RVA 0x9d28
		// Header size: 12
		// Code size: 102 (0x66)
		.maxstack 3

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::_state
		IL_0006: ldc.i4.1
		IL_0007: beq.s IL_000b

		IL_0009: ldc.i4.0
		IL_000a: ret

		IL_000b: call class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager::get_Instance()
		IL_0010: callvirt instance string Philips.PSG.Gateway.DekTecManager::get_DeviceType()
		IL_0015: call bool [mscorlib]System.String::IsNullOrEmpty(string)
		IL_001a: brfalse.s IL_0041

		IL_001c: call string Philips.PSG.Gateway.Resources::get_DLG_DekTecDeviceNotAvailable()
		IL_0021: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0026: ldstr " "
		IL_002b: call string Philips.PSG.Gateway.Resources::get_GLOBAL_AppName()
		IL_0030: callvirt instance string [mscorlib]System.Object::ToString()
		IL_0035: call string [mscorlib]System.String::Concat(string, string, string)
		IL_003a: call void [mscorlib]System.Console::WriteLine(string)
		IL_003f: ldc.i4.0
		IL_0040: ret

		IL_0041: ldstr "TSPlayer: play TS"
		IL_0046: call void [mscorlib]System.Console::WriteLine(string)
		IL_004b: ldarg.0
		IL_004c: ldc.i4.2
		IL_004d: call instance void Philips.PSG.Gateway.Data.TsPlayer::SetState(valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState)
		IL_0052: call class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager::get_Instance()
		IL_0057: ldarg.0
		IL_0058: ldfld string Philips.PSG.Gateway.Data.TsPlayer::_transportStreamFile
		IL_005d: ldarg.1
		IL_005e: callvirt instance bool Philips.PSG.Gateway.DekTecManager::StartPlayback(string, class Philips.PSG.Gateway.PlayStreamHandlerErrored)
		IL_0063: pop
		IL_0064: ldc.i4.1
		IL_0065: ret
	} // end of method TsPlayer::Play

	.method public hidebysig 
		instance void Pause () cil managed 
	{
		// Method begins at RVA 0x9d9a
		// Header size: 1
		// Code size: 37 (0x25)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::_state
		IL_0006: ldc.i4.2
		IL_0007: bne.un.s IL_0024

		IL_0009: ldstr "TSPlayer: pausing play"
		IL_000e: call void [mscorlib]System.Console::WriteLine(string)
		IL_0013: ldarg.0
		IL_0014: ldc.i4.1
		IL_0015: call instance void Philips.PSG.Gateway.Data.TsPlayer::SetState(valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState)
		IL_001a: call class Philips.PSG.Gateway.DekTecManager Philips.PSG.Gateway.DekTecManager::get_Instance()
		IL_001f: callvirt instance void Philips.PSG.Gateway.DekTecManager::StopPlayback()

		IL_0024: ret
	} // end of method TsPlayer::Pause

	.method public hidebysig 
		instance bool IsValidTsFile (
			string filePath
		) cil managed 
	{
		// Method begins at RVA 0x9dc0
		// Header size: 12
		// Code size: 22 (0x16)
		.maxstack 1
		.locals init (
			[0] bool
		)

		IL_0000: ldc.i4.0
		IL_0001: stloc.0
		IL_0002: ldarg.1
		IL_0003: call bool [mscorlib]System.String::IsNullOrEmpty(string)
		IL_0008: brtrue.s IL_0014

		IL_000a: ldarg.1
		IL_000b: call bool [mscorlib]System.IO.File::Exists(string)
		IL_0010: brfalse.s IL_0014

		IL_0012: ldc.i4.1
		IL_0013: stloc.0

		IL_0014: ldloc.0
		IL_0015: ret
	} // end of method TsPlayer::IsValidTsFile

	.method private hidebysig 
		instance void SetState (
			valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState state
		) cil managed 
	{
		// Method begins at RVA 0x9de2
		// Header size: 1
		// Code size: 38 (0x26)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::_state
		IL_0006: ldarg.1
		IL_0007: beq.s IL_0025

		IL_0009: ldarg.0
		IL_000a: ldarg.1
		IL_000b: stfld valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::_state
		IL_0010: ldarg.1
		IL_0011: ldc.i4.1
		IL_0012: beq.s IL_001f

		IL_0014: ldarg.1
		IL_0015: ldc.i4.2
		IL_0016: bne.un.s IL_0025

		IL_0018: ldc.i4.1
		IL_0019: call void Philips.PSG.Gateway.Data.TsPlayer::SetTsPlayInProgress(bool)
		IL_001e: ret

		IL_001f: ldc.i4.0
		IL_0020: call void Philips.PSG.Gateway.Data.TsPlayer::SetTsPlayInProgress(bool)

		IL_0025: ret
	} // end of method TsPlayer::SetState

	.method private hidebysig 
		instance void SetInitialState () cil managed 
	{
		// Method begins at RVA 0x9e09
		// Header size: 1
		// Code size: 19 (0x13)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldarg.0
		IL_0002: call instance bool Philips.PSG.Gateway.Data.TsPlayer::IsMultiplexedTsExists()
		IL_0007: brtrue.s IL_000c

		IL_0009: ldc.i4.0
		IL_000a: br.s IL_000d

		IL_000c: ldc.i4.1

		IL_000d: stfld valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::_state
		IL_0012: ret
	} // end of method TsPlayer::SetInitialState

	.method private hidebysig 
		instance bool IsMultiplexedTsExists () cil managed 
	{
		// Method begins at RVA 0x9e1d
		// Header size: 1
		// Code size: 12 (0xc)
		.maxstack 8

		IL_0000: ldarg.0
		IL_0001: ldfld string Philips.PSG.Gateway.Data.TsPlayer::_transportStreamFile
		IL_0006: call bool [mscorlib]System.IO.File::Exists(string)
		IL_000b: ret
	} // end of method TsPlayer::IsMultiplexedTsExists

	.method private hidebysig static 
		void SetTsPlayInProgress (
			bool inProgress
		) cil managed 
	{
		// Method begins at RVA 0x9e2c
		// Header size: 12
		// Code size: 80 (0x50)
		.maxstack 3
		.locals init (
			[0] class [mscorlib]System.IO.FileStream,
			[1] class [mscorlib]System.Exception
		)

		.try
		{
			IL_0000: ldarg.0
			IL_0001: brfalse.s IL_001f

			IL_0003: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
			IL_0008: callvirt instance string Philips.PSG.Gateway.Config::get_TsPlayFile()
			IL_000d: call class [mscorlib]System.IO.FileStream [mscorlib]System.IO.File::Create(string)
			IL_0012: stloc.0
			.try
			{
				IL_0013: leave.s IL_002e
			} // end .try
			finally
			{
				IL_0015: ldloc.0
				IL_0016: brfalse.s IL_001e

				IL_0018: ldloc.0
				IL_0019: callvirt instance void [mscorlib]System.IDisposable::Dispose()

				IL_001e: endfinally
			} // end handler

			IL_001f: call class Philips.PSG.Gateway.Config Philips.PSG.Gateway.ConfigFactory::get_CurrentConfig()
			IL_0024: callvirt instance string Philips.PSG.Gateway.Config::get_TsPlayFile()
			IL_0029: call void [mscorlib]System.IO.File::Delete(string)

			IL_002e: leave.s IL_004f
		} // end .try
		catch [mscorlib]System.Exception
		{
			IL_0030: stloc.1
			IL_0031: ldstr "TSPlayer: Error - failed to create/delete TS play file: "
			IL_0036: ldloc.1
			IL_0037: dup
			IL_0038: brtrue.s IL_003e

			IL_003a: pop
			IL_003b: ldnull
			IL_003c: br.s IL_0043

			IL_003e: callvirt instance string [mscorlib]System.Object::ToString()

			IL_0043: call string [mscorlib]System.String::Concat(string, string)
			IL_0048: call void [mscorlib]System.Console::WriteLine(string)
			IL_004d: leave.s IL_004f
		} // end handler

		IL_004f: ret
	} // end of method TsPlayer::SetTsPlayInProgress

	.method private hidebysig specialname rtspecialname static 
		void .cctor () cil managed 
	{
		// Method begins at RVA 0x9ea4
		// Header size: 1
		// Code size: 11 (0xb)
		.maxstack 8

		IL_0000: newobj instance void Philips.PSG.Gateway.Data.TsPlayer::.ctor()
		IL_0005: stsfld class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.Data.TsPlayer::_instance
		IL_000a: ret
	} // end of method TsPlayer::.cctor

	// Properties
	.property class Philips.PSG.Gateway.Data.TsPlayer Instance()
	{
		.get class Philips.PSG.Gateway.Data.TsPlayer Philips.PSG.Gateway.Data.TsPlayer::get_Instance()
	}
	.property instance valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState State()
	{
		.get instance valuetype Philips.PSG.Gateway.Data.TsPlayer/TsPlayerState Philips.PSG.Gateway.Data.TsPlayer::get_State()
	}

} // end of class Philips.PSG.Gateway.Data.TsPlayer

