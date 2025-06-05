/**

	Provides custom exception classes for mathematical operations and number processing.

	<p>This package contains specialized exceptions that extend standard Java exceptions
	to handle errors specific to mathematical computations, including:</p>

	<ul>

		<li>Number format violations</li>
		<li>Undefined mathematical operations</li>
		<li>Indeterminate forms</li>
		<li>Other arithmetic edge cases</li>

	</ul>

	<p><b>Key Features:</b></p>
	<ul>

		<li>Thrown when a number string doesn't conform to expected numeric formats ({@link math.core.exceptions.IllegalNumberFormatException})</li>
		<li>Thrown for indeterminate mathematical forms like {@code x/0} ({@link math.core.exceptions.UndeterminedException})</li>

	</ul>


	@author Dandelion
	@version v0.0.1
	@since v0.0.16

*/

package math.core.exceptions;