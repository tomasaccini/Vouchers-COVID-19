package assemblers;


import commands.TrackingCommand
import templates.ConcreteObjectAssembler
import vouchers.Tracking


class TrackingAssembler extends ConcreteObjectAssembler<Tracking, TrackingCommand> {

    @Override
    protected Tracking getEntity(Long id) {
        return (id == null || id == 0) ? new Tracking() : Tracking.get(id)
    }

    @Override
    protected TrackingCommand createBean() {
        return new TrackingCommand()
    }

    @Override
    TrackingCommand toBean(Tracking domain) {
        TrackingCommand bean  = super.toBean(domain)

        return bean
    }

    @Override
    Tracking fromBean(TrackingCommand bean) {

        Tracking domain = super.fromBean(bean)

        return domain
    }

}
