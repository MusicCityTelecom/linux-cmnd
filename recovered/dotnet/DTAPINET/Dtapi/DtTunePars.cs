using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 36)]
[NativeCppClass]
[UnsafeValueType]
internal struct DtTunePars
{
	[StructLayout(LayoutKind.Explicit, Size = 36)]
	[CLSCompliant(false)]
	[NativeCppClass]
	[UnsafeValueType]
	public struct _003Cunnamed_002Dtype_002Du_003E
	{
		[StructLayout(LayoutKind.Sequential, Size = 36)]
		[UnsafeValueType]
		[CLSCompliant(false)]
		[NativeCppClass]
		public struct _003Cunnamed_002Dtype_002Dm_Dta2131TunePars_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 8)]
		[CLSCompliant(false)]
		[NativeCppClass]
		public struct _003Cunnamed_002Dtype_002Dm_SdrFrontEndTunePars_003E
		{
		}
	}
}
