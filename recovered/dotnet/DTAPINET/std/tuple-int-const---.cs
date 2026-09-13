using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace std;

[StructLayout(LayoutKind.Sequential, Size = 4)]
[NativeCppClass]
internal struct tuple_003Cint_0020const_0020_0026_003E
{
	[SpecialName]
	public unsafe static void _003CMarshalCopy_003E(tuple_003Cint_0020const_0020_0026_003E* A_0, tuple_003Cint_0020const_0020_0026_003E* A_1)
	{
		// IL cpblk instruction
		System.Runtime.CompilerServices.Unsafe.CopyBlock(A_0, A_1, 4);
	}
}
