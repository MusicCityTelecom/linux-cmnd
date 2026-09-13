using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 32)]
[NativeCppClass]
internal struct DtBsProgress
{
	[CLSCompliant(false)]
	[NativeCppClass]
	public enum BsEvent
	{

	}
}
