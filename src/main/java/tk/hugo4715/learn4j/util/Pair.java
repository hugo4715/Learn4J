package tk.hugo4715.learn4j.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<A,B> {
	protected A left;
	protected B right;
}
