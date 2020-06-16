package assemblers;


import commands.TrackingCommand
import enums.TrackingType
import vouchers.Client;
import vouchers.Tracking;


public class TrackingAssembler extends BaseAssembler {

    TrackingCommand fromDomain(Tracking domain) {
        TrackingCommand command = new TrackingCommand(type: domain.type.value, clientID: domain.client.id)
        return command
    }

    Tracking toDomain(TrackingCommand command) {
        Tracking domain = command.id ? Tracking.get(command.id) : new Tracking(type: new TrackingType(command.type), client: Client.get(command.clientID))
        return domain
    }
}
