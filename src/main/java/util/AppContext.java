package util;

import core.CarField;
import core.IApplicationContext;
import model.Car;

public class AppContext implements IApplicationContext {
    private final IValidationSchema<CarField, Car> schema = new CarValidationSchema();

	@Override
	public ICarBuilder getBuilder() {
		return new SimpleCarBuilder(schema);
	}

	@Override
	public IValidationSchema<CarField, Car> getValidationSchema() {
		return schema;
	}

}
