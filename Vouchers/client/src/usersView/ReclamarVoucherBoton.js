import React from "react";
import IconButton from '@material-ui/core/IconButton';
import FeedbackIcon from '@material-ui/icons/Feedback';

export default function ReclamarVoucherBoton(props) {
  const [modal, setModal] = React.useState(false);

  return (
    <IconButton color="action" component="span">
      <FeedbackIcon />
    </IconButton>
  )
}
