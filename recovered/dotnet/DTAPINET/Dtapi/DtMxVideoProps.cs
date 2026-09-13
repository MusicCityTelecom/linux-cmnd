using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 88)]
[NativeCppClass]
internal struct DtMxVideoProps
{
	[StructLayout(LayoutKind.Sequential, Size = 16)]
	[CLSCompliant(false)]
	[NativeCppClass]
	public struct Field
	{
	}
}
