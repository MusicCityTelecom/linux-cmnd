using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 52)]
[NativeCppClass]
internal struct DtEncMuxPars
{
	[StructLayout(LayoutKind.Sequential, Size = 8)]
	[CLSCompliant(false)]
	[NativeCppClass]
	public struct EsPars
	{
	}
}
