// =======================================================================================
// Warning. Do not edit this file directly.  This file was built with the SPL generator.
// Eduardo Andrade - <%= Time.now %>
// =======================================================================================
package <%= entity.package() %>;

public class <%= entity.name() %> {
<% fields.each { |field| %>
	private <%= field.type() %> _<%= field.name() %>;<% } %>

	public <%= entity.name() %>() { 
	<% fields.each { |field| %>
		_<%= field.name() %> = <%= field.value() %>;
	<% } %>
	}
	
	<% 
	fields.each { |field| 
	if (field.getter_setter() == true)%>
		public <%= field.type() %> get<%= field.name().capitalize %>() { return _<%= field.name() %>; }
		public void set<%= field.name().capitalize %>( <%= field.type() %> value ) { _<%= field.name() %> = value; }
	<% 
	end
	} 
	%>

}
