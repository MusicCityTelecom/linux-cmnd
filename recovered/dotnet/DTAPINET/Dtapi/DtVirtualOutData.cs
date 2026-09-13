using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 24)]
[NativeCppClass]
internal struct DtVirtualOutData
{
	[CLSCompliant(false)]
	[NativeCppClass]
	public enum OutDataType
	{

	}

	[StructLayout(LayoutKind.Explicit, Size = 16)]
	[CLSCompliant(false)]
	[NativeCppClass]
	public struct _003Cunnamed_002Dtype_002Du_003E
	{
		[StructLayout(LayoutKind.Sequential, Size = 12)]
		[CLSCompliant(false)]
		[NativeCppClass]
		public struct _003Cunnamed_002Dtype_002DIqSamplesInt16_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 12)]
		[CLSCompliant(false)]
		[NativeCppClass]
		public struct _003Cunnamed_002Dtype_002DIqSamplesFloat32_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 16)]
		[CLSCompliant(false)]
		[NativeCppClass]
		public struct _003Cunnamed_002Dtype_002DT2MiTs188_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 8)]
		[NativeCppClass]
		[CLSCompliant(false)]
		public struct _003Cunnamed_002Dtype_002DDvbS2L3_003E
		{
		}
	}
}
