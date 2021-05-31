package com.jrorg.bookmyshow.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jrorg.bookmyshow.entity.Place;

public class PlaceSerializer extends StdSerializer<Place>{
	public PlaceSerializer(){this(null);}
    public PlaceSerializer(Class<Place> t){super(t);} // sets `handledType` to the provided class
	@Override
	public void serialize(Place value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		 gen.writeStartObject();
	     gen.writeObjectField("places", value);
	     gen.writeEndObject();
	}
}
