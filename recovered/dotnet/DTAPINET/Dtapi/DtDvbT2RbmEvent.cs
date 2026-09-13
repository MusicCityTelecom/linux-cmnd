using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 40)]
[UnsafeValueType]
[NativeCppClass]
internal struct DtDvbT2RbmEvent
{
	[StructLayout(LayoutKind.Explicit, Size = 16)]
	[CLSCompliant(false)]
	[NativeCppClass]
	[UnsafeValueType]
	public struct _003Cunnamed_002Dtype_002Du_003E
	{
		[StructLayout(LayoutKind.Sequential, Size = 16)]
		[CLSCompliant(false)]
		[NativeCppClass]
		[UnsafeValueType]
		public struct _003Cunnamed_002Dtype_002DPlot_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 4)]
		[CLSCompliant(false)]
		[NativeCppClass]
		public struct _003Cunnamed_002Dtype_002DBufsTooSmall_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 4)]
		[CLSCompliant(false)]
		[NativeCppClass]
		public struct _003Cunnamed_002Dtype_002DTtoInThePast_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 8)]
		[NativeCppClass]
		[CLSCompliant(false)]
		public struct _003Cunnamed_002Dtype_002DDjbOverflow_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 4)]
		[NativeCppClass]
		[CLSCompliant(false)]
		public struct _003Cunnamed_002Dtype_002DCrc8ErrorHeader_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 8)]
		[CLSCompliant(false)]
		[NativeCppClass]
		public struct _003Cunnamed_002Dtype_002DSyncDTooLarge_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 8)]
		[NativeCppClass]
		[CLSCompliant(false)]
		public struct _003Cunnamed_002Dtype_002DInvalidSyncD_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 8)]
		[NativeCppClass]
		[CLSCompliant(false)]
		public struct _003Cunnamed_002Dtype_002DTdiOverflow_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 8)]
		[NativeCppClass]
		[CLSCompliant(false)]
		public struct _003Cunnamed_002Dtype_002DInvalidPlpStart_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 4)]
		[NativeCppClass]
		[CLSCompliant(false)]
		public struct _003Cunnamed_002Dtype_002DIscrError_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 8)]
		[NativeCppClass]
		[CLSCompliant(false)]
		public struct _003Cunnamed_002Dtype_002DBufsNotConstant_003E
		{
		}

		[StructLayout(LayoutKind.Sequential, Size = 4)]
		[CLSCompliant(false)]
		[NativeCppClass]
		public struct _003Cunnamed_002Dtype_002DPlpNumBlocksTooSmall_003E
		{
		}
	}
}
