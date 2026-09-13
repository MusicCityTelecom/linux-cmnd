using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 32)]
[UnsafeValueType]
[NativeCppClass]
internal struct DtStreamSelPars
{
	[StructLayout(LayoutKind.Explicit, Size = 24)]
	[UnsafeValueType]
	[CLSCompliant(false)]
	[NativeCppClass]
	public struct _003Cunnamed_002Dtype_002Du_003E
	{
	}
}
