# StopWatch

This is an application displaying the Start and stop timer with respective start, stop and rest buttons

The application is mainly shows the smooth usuage of services and broadcast recievers
as how we call the service from the main activity by passing the current time with a service intent
and then the service call later invokes an inner timer task claas where the current time is updated
and the updated tme is later sent by broadcast
where the broadcast is recieved at the main activity displaying the time.
the time time is displayed in an propoer formatted time using a get format emthod.
