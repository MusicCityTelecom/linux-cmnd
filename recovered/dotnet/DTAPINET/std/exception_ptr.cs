using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace std;

[StructLayout(LayoutKind.Sequential, Size = 8)]
[NativeCppClass]
internal struct exception_ptr
{
	[SpecialName]
	public unsafe static void _003CMarshalCopy_003E(exception_ptr* A_0, exception_ptr* A_1)
	{
		global::_003CModule_003E.__ExceptionPtrCopy(A_0, A_1);
	}

	[SpecialName]
	public unsafe static void _003CMarshalDestroy_003E(exception_ptr* A_0)
	{
		global::_003CModule_003E.__ExceptionPtrDestroy(A_0);
	}
}
