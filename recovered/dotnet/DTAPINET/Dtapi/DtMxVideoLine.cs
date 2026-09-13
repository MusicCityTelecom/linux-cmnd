using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 12)]
[NativeCppClass]
internal struct DtMxVideoLine
{
	[StructLayout(LayoutKind.Sequential, Size = 16)]
	[CLSCompliant(false)]
	[NativeCppClass]
	public struct Plane
	{
	}
}
