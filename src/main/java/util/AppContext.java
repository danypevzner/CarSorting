package util;

import core.CarField;
import core.IApplicationContext;
import model.Car;

public class AppContext implements IApplicationContext {
    private final CarInvariants schema = new CarInvariants();

	@Override
	public ICarBuilder getBuilder() {
		return new SimpleCarBuilder(schema);
	}

	@Override
	public IValidationSchema<CarField, Car> getValidationSchema() {
		return schema;
	}

    @Override
    public ICarFactory getFactory() {
        return new RandomCarFactory(schema);
    }

}
